package com.app55.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

/**
 * Singleton wrapper class which configures the Jackson JSON parser.
 */
public final class JsonUtil
{
	private static ObjectMapper	MAPPER;

	private static ObjectMapper get()
	{
		if (MAPPER == null)
		{
			MAPPER = new ObjectMapper();

			// This is useful in case new object properties are added in the future.
			MAPPER.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		}

		return MAPPER;
	}

	public static String string(Object data)
	{
		try
		{
			return get().writeValueAsString(data);
		}
		catch (Exception e)
		{
			return null;
		}
	}

	public static <T> T objectOrThrow(String data, Class<T> type) throws JsonParseException, JsonMappingException, IOException
	{
		return get().readValue(data, type);
	}

	public static <T> T object(String data, Class<T> type)
	{
		try
		{
			return objectOrThrow(data, type);
		}
		catch (Exception e)
		{
			return null;
		}
	}

	public static Map<String, Object> mapOrThrow(String data) throws JsonParseException, JsonMappingException, IOException
	{
		return get().readValue(data, new TypeReference<HashMap<String, Object>>() {});
	}

	public static Map<String, Object> map(String data)
	{
		try
		{
			return mapOrThrow(data);
		}
		catch (Exception e)
		{
			return null;
		}
	}
}