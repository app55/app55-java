package com.app55.test.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TestUtil
{
	public static String getTimestamp(String format, Integer daysInFuture)
	{
		Calendar c = Calendar.getInstance();
		if (daysInFuture != null)
			c.add(Calendar.DAY_OF_YEAR, daysInFuture);
		return new SimpleDateFormat(format).format(c.getTime());
	}

	public static String getTimestamp()
	{
		return getTimestamp("yyyyMMddHHmmss", null);
	}
}