package com.app55.converter;

interface IConverter
{
	boolean canConvert(Object o);

	String convert(Object o);

	<T> T convert(String s, Class<T> type);
}