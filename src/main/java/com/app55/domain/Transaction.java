package com.app55.domain;

import org.codehaus.jackson.annotate.JsonProperty;

public class Transaction
{
	private String	id;
	private String	amount;
	private String	currency;
	private String	description;
	private String	code;
	private String	authCode;
	private Boolean	commit;
	private String	type;

	public Transaction()
	{
	}

	public Transaction(String amount, String currency)
	{
		this.amount = amount;
		this.currency = currency;
	}

	public Transaction(String amount, String currency, String description)
	{
		this(amount, currency);
		this.description = description;
	}

	public Transaction(String id, String amount, String currency, String description)
	{
		this(amount, currency, description);
		this.id = id;
	}

	public Transaction(String id)
	{
		this.id = id;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getAmount()
	{
		return amount;
	}

	public void setAmount(String amount)
	{
		this.amount = amount;
	}

	public String getCurrency()
	{
		return currency;
	}

	public void setCurrency(String currency)
	{
		this.currency = currency;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	@JsonProperty(value = "auth_code")
	public String getAuthCode()
	{
		return authCode;
	}

	public void setAuthCode(String authCode)
	{
		this.authCode = authCode;
	}

	public Boolean getCommit()
	{
		return commit;
	}

	public void setCommit(Boolean commit)
	{
		this.commit = commit;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this.id != null && obj != null && obj instanceof Transaction)
			return id.equals(((Transaction) obj).getId());

		return super.equals(obj);
	}

	@Override
	public int hashCode()
	{
		if (this.id != null)
			return id.hashCode();

		return super.hashCode();
	}
}