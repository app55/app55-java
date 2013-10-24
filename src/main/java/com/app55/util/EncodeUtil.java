package com.app55.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncodeUtil
{
	public static String createBasicAuthString(String username, String password)
	{
		String toEncode = username + ":" + password;
		return "Basic " + base64(toEncode.getBytes());
	}

	public static byte[] sha1(final String data)
	{
		try
		{
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			return md.digest(data.getBytes("UTF-8"));
		}
		catch (final NoSuchAlgorithmException e)
		{
			// Will never happen.
		}
		catch (UnsupportedEncodingException e)
		{
			// Will never happen.
		}

		return null;
	}

	public static String base64(byte[] data)
	{
		return Base64.encodeBytes(data, Base64.DONT_BREAK_LINES | Base64.URL_SAFE);
	}
}