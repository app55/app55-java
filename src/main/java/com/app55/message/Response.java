package com.app55.message;

import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import com.app55.Gateway;
import com.app55.util.ReflectionUtil;

public class Response extends Message
{
	private String	signature;
	private String	timestamp;

	protected Response()
	{
	}

	protected Response(Gateway gateway, Map<String, Object> ht)
	{
		setGateway(gateway);
		this.populate(ht);
	}

	@Override
	@JsonProperty(value = "sig")
	public String getSignature()
	{
		return signature;
	}

	public void setSignature(String signature)
	{
		this.signature = signature;
	}

	@Override
	@JsonProperty(value = "ts")
	public String getTimestamp()
	{
		return timestamp;
	}

	public void setTimestamp(String timestamp)
	{
		this.timestamp = timestamp;
	}

	@JsonIgnore
	public boolean isValidSignature()
	{
		String expectedSig = getExpectedSignature();
		return expectedSig.equals(signature);
	}

	@JsonIgnore
	public String getExpectedSignature()
	{
		return toSignature();
	}

	@SuppressWarnings("unchecked")
	private void populateAdditional(Map<String, Object> hashtable, String prefix)
	{
		SortedMap<String, Object> ht = new TreeMap<String, Object>(hashtable);
		for (Entry<String, Object> entry : ht.entrySet())
		{
			String key = entry.getKey();
			Object value = entry.getValue();

			if (value != null)
			{
				if (value instanceof Map)
				{
					populateAdditional((Map<String, Object>) value, prefix + key + ".");
				}
				else if (value instanceof List)
				{
					int i = 0;
					for (Object arrayItem : ((List<Object>) value))
					{
						if (arrayItem instanceof Map)
						{
							populateAdditional((Map<String, Object>) arrayItem, prefix + key + "." + i++ + ".");
						}
						else
						{
							additionalFields.put(prefix + key + "." + i++, arrayItem.toString());
						}
					}
				}
				else
					additionalFields.put(prefix + key, value.toString());
			}
		}
	}

	void populate(Map<String, Object> hashtable)
	{
		populate(hashtable, null);
	}

	private void populate(Map<String, Object> hashtable, Object o)
	{
		populate(hashtable, o, "");
	}

	@SuppressWarnings("unchecked")
	private void populate(Map<String, Object> hashtable, Object o, String prefix)
	{
		if (o == null)
			o = this;

		Map<String, PropertyDescriptor> properties = ReflectionUtil.getAllProperties(o);
		for (Entry<String, Object> entry : hashtable.entrySet())
		{
			try
			{
				String key = entry.getKey();
				Object value = entry.getValue();

				if (value == null || !properties.containsKey(key))
				{
					if (value != null)
					{
						if (value instanceof Map)
						{
							populateAdditional((Map<String, Object>) value, prefix + key + ".");
						}
						else if (value instanceof List)
						{
							int i = 0;
							for (Object arrayItem : ((List<Object>) value))
							{
								if (arrayItem instanceof Map)
								{
									populateAdditional((Map<String, Object>) arrayItem, prefix + key + "." + i++ + ".");
								}
								else
								{
									additionalFields.put(prefix + key + "." + i++, arrayItem.toString());
								}
							}
						}
						else
							additionalFields.put(prefix + key, value.toString());
					}
					continue;
				}
			}
			catch (Exception e)
			{
			}
		}
	}
}