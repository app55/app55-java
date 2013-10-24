package com.app55.converter;

import java.util.ArrayList;
import java.util.List;

public class Converter implements IConverter
{
	private List<IConverter>	converters;

	private Converter()
	{
		converters = new ArrayList<IConverter>();
		register(new PrimitiveConverter());
	}

	private static Converter	instance;

	public static Converter getInstance()
	{
		if (instance == null)
			instance = new Converter();
		return instance;
	}

	private void register(IConverter converter)
	{
		if (converter == this)
			throw new IllegalArgumentException();
		converters.add(converter);
	}

	public boolean canConvert(Object o)
	{
		for (IConverter converter : converters)
			if (converter.canConvert(o))
				return true;

		return false;
	}

	public String convert(Object o)
	{
		if (o == null)
			return null;

		for (IConverter converter : converters)
			if (converter.canConvert(o))
				return converter.convert(o);

		return null;
	}

	public <T> T convert(String s, Class<T> type)
	{
		for (IConverter converter : converters)
			if (converter.canConvert(s))
				return converter.convert(s, type);

		return null;
	}
}