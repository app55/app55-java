package com.app55.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.junit.Assert;
import org.junit.Test;

import com.app55.domain.Address;
import com.app55.domain.Card;
import com.app55.domain.Schedule;
import com.app55.domain.Transaction;
import com.app55.domain.User;
import com.app55.message.CardCreateResponse;
import com.app55.message.CardDeleteResponse;
import com.app55.message.CardListResponse;
import com.app55.message.Request;
import com.app55.message.ScheduleCreateResponse;
import com.app55.message.ScheduleDeleteResponse;
import com.app55.message.ScheduleGetResponse;
import com.app55.message.ScheduleListResponse;
import com.app55.message.ScheduleUpdateResponse;
import com.app55.message.TransactionCancelResponse;
import com.app55.message.TransactionCommitResponse;
import com.app55.message.TransactionCreateRequest;
import com.app55.message.TransactionCreateResponse;
import com.app55.message.UserAuthenticateResponse;
import com.app55.message.UserCreateResponse;
import com.app55.message.UserGetResponse;
import com.app55.message.UserUpdateResponse;
import com.app55.test.util.TestUtil;
import com.app55.transport.HttpResponse;
import com.app55.util.EncodeUtil;
import com.app55.util.JsonUtil;

/**
 * This test flow provides a basic test of the java library against a sandbox account with api credentials defined in environment variables (APP55_API_KEY,
 * APP55_API_SECRET) or in the file TestConfiguration. Please ensure these variables are setup correctly before attempting to run the tests.
 */
public class IntegrationTest
{
	@Test
	public void test() throws Exception
	{
		System.out.print("\napp55-java integration tests - api key: " + TestConfiguration.GATEWAY.getApiKey() + "\n");
		User user = createUser().getUser();
		getUser(user.getId());

		Card card1 = createCard(user, "4111111111111111", false).getCard();
		Transaction transaction = createTransaction(user, card1).getTransaction();
		
		cancelTransaction(user, transaction);
		try
		{
			cancelTransaction(user, transaction);
			assertTrue("It should throw an exception", false);
		}
		catch (Exception e)
		{
			// The transaction was already cancelled.
		}

		Card card2 = createCard(user, "4111111111111111", false).getCard();
		transaction = createTransaction(user, card2).getTransaction();
		commitTransaction(transaction);

		Card card3 = createCard(user, "4111111111111111", false).getCard();
		transaction = createTransaction(user, card3).getTransaction();
		commitTransaction(transaction);
		//======
		//create transaction with threeds=true and transaction.commit=false for an enrolled card
		//do 3D redirect flow
		TransactionCreateResponse tResponse = create3DTransaction(user,card3, false);
		String redirectUrl = tResponse.getThreeDSecureRedirectUrl();
		do3DRedirect(redirectUrl);
		commitTransaction(tResponse.getTransaction());
		//======
		//create transaction with threeds=true and transaction.commit=true for an enrolled card
		//do 3D redirect flow
		TransactionCreateResponse tResponse2 = create3DTransaction(user,card3, true);
		redirectUrl = tResponse2.getThreeDSecureRedirectUrl();
		do3DRedirect(redirectUrl);
		//======
		Card card4 = createCard(user, "4543130000001116", true).getCard();
		//======
		//create transaction with threeds=true with transaction.commit=false for a non-enrolled card
		//send a transaction commit for above transaction 
		TransactionCreateResponse tResponse3 = create3DTransaction(user,card4, false);
		commitTransaction(tResponse3.getTransaction());
		//======		
		//create transaction with threeds=true and transaction.commit=true for a non-enrolled card
		create3DTransaction(user,card4, true);
		
		List<Card> cards = listCards(user, 4).getCards();

		int count = 0;
		Card workingCard = null;
		for (Card card : cards)
		{
			if (++count > 3)
			{
				workingCard = card;
				break;
			}

			deleteCard(user, card);
		}

		listCards(user, 1);

		updateUser(user);
		authenticateUser(user.getId());

		Schedule schedule1 = createSchedule(user, workingCard, "0.10").getSchedule();
		getSchedule(user, schedule1).getSchedule();

		ScheduleGetResponse response = getSchedule(user, schedule1);
		Assert.assertEquals(workingCard.getToken(), response.getCard().getToken());

		Schedule schedule2 = createSchedule(user, workingCard, "0.12").getSchedule();
		Schedule schedule3 = createSchedule(user, workingCard, "0.13").getSchedule();
		
		try {
		    Thread.sleep(5000);
		} catch(InterruptedException ex) {
		    
		}

		schedule3.setEnd("2016-10-10");
		updateSchedule(user, workingCard, schedule3);
		schedule3 = getSchedule(user, schedule3).getSchedule();

		ScheduleListResponse listResponse = listSchedules(user, null);
		assertTrue("List does not contain expected schedule1", listResponse.getSchedules().contains(schedule1));
		assertTrue("List does not contain expected schedule2", listResponse.getSchedules().contains(schedule2));
		assertTrue("List does not contain expected schedule3", listResponse.getSchedules().contains(schedule3));
		assertEquals("List is not the correct length", 3, listResponse.getSchedules().size());

		deleteSchedule(user, schedule1);
		deleteSchedule(user, schedule2);
		deleteSchedule(user, schedule3);

		listResponse = listSchedules(user, null);
		assertEquals("Schedule list not expected size.", 3, listResponse.getSchedules().size());
		listResponse = listSchedules(user, true);
		assertEquals("Active schedule list not expected size.", 0, listResponse.getSchedules().size());
	}
	private void do3DRedirect(String redirectUrl) {
		try {
			;
			HttpURLConnection con = (HttpURLConnection) new URL(redirectUrl + "&next=" + TestConfiguration.GATEWAY.getEnvironment().getBaseUrl() + "/echo").openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Accept", "application/json");
			con.setRequestProperty("Authorization", EncodeUtil.createBasicAuthString(TestConfiguration.GATEWAY.getApiKey(), TestConfiguration.GATEWAY.getApiSecret()));
			
			Scanner s = null;
			if (con.getResponseCode() > 400) {
				InputStream is = con.getErrorStream();
				if (is != null) {
					s = new Scanner(is);
				}
			}
			else {
				s = new Scanner(con.getInputStream());
			}
			String content = "";
			if (s != null) {
				s.useDelimiter("\\Z");
				content = s.next();
			}
			@SuppressWarnings("rawtypes")
			Request req = new TransactionCreateRequest();
			TransactionCreateResponse res =JsonUtil.object(content, TransactionCreateResponse.class);
			Assert.assertEquals("3D Redirect: Unexpected transaction code.", "succeeded", res.getTransaction().getCode());
			System.out.println("3D Redirect: SUCCESS");
		}
		catch (Exception ex) {
			//ex.printStackTrace();
		}
	}
	private UserCreateResponse createUser()
	{
		String email = "example." + TestUtil.getTimestamp() + "@app55.com";
		String phone = "0123 456 7890";
		String password = "pa55word";
		System.out.print("Creating user " + email + "...");

		UserCreateResponse response = TestConfiguration.GATEWAY.createUser(new User(email, phone, password, password)).send();
		
		assertEquals("UserCreate: Unexpected email.", email, response.getUser().getEmail());
		assertEquals("UserCreate: Unexpected phone.", phone, response.getUser().getPhone());
		System.out.print("DONE (" + response.getUser().getId() + ")\n");
		return response;
	}
	
	private void getUser(Long userId)
	{
		System.out.print("Getting user " + String.valueOf(userId) + "...");

		User basicUser = new User();
		basicUser.setId(userId);

		UserGetResponse response = TestConfiguration.GATEWAY.getUser(basicUser).send();

		Assert.assertEquals("UserGet: Returned user not not as expected.", userId, response.getUser().getId());
		System.out.print("DONE\n");
	}
	
	private UserUpdateResponse updateUser(User user)
	{
		String email = "example." + TestUtil.getTimestamp() + "@emailtester.com";
		String password = "password01";
		User u = new User((long) user.getId(), email, password, password);
		System.out.print("Updating user...");

		UserUpdateResponse response = TestConfiguration.GATEWAY.updateUser(u).send();

		assertNotNull("UserUpdate: Response not returned.", response);
		System.out.print("DONE\n");
		return response;
	}
	
	private UserAuthenticateResponse authenticateUser(Long expectedUserId)
	{
		String password = "password01";
		User user = new User(expectedUserId);
		user.setPassword(password);
		System.out.print("Authenticating user...");

		UserAuthenticateResponse response = TestConfiguration.GATEWAY.authenticateUser(user).send();

		Assert.assertEquals("UserAuthenticate: Unexpected user id.", expectedUserId, response.getUser().getId());
		System.out.print("DONE\n");
		return response;
	}

	private CardCreateResponse createCard(User user, String cardNumber, boolean threeds)
	{
		Address address = new Address("8 Exchange Quay", "Manchester", "M5 3EJ", "GB");
		Card card = new Card("App55 User", cardNumber, TestUtil.getTimestamp("MM/yyyy", 90), "111", null, address);

		System.out.print("Creating card...");
		CardCreateResponse response = TestConfiguration.GATEWAY.createCard(new User((long) user.getId()), card, threeds).send();

		Assert.assertEquals("CardCreate: Unexpected card expiry.", card.getExpiry(), response.getCard().getExpiry());
		String token = response.getCard().getToken();
		assertNotNull("CardCreate: Unexpected card token.",token);
		System.out.print("DONE (card-token " + token + ")\n");
		return response;
	}

	private CardListResponse listCards(User user, int expectedNumber)
	{
		System.out.print("Listing cards...");
		CardListResponse response = TestConfiguration.GATEWAY.listCards(new User((long) user.getId())).send();
		int size = response.getCards().size();
		assertTrue("CardList: Incorrect card list size.", size == expectedNumber);
		System.out.print("DONE (" + size + " cards)\n");
		return response;
	}

	private CardDeleteResponse deleteCard(User user, Card card)
	{
		System.out.print("Deleting card " + card.getToken() + "...");

		CardDeleteResponse response = TestConfiguration.GATEWAY.deleteCard(new User((long) user.getId()), new Card(card.getToken())).send();

		assertNotNull("CardDelete: Response not returned.", response);
		System.out.print("DONE\n");
		return response;
	}

	private TransactionCreateResponse createTransaction(User user, Card card)
	{
		Transaction transaction = new Transaction("0.10", "GBP", null);
		Card c = new Card(card.getToken());
		System.out.print("Creating transaction...");

		TransactionCreateResponse response = TestConfiguration.GATEWAY.createTransaction(new User((long) user.getId()), c, transaction).send();

		Assert.assertEquals("TransactionCreate: Unexpected amount.", transaction.getAmount(), response.getTransaction().getAmount());
		Assert.assertEquals("TransactionCreate: Unexpected transaction code.", "succeeded", response.getTransaction().getCode());
		System.out.print("DONE (transaction-id " + response.getTransaction().getId() + ")\n");
		return response;
	}
	
	private TransactionCreateResponse create3DTransaction(User user, Card card, boolean commit)
	{
		Transaction transaction = new Transaction("0.10", "GBP", null);
		transaction.setCommit(commit);
		Card c = new Card(card.getToken());
		System.out.print("Creating transaction...");
		
		TransactionCreateResponse response = TestConfiguration.GATEWAY.createTransaction(new User((long) user.getId()), c, transaction, true).send();

		if (card.getNumber().equals("454313******1116")) {
			Assert.assertNull("Non-enrolled TransactionCreate: expected threeDSecureRedirectUrl to be null", response.getThreeDSecureRedirectUrl());
		}
		else {
			Assert.assertNotNull("3D TransactionCreate: Expected threeDSecureRedirectUrl.", response.getThreeDSecureRedirectUrl());
		}
		System.out.print("DONE (transaction-id " + response.getTransaction().getId() + ")\n");
		return response;
	}
	
	private TransactionCreateResponse createGuestTransaction(String cardNumber, boolean commit, boolean threeds)
	{
		String email = "example." + TestUtil.getTimestamp() + "@javalibtester.com";
		
		Address address = new Address("8 Exchange Quay", "Manchester", "M5 3EJ", "GB");
		Card c = new Card("App55 User", cardNumber, TestUtil.getTimestamp("MM/yyyy", 90), "111", null, address);
		
		User u = new User(email);
		
		Transaction t = new Transaction("0.10", "EUR", null);
		t.setCommit(commit);
		System.out.print("Creating transaction...");
		
		TransactionCreateResponse response = TestConfiguration.GATEWAY.createTransaction(u, c, t, threeds).send();

		Assert.assertEquals("3D TransactionCreate: Unexpected amount.", t.getAmount(), response.getTransaction().getAmount());
		Assert.assertNull("3D TransactionCreate: Expected transaction code is null.", response.getTransaction().getCode());
		if (!threeds) {
			Assert.assertNull("Non-enrolled TransactionCreate: expected threeDSecureRedirectUrl to be null", response.getThreeDSecureRedirectUrl());
		}
		else {
			Assert.assertNotNull("3D TransactionCreate: Expected threeDSecureRedirectUrl.", response.getThreeDSecureRedirectUrl());
		}
		System.out.print("DONE (transaction-id " + response.getTransaction().getId() + ")\n");
		return response;
	}

	private TransactionCommitResponse commitTransaction(Transaction transaction)
	{
		System.out.print("Committing transaction..");

		TransactionCommitResponse response = TestConfiguration.GATEWAY.commitTransaction(new Transaction(transaction.getId())).send();

		Assert.assertEquals("TransactionCommit: Unexpected transaction code.", "succeeded", response.getTransaction().getCode());
		assertNotNull("TransactionCommit: Unexpected transaction auth code.", response.getTransaction().getAuthCode());
		System.out.print("DONE\n");
		return response;
	}

	private TransactionCancelResponse cancelTransaction(User user, Transaction transaction)
	{
		System.out.print("Cancelling transaction...");
		TransactionCancelResponse response = null;
		try {
			response = TestConfiguration.GATEWAY.cancelTransaction(new User(user.getId()), new Transaction(String.valueOf(transaction.getId()))).send();
			assertNotNull("TransactionCancel: Response is null.", response);
			System.out.print("DONE\n");
			return response;
		}
		catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	private ScheduleCreateResponse createSchedule(User user, Card card, String amount)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String start = format.format(new Date());
		System.out.print("Creating schedule...");

		// Remove extra data.
		Card cardTokenOnly = new Card();
		cardTokenOnly.setToken(card.getToken());

		Transaction transaction = new Transaction(amount, "EUR", "Scheduled Transaction");
		Schedule schedule = new Schedule(Schedule.TIMEUNIT_DAILY, start);

		ScheduleCreateResponse response = TestConfiguration.GATEWAY.createSchedule(user, cardTokenOnly, transaction, schedule).send();

		assertNotNull("ScheduleCreate: Response not returned.", response);
		System.out.print("DONE (schedule " + response.getSchedule().getId() + ")\n");
		return response;
	}

	private ScheduleGetResponse getSchedule(User user, Schedule schedule)
	{
		System.out.print("Getting schedule...");

		// Remove extra data.
		Schedule basicSchedule = new Schedule();
		basicSchedule.setId(schedule.getId());
		User basicUser = new User();
		basicUser.setId(user.getId());

		ScheduleGetResponse response = TestConfiguration.GATEWAY.getSchedule(basicUser, basicSchedule).send();

		Assert.assertEquals("ScheduleGet: Unexpected schedule id.", schedule.getId(), response.getSchedule().getId());
		System.out.print("DONE\n");
		return response;
	}

	private ScheduleUpdateResponse updateSchedule(User user, Card card, Schedule schedule)
	{
		System.out.print("Updating schedule...");

		// Remove extra data.
		User basicUser = new User();
		basicUser.setId(user.getId());
		Card basicCard = new Card();
		basicCard.setToken(card.getToken());

		ScheduleUpdateResponse response = TestConfiguration.GATEWAY.updateSchedule(basicUser, basicCard, schedule).send();

		assertNotNull("ScheduleUpdate: Response not returned.", response);
		System.out.print("DONE\n");
		return response;
	}

	private ScheduleListResponse listSchedules(User user, Boolean active)
	{
		System.out.print("Listing schedules...");

		// Remove extra data.
		User basicUser = new User();
		basicUser.setId(user.getId());

		ScheduleListResponse response = TestConfiguration.GATEWAY.listSchedule(basicUser, active).send();

		assertNotNull("ScheduleList: Response not returned.", response);
		System.out.print("DONE (" + response.getSchedules().size() + " schedules)\n");
		return response;
	}

	private ScheduleDeleteResponse deleteSchedule(User user, Schedule schedule)
	{
		System.out.print("Deleting schedule...");

		// Remove extra data.
		User basicUser = new User();
		basicUser.setId(user.getId());
		Schedule basicSchedule = new Schedule();
		basicSchedule.setId(schedule.getId());

		ScheduleDeleteResponse response = TestConfiguration.GATEWAY.deleteSchedule(basicUser, basicSchedule).send();

		assertNotNull("ScheduleDelete: Response not returned.", response);
		System.out.print("DONE\n");
		return response;
	}
	
	private void multipleTransactions() {
		System.out.print("multipleTransactions not yet implemented\n");
	}
	private void duplicateTransactions() {
		System.out.print("duplicateTransactions not yet implemented\n");
	}
	private void duplicateGuestTransactions() {
		System.out.print("duplicateGuestTransactions not yet implemented\n");
	}
}