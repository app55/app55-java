package com.app55.converter;

class PrimitiveConverter implements IConverter
{
	public boolean canConvert(Object o)
	{
		return isPrimitive(o);
	}

	private boolean isPrimitive(Object o)
	{
		return o instanceof Boolean || o instanceof Character || o instanceof Byte || o instanceof Short || o instanceof Integer || o instanceof Long
				|| o instanceof Float || o instanceof Double || o instanceof Void || o instanceof String;
	}

	public String convert(Object o)
	{
		if (o == null)
			return null;
		if (!canConvert(o))
			return null;
		if (Boolean.class.equals(o.getClass()))
			return o.toString().toLowerCase();

		return o.toString();
	}

	@SuppressWarnings("unchecked")
	public <T> T convert(String s, Class<T> t)
	{
		if (s == null)
			return null;

		if (Boolean.class.equals(t))
			return (T) Boolean.valueOf(s);
		if (Byte.class.equals(t))
			return (T) Byte.valueOf(s);
		if (Short.class.equals(t))
			return (T) Short.valueOf(s);
		if (Integer.class.equals(t))
			return (T) Integer.valueOf(s);
		if (Long.class.equals(t))
			return (T) Long.valueOf(s);
		if (Character.class.equals(t))
			return (T) Character.valueOf(s.charAt(0));
		if (Float.class.equals(t))
			return (T) Float.valueOf(s);
		if (Double.class.equals(t))
			return (T) Double.valueOf(s);
		if (String.class.equals(t))
			return (T) s;

		return null;
	}
}
