package com.app55.test;

import com.app55.message.Message;
import com.app55.message.TransactionCreateResponse;
import com.app55.util.ReflectionUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class PopulateTest
{
	@Test
	public void testOne()
	{
		String email = "test@gmail.com";

		TransactionCreateResponse res = new TransactionCreateResponse();
		Map<String, Object> table = new HashMap<String, Object>();

		Map<String, Object> userTable = new HashMap<String, Object>();
		userTable.put("email", email);
		userTable.put("id", null);
		table.put("user", userTable);

		ReflectionUtil.invokeMethod("populate", res, new Class<?>[]{ Map.class, Object.class }, new Object[]{ table, null });
		Map<String, String> additionalFields = getAdditionalFields(res);

		assertEquals("user.email additional fields presence.", email, additionalFields.get("user.email"));
	}

	@Test
	public void testTwo()
	{
		String amount = "2.50";
		String currency = "USD";
		String other = "other";

		TransactionCreateResponse res = new TransactionCreateResponse();
		Map<String, Object> table = new HashMap<String, Object>();

		table.put("other", other);
		table.put("otherTwo", null);

		Map<String, Object> transactionTable = new HashMap<String, Object>();
		transactionTable.put("amount", amount);
		transactionTable.put("currency", currency);
		table.put("transaction", transactionTable);

		ReflectionUtil.invokeMethod("populate", res, new Class<?>[]{ Map.class, Object.class }, new Object[]{ table, res });
		Map<String, String> additionalFields = getAdditionalFields(res);

		assertEquals("other additional fields presence.", other, additionalFields.get("other"));
		assertNull("otherTwo additional fields absence.", additionalFields.get("otherTwo"));
	}

	@Test
	public void testThree()
	{
		TransactionCreateResponse res = new TransactionCreateResponse();
		Map<String, Object> table = new HashMap<String, Object>();

		List<String> values = new ArrayList<String>();
		values.add("123");
		table.put("values", values);

		List<Map<String, Object>> mapValues = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapSet = new HashMap<String, Object>();
		mapSet.put("entry", "value");
		mapValues.add(mapSet);
		table.put("entryTest", mapValues);

		ReflectionUtil.invokeMethod("populate", res, new Class<?>[]{ Map.class, Object.class }, new Object[]{ table, null });
		Map<String, String> additionalFields = getAdditionalFields(res);

		assertNotNull("values list additional fields presence.", additionalFields.get("values.0"));
		assertNotNull("map of list additional fields presence.", additionalFields.get("entryTest.0.entry"));
	}

	@SuppressWarnings("unchecked")
	private Map<String, String> getAdditionalFields(Message message)
	{
		return (Map<String, String>) ReflectionUtil.getMember("additionalFields", message);
	}
}