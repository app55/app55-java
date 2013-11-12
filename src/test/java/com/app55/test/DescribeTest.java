package com.app55.test;

import com.app55.domain.Address;
import com.app55.domain.Card;
import com.app55.domain.User;
import com.app55.message.CardCreateRequest;
import com.app55.message.CardListResponse;
import com.app55.message.UserCreateRequest;
import com.app55.util.ReflectionUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class DescribeTest
{
	@Test
	@SuppressWarnings("unchecked")
	public void testOne()
	{
		Long id = 1234L;
		String email = "test@gmail.com";

		UserCreateRequest req = new UserCreateRequest();

		User user = new User();
		user.setEmail(email);
		user.setId(1234L);
		req.setUser(user);

		Map<String, String> description = (Map<String, String>) ReflectionUtil.invokeMethod("describe", req, new Class<?>[] { Object.class, String.class,
				Map.class }, new Object[] { req, null, null });

		assertNotNull("timestamp presence.", description.get("ts"));
		assertEquals("user.email presence.", email, description.get("user.email"));
		assertEquals("user.email presence.", String.valueOf(id), description.get("user.id"));

		// Test exclude
		Map<String, Boolean> exclude = new HashMap<String, Boolean>();
		exclude.put("ts", true);

		description = (Map<String, String>) ReflectionUtil.invokeMethod("describe", req, new Class<?>[] { Object.class, String.class, Map.class },
				new Object[] { req, null, exclude });

		assertNull("timestamp absence.", description.get("ts"));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testTwo()
	{
		String number = "6786568512345675";
		String city = "London";

		CardCreateRequest req = new CardCreateRequest();

		Card card = new Card();
		card.setNumber(number);

		Address address = new Address();
		address.setCity(city);

		card.setAddress(address);
		req.setCard(card);

		Map<String, String> description = (Map<String, String>) ReflectionUtil.invokeMethod("describe", req, new Class<?>[] { Object.class, String.class,
				Map.class }, new Object[] { req, null, null });

		assertEquals("card.number presence.", number, description.get("card.number"));
		assertEquals("card.address.city presence.", city, description.get("card.address.city"));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testThree()
	{
		String number = "6786568512345675";

		CardListResponse req = new CardListResponse();

		Card card = new Card();
		card.setNumber(number);

		req.setCards(new ArrayList<Card>());
		for (int i = 0; i < 3; i++)
		{
			req.getCards().add(card);
		}

		Map<String, String> description = (Map<String, String>) ReflectionUtil.invokeMethod("describe", req, new Class<?>[] { Object.class, String.class,
				Map.class }, new Object[] { req, null, null });

		assertEquals("card.0.number presence.", number, description.get("cards.0.number"));
		assertEquals("cards.3.number presence.", number, description.get("cards.2.number"));
		assertNull("cards.4.number absence.", description.get("cards.3.number"));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testEmptyDescription()
	{
		UserCreateRequest req = new UserCreateRequest();
		Map<String, String> description = (Map<String, String>) ReflectionUtil.invokeMethod("describe", req, new Class<?>[] { Object.class },
				new Object[] { null });

		assertEquals("Empty map.", 0, description.size());
	}
}