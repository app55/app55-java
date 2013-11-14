package com.app55.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.app55.domain.User;
import com.app55.error.ApiException;
import com.app55.message.ResponseListener;
import com.app55.message.UserCreateResponse;
import com.app55.test.util.TestUtil;

public class AsyncTest implements ResponseListener<UserCreateResponse>
{
	String				email		= "example." + TestUtil.getTimestamp() + "@javalibtester.com";
	String				phone		= "0123 456 7890";
	String				password	= "pa55word";

	boolean				returned	= false;
	UserCreateResponse	response;

	@Test
	public void testOne() throws Exception
	{
		System.out.println("\nUserCreate: " + email);

		TestConfiguration.GATEWAY.createUser(new User(email, phone, password, password)).send(this);

		while (!returned)
			Thread.sleep(500);

		System.out.println("UserCreated:" + response.getUser().getId());
		assertNotNull("UserCreate: response was null", response);
		assertEquals("UserCreate: Unexpected email.", email, response.getUser().getEmail());
		assertEquals("UserCreate: Unexpected phone.", phone, response.getUser().getPhone());
		System.out.println("UserCreate: SUCCESS");
	}

	@Override
	public void onResponse(UserCreateResponse response)
	{
		returned = true;
		this.response = response;
	}

	@Override
	public void onError(ApiException e)
	{
		returned = true;
	}
}
