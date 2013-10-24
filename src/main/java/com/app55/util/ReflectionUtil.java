package com.app55.util;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public final class ReflectionUtil
{
	public static Map<String, PropertyDescriptor> getAllProperties(Object o)
	{
		return getAllProperties(o, null);
	}

	private static Map<String, PropertyDescriptor> getAllProperties(Object o, Map<String, Boolean> exclude)
	{
		try
		{
			PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(o.getClass()).getPropertyDescriptors();
			Map<String, PropertyDescriptor> properties = new HashMap<String, PropertyDescriptor>();

			for (PropertyDescriptor p : propertyDescriptors)
			{
				if ("class".equals(p.getName()))
					continue;

				if (p.getReadMethod().getAnnotation(JsonIgnore.class) != null)
					continue;

				String name = p.getName();
				JsonProperty altName = p.getReadMethod().getAnnotation(JsonProperty.class);
				if (altName != null && altName.value().length() > 0)
					name = altName.value();

				if (exclude != null && exclude.containsKey(name) && exclude.get(name))
					continue;

				properties.put(name, p);
			}

			return properties;
		}
		catch (IntrospectionException e)
		{
			return null;
		}
	}

	public static Object invokeMethod(String name, Object object, Class<?>[] parameterTypes, Object[] params)
	{
		try
		{
			Method m = getMethod(name, object.getClass(), parameterTypes);
			m.setAccessible(true);
			return m.invoke(object, params);
		}
		catch (Exception e)
		{
			return null;
		}
	}

	public static Object getMember(String name, Object object)
	{
		try
		{
			Field f = getField(name, object.getClass());
			if (f == null)
				return null;

			f.setAccessible(true);
			return f.get(object);
		}
		catch (Exception e)
		{
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	private static Field getField(String fieldName, Class clazz) throws NoSuchFieldException
	{
		try
		{
			return clazz.getDeclaredField(fieldName);
		}
		catch (NoSuchFieldException e)
		{
			Class superClass = clazz.getSuperclass();
			if (superClass == null)
			{
				throw e;
			}
			else
			{
				return getField(fieldName, superClass);
			}
		}
	}

	private static Method getMethod(String methodName, Class clazz, Class<?>[] parameterTypes) throws NoSuchMethodException
	{
		try
		{
			return clazz.getDeclaredMethod(methodName, parameterTypes);
		}
		catch (NoSuchMethodException e)
		{
			Class superClass = clazz.getSuperclass();
			if (superClass == null)
			{
				throw e;
			}
			else
			{
				return getMethod(methodName, superClass, parameterTypes);
			}
		}
	}
}