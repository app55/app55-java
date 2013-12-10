package com.app55.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import com.app55.message.ScheduleCreateResponse;
import com.app55.message.ScheduleDeleteResponse;
import com.app55.message.ScheduleGetResponse;
import com.app55.message.ScheduleListResponse;
import com.app55.message.ScheduleUpdateResponse;
import com.app55.message.TransactionCommitResponse;
import com.app55.message.TransactionCreateResponse;
import com.app55.message.UserAuthenticateResponse;
import com.app55.message.UserCreateResponse;
import com.app55.message.UserGetResponse;
import com.app55.message.UserUpdateResponse;
import com.app55.test.util.TestUtil;

/**
 * This test flow provides a basic test of the java library against a sandbox account with api credentials defined in environment variables (APP55_API_KEY,
 * APP55_API_SECRET) or in the file TestConfiguration. Please ensure these variables are setup correctly before attempting to run the tests.
 */
public class IntegrationTest
{
	@Test
	public void test() throws Exception
	{
		User user = createUser().getUser();
		checkGetUser(user);

		Card card1 = createCard(user).getCard();
		Transaction transaction = createTransaction(user, card1).getTransaction();
		commitTransaction(transaction);

		Card card2 = createCard(user).getCard();
		transaction = createTransaction(user, card2).getTransaction();
		commitTransaction(transaction);

		Card card3 = createCard(user).getCard();
		transaction = createTransaction(user, card3).getTransaction();
		commitTransaction(transaction);

		List<Card> cards = listCards(user, 3).getCards();

		int count = 0;
		Card workingCard = null;
		for (Card card : cards)
		{
			if (++count > 2)
			{
				workingCard = card;
				break;
			}

			deleteCard(user, card);
		}

		listCards(user, 1);

		updateUser(user);
		authenticateUser(user.getId());

		Schedule schedule1 = createSchedule(user, workingCard).getSchedule();
		getSchedule(user, schedule1).getSchedule();

		ScheduleGetResponse response = getSchedule(user, schedule1);
		Assert.assertEquals(workingCard.getToken(), response.getCard().getToken());

		Schedule schedule2 = createSchedule(user, workingCard, "0.12").getSchedule();
		Schedule schedule3 = createSchedule(user, workingCard, "0.13").getSchedule();
		
		schedule3.setEnd("10-10-2016");
		updateSchedule(user, workingCard, schedule3);
		schedule3 = getSchedule(user, schedule3).getSchedule();

		ScheduleListResponse listResponse = listSchedules(user, null);
		assertTrue("List does not contain expected schedule1", listResponse.getSchedules().contains(schedule1));
		assertTrue("List does not contain expected schedule2", listResponse.getSchedules().contains(schedule2));
		assertTrue("List does not contain expected schedule3", listResponse.getSchedules().contains(schedule3));
		assertEquals("List is not the correct length", 3, listResponse.getSchedules().size());

		deleteSchedule(user, schedule1);
		deleteSchedule(user, schedule2);
		// deleteSchedule(user, schedule3);

		listResponse = listSchedules(user, null);
		assertEquals("Schedule list not expected size.", 3, listResponse.getSchedules().size());
		listResponse = listSchedules(user, true);
		assertEquals("Active schedule list not expected size.", 1, listResponse.getSchedules().size());
	}

	private UserCreateResponse createUser()
	{
		String email = "example." + TestUtil.getTimestamp() + "@javalibtester.com";
		String phone = "0123 456 7890";
		String password = "pa55word";
		System.out.println("\nUserCreate: " + email);

		UserCreateResponse response = TestConfiguration.GATEWAY.createUser(new User(email, phone, password, password)).send();

		System.out.println("UserCreated:" + response.getUser().getId());
		assertEquals("UserCreate: Unexpected email.", email, response.getUser().getEmail());
		assertEquals("UserCreate: Unexpected phone.", phone, response.getUser().getPhone());
		System.out.println("UserCreate: SUCCESS");
		return response;
	}

	private CardCreateResponse createCard(User user)
	{
		Address address = new Address("8 Exchange Quay", "Manchester", "M5 3EJ", "GB");
		Card card = new Card("App55 User", "4111111111111111", TestUtil.getTimestamp("MM/yyyy", 90), "111", null, address);

		System.out.println("\nCardCreate: " + card.getNumber());
		CardCreateResponse response = TestConfiguration.GATEWAY.createCard(new User((long) user.getId()), card).send();

		Assert.assertEquals("CardCreate: Unexpected card expiry.", card.getExpiry(), response.getCard().getExpiry());
		assertNotNull("CardCreate: Unexpected card token.", response.getCard().getToken());
		System.out.println("CardCreate: SUCCESS");
		return response;
	}

	private CardListResponse listCards(User user, int expectedNumber)
	{
		System.out.println("\nCardList: " + user.getId());
		CardListResponse response = TestConfiguration.GATEWAY.listCards(new User((long) user.getId())).send();

		assertTrue("CardList: Incorrect card list size.", response.getCards().size() == expectedNumber);
		System.out.println("CardList: SUCCESS");
		return response;
	}

	private CardDeleteResponse deleteCard(User user, Card card)
	{
		System.out.println("\nCardDelete: " + card.getToken());

		CardDeleteResponse response = TestConfiguration.GATEWAY.deleteCard(new User((long) user.getId()), new Card(card.getToken())).send();

		assertNotNull("CardDelete: Response not returned.", response);
		System.out.println("CardDelete: SUCCESS");
		return response;
	}

	private TransactionCreateResponse createTransaction(User user, Card card)
	{
		Transaction transaction = new Transaction("0.10", "GBP", null);
		Card c = new Card(card.getToken());
		System.out.println("\nTransactionCreate: " + transaction.getAmount() + transaction.getCurrency());

		TransactionCreateResponse response = TestConfiguration.GATEWAY.createTransaction(new User((long) user.getId()), c, transaction).send();

		Assert.assertEquals("TransactionCreate: Unexpected amount.", transaction.getAmount(), response.getTransaction().getAmount());
		Assert.assertEquals("TransactionCreate: Unexpected transaction code.", "succeeded", response.getTransaction().getCode());
		System.out.println("TransactionCreate: SUCCESS");
		return response;
	}

	private TransactionCommitResponse commitTransaction(Transaction transaction)
	{
		System.out.println("\nTransactionCommit: " + transaction.getAmount());

		TransactionCommitResponse response = TestConfiguration.GATEWAY.commitTransaction(new Transaction(transaction.getId())).send();

		Assert.assertEquals("TransactionCommit: Unexpected transaction code.", "succeeded", response.getTransaction().getCode());
		assertNotNull("TransactionCommit: Unexpected transaction auth code.", response.getTransaction().getAuthCode());
		System.out.println("TransactionCommit: SUCCESS");
		return response;
	}

	private UserUpdateResponse updateUser(User user)
	{
		String email = "example." + TestUtil.getTimestamp() + "@emailtester.com";
		String password = "password01";
		User u = new User((long) user.getId(), email, password, password);
		System.out.println("\nUserUpdate: " + u.getId());

		UserUpdateResponse response = TestConfiguration.GATEWAY.updateUser(u).send();

		assertNotNull("UserUpdate: Response not returned.", response);
		System.out.println("UserUpdate: SUCCESS");
		return response;
	}

	private void checkGetUser(User user)
	{
		System.out.println("\nUserGet: " + user.getId());

		// Remove extra data.
		User basicUser = new User();
		basicUser.setId(user.getId());

		UserGetResponse response = TestConfiguration.GATEWAY.getUser(basicUser).send();

		Assert.assertEquals("UserGet: Returned user not not as expected.", user.getId(), response.getUser().getId());
		System.out.println("UserGet: SUCCESS");
	}

	private UserAuthenticateResponse authenticateUser(Long expectedUserId)
	{
		String password = "password01";
		User user = new User(expectedUserId);
		user.setPassword(password);
		System.out.println("\nUserAuthenticate: " + user.getId());

		UserAuthenticateResponse response = TestConfiguration.GATEWAY.authenticateUser(user).send();

		Assert.assertEquals("UserAuthenticate: Unexpected user id.", expectedUserId, response.getUser().getId());
		System.out.println("UserAuthenticate: SUCCESS");
		return response;
	}

	private ScheduleCreateResponse createSchedule(User user, Card card)
	{
		return createSchedule(user, card, "0.10");
	}

	private ScheduleCreateResponse createSchedule(User user, Card card, String amount)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String start = format.format(new Date());
		System.out.println("\nScheduleCreate: " + user.getId() + " " + card.getToken() + " " + amount);

		// Remove extra data.
		Card cardTokenOnly = new Card();
		cardTokenOnly.setToken(card.getToken());

		Transaction transaction = new Transaction(amount, "EUR", "Scheduled Transaction");
		Schedule schedule = new Schedule(Schedule.TIMEUNIT_DAILY, start);

		ScheduleCreateResponse response = TestConfiguration.GATEWAY.createSchedule(user, cardTokenOnly, transaction, schedule).send();

		assertNotNull("ScheduleCreate: Response not returned.", response);
		System.out.println("ScheduleCreate: SUCCESS");
		return response;
	}

	private ScheduleGetResponse getSchedule(User user, Schedule schedule)
	{
		System.out.println("\nScheduleGet: " + schedule.getId());

		// Remove extra data.
		Schedule basicSchedule = new Schedule();
		basicSchedule.setId(schedule.getId());
		User basicUser = new User();
		basicUser.setId(user.getId());

		ScheduleGetResponse response = TestConfiguration.GATEWAY.getSchedule(basicUser, basicSchedule).send();

		Assert.assertEquals("ScheduleGet: Unexpected schedule id.", schedule.getId(), response.getSchedule().getId());
		System.out.println("ScheduleGet: SUCCESS");
		return response;
	}

	private ScheduleUpdateResponse updateSchedule(User user, Card card, Schedule schedule)
	{
		System.out.println("\nScheduleUpdate: " + schedule.getId());

		// Remove extra data.
		User basicUser = new User();
		basicUser.setId(user.getId());
		Card basicCard = new Card();
		basicCard.setToken(card.getToken());

		ScheduleUpdateResponse response = TestConfiguration.GATEWAY.updateSchedule(basicUser, basicCard, schedule).send();

		assertNotNull("ScheduleUpdate: Response not returned.", response);
		System.out.println("ScheduleUpdate: SUCCESS");
		return response;
	}

	private ScheduleListResponse listSchedules(User user, Boolean active)
	{
		System.out.println("\nScheduleList: " + user.getId());

		// Remove extra data.
		User basicUser = new User();
		basicUser.setId(user.getId());

		ScheduleListResponse response = TestConfiguration.GATEWAY.listSchedule(basicUser, active).send();

		assertNotNull("ScheduleList: Response not returned.", response);
		System.out.println("ScheduleList: SUCCESS");
		return response;
	}

	private ScheduleDeleteResponse deleteSchedule(User user, Schedule schedule)
	{
		System.out.println("\nScheduleDelete: " + schedule.getId());

		// Remove extra data.
		User basicUser = new User();
		basicUser.setId(user.getId());
		Schedule basicSchedule = new Schedule();
		basicSchedule.setId(schedule.getId());

		ScheduleDeleteResponse response = TestConfiguration.GATEWAY.deleteSchedule(basicUser, basicSchedule).send();

		assertNotNull("ScheduleDelete: Response not returned.", response);
		System.out.println("ScheduleDelete: SUCCESS");
		return response;
	}
}