package com.app55.test;

import org.junit.runner.JUnitCore;

public class RunTests
{
	private static Class<?>[]	TESTS	= { DescribeTest.class, PopulateTest.class, IntegrationTest.class, AsyncTest.class };

	/**
	 * Execute this test via the command line if you wish.
	 */
	public static void main(String[] args)
	{
		new JUnitCore().run(TESTS);
	}
}