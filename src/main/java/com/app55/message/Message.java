package com.app55.message;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import com.app55.Gateway;
import com.app55.converter.Converter;
import com.app55.util.EncodeUtil;
import com.app55.util.ReflectionUtil;

public abstract class Message
{
	protected Map<String, String>	additionalFields	= new HashMap<String, String>();
	private Gateway					gateway;

	@JsonIgnore
	public Gateway getGateway()
	{
		return gateway;
	}

	public void setGateway(Gateway gateway)
	{
		this.gateway = gateway;
	}

	@JsonProperty(value = "sig")
	public abstract String getSignature();

	@JsonProperty(value = "ts")
	public abstract String getTimestamp();

	@JsonIgnore
	public String getFormData()
	{
		return toFormData(true, null);
	}

	protected String toSignature()
	{
		return toSignature(false);
	}

	protected String toSignature(boolean includeApiKey)
	{
		Map<String, Boolean> exclude = new HashMap<String, Boolean>();
		exclude.put("sig", true);
		String formData = toFormData(includeApiKey, exclude);
		byte[] digest = EncodeUtil.sha1(gateway.getApiSecret() + formData);
		return EncodeUtil.base64(digest);
	}

	protected String toFormData(boolean includeApiKey, Map<String, Boolean> exclude)
	{
		try
		{
			String formData = "";
			Map<String, String> description = describe(this, null, exclude);
			if (includeApiKey)
			{
				description.put("api_key", gateway.getApiKey());
				if (exclude == null || !exclude.containsKey("sig"))
				{
					description.remove("sig");
					description.put("sig", toSignature(true));
				}
			}

			for (Map.Entry<String, String> entry : additionalFields.entrySet())
				description.put(entry.getKey(), entry.getValue());

			for (Map.Entry<String, String> entry : description.entrySet())
				formData += "&" + entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), "UTF-8");

			if (formData.length() > 0)
				formData = formData.substring(1);

			formData = formData.replace("+", "%20");
			formData = formData.replace("+", "%20");

			return formData;
		}
		catch (Exception e)
		{
			return null;
		}
	}

	private Map<String, String> describe(Object o)
	{
		return describe(o, null, null);
	}

	private Map<String, String> describe(Object o, String prefix)
	{
		return describe(o, prefix, null);
	}

	private Map<String, String> describe(Object o, String prefix, Map<String, Boolean> exclude)
	{
		if (o == null)
			return new HashMap<String, String>();

		prefix = prefix == null ? "" : prefix + ".";

		Map<String, String> description = new TreeMap<String, String>();
		Map<String, PropertyDescriptor> properties = ReflectionUtil.getAllProperties(o);

		for (Entry<String, PropertyDescriptor> property : properties.entrySet())
		{
			Method m = property.getValue().getReadMethod();

			if (exclude != null && exclude.containsKey(property.getKey()))
				continue;

			Object value = null;
			try
			{
				value = m.invoke(o);
			}
			catch (Exception e)
			{
			}

			if (value == null)
				continue;

			if (Converter.getInstance().canConvert(value))
			{
				description.put(prefix + property.getKey(), Converter.getInstance().convert(value));
			}
			else if (value instanceof Collection)
			{
				int i = 0;
				for (Object obj : (Collection<?>) value)
				{
					for (Entry<String, String> entry : describe(obj, prefix + property.getKey() + '.' + i).entrySet())
						description.put(entry.getKey(), entry.getValue());
					i++;
				}
			}
			else
			{
				for (Entry<String, String> entry : describe(value, prefix + property.getKey()).entrySet())
					description.put(entry.getKey(), entry.getValue());
			}
		}

		return description;
	}
}