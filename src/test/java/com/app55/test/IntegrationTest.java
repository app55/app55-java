package com.app55.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.app55.domain.Address;
import com.app55.domain.Card;
import com.app55.domain.Transaction;
import com.app55.domain.User;
import com.app55.message.CardCreateResponse;
import com.app55.message.CardDeleteResponse;
import com.app55.message.CardListResponse;
import com.app55.message.TransactionCommitResponse;
import com.app55.message.TransactionCreateResponse;
import com.app55.message.UserAuthenticateResponse;
import com.app55.message.UserCreateResponse;
import com.app55.message.UserUpdateResponse;
import com.app55.test.util.TestUtil;

/**
 * This test flow provides a basic test of the java library against a sandbox account
 *  with api credentials defined in environment variables (APP55_API_KEY, APP55_API_SECRET) or in the file TestConfiguration.
 * Please ensure these variables are setup correctly before attempting to run the tests.
 */
public class IntegrationTest
{
	@Test
	public void test() throws Exception
	{
		User user = createUser().getUser();

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

		for (Card card : cards)
			deleteCard(user, card);

		listCards(user, 0);

		updateUser(user);
		authenticateUser(user.getId());
	}

	private UserCreateResponse createUser()
	{
		String email = "example." + TestUtil.getTimestamp() + "@javalibtester.com";
		String phone = "0123 456 7890";
		String password = "pa55word";
		System.out.println("\nUserCreate: " + email);

		UserCreateResponse response = TestConfiguration.GATEWAY.createUser(new User(email, phone, password, password)).send();

		System.out.println("UserCreated:" + response.getUser().getId());
		assertEquals("UserCreate: Email", email, response.getUser().getEmail());
		assertEquals("UserCreate: Phone", phone, response.getUser().getPhone());
		System.out.println("UserCreate: SUCCESS");
		return response;
	}

	private CardCreateResponse createCard(User user)
	{
		Address address = new Address("8 Exchange Quay", "Manchester", "M5 3EJ", "GB");
		Card card = new Card("App55 User", "4111111111111111", TestUtil.getTimestamp("MM/yyyy", 90), "111", null, address);

		System.out.println("\nCardCreate: " + card.getNumber());
		CardCreateResponse response = TestConfiguration.GATEWAY.createCard(new User((long) user.getId()), card).send();

		assertEquals("CardCreate: Card Expiry", card.getExpiry(), response.getCard().getExpiry());
		assertNotNull("CardCreate: Card Token", response.getCard().getToken());
		System.out.println("CardCreate: SUCCESS");
		return response;
	}

	private CardListResponse listCards(User user, int expectedNumber)
	{
		System.out.println("\nCardList: " + user.getId());
		CardListResponse response = TestConfiguration.GATEWAY.listCards(new User((long) user.getId())).send();

		assertTrue("CardList: Card List Size", response.getCards().size() == expectedNumber);
		System.out.println("CardList: SUCCESS");
		return response;
	}

	private CardDeleteResponse deleteCard(User user, Card card)
	{
		System.out.println("\nCardDelete: " + card.getToken());

		CardDeleteResponse response = TestConfiguration.GATEWAY.deleteCard(new User((long) user.getId()), new Card(card.getToken())).send();

		assertNotNull("CardDelete: Response returned.", response);
		System.out.println("CardDelete: SUCCESS");
		return response;
	}

	private TransactionCreateResponse createTransaction(User user, Card card)
	{
		Transaction transaction = new Transaction("0.10", "GBP", null);
		Card c = new Card(card.getToken());
		System.out.println("\nTransactionCreate: " + transaction.getAmount() + transaction.getCurrency());

		TransactionCreateResponse response = TestConfiguration.GATEWAY.createTransaction(new User((long) user.getId()), c, transaction).send();

		assertEquals("TransactionCreate: Amount", transaction.getAmount(), response.getTransaction().getAmount());
		assertEquals("TransactionCreate: Code", "succeeded", response.getTransaction().getCode());
		System.out.println("TransactionCreate: SUCCESS");
		return response;
	}

	private TransactionCommitResponse commitTransaction(Transaction transaction)
	{
		System.out.println("\nTransactionCommit: " + transaction.getAmount());

		TransactionCommitResponse response = TestConfiguration.GATEWAY.commitTransaction(new Transaction(transaction.getId())).send();

		assertEquals("TransactionCommit: Code", "succeeded", response.getTransaction().getCode());
		assertNotNull("TransactionCommit: Auth Code", response.getTransaction().getAuthCode());
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

		assertNotNull("UserUpdate: Response returned.", response);
		System.out.println("UserUpdate: SUCCESS");
		return response;
	}

	private UserAuthenticateResponse authenticateUser(Long expectedUserId)
	{
		String password = "password01";
		User user = new User(expectedUserId);
		user.setPassword(password);
		System.out.println("\nUserAuthenticate: " + user.getId());

		UserAuthenticateResponse response = TestConfiguration.GATEWAY.authenticateUser(user).send();

		assertEquals("UserAuthenticate: Id", expectedUserId, response.getUser().getId());
		System.out.println("UserAuthenticate: SUCCESS");
		return response;
	}
}