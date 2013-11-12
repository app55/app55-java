package com.app55.domain;

import org.codehaus.jackson.annotate.JsonProperty;

public class Card
{
	private String	token;
	private String	holderName;
	private String	number;
	private String	expiry;
	private String	expiryMonth;
	private String	expiryYear;
	private String	securityCode;
	private Address	address;
	private String	type;
	private String	description;
	private String	issue;

	public Card()
	{
	}

	public Card(String token)
	{
		this.token = token;
	}

	public Card(String holderName, String number, String expiry, String securityCode, Address address)
	{
		this.holderName = holderName;
		this.number = number;
		this.expiry = expiry;
		this.securityCode = securityCode;
		this.address = address;
	}

	public Card(String holderName, String number, String expiry, String securityCode, String issue, Address address)
	{
		this(holderName, number, expiry, securityCode, address);
		this.issue = issue;
	}

	public Card(String holderName, String number, String expiry, String securityCode, String issue, Address address, String description)
	{
		this(holderName, number, expiry, securityCode, issue, address);
		this.description = description;
	}

	public Card(String holderName, String number, String expiryMonth, String expiryYear, String securityCode, String issue, Address address)
	{
		this.holderName = holderName;
		this.number = number;
		this.expiryMonth = expiryMonth;
		this.expiryYear = expiryYear;
		this.securityCode = securityCode;
		this.address = address;
		this.issue = issue;
	}

	public Card(String holderName, String number, String expiryMonth, String expiryYear, String securityCode, String issue, Address address, String description)
	{
		this(holderName, number, expiryMonth, expiryYear, securityCode, issue, address);
		this.description = description;
	}

	public String getToken()
	{
		return token;
	}

	public void setToken(String token)
	{
		this.token = token;
	}

	@JsonProperty(value = "holder_name")
	public String getHolderName()
	{
		return holderName;
	}

	public void setHolderName(String holderName)
	{
		this.holderName = holderName;
	}

	public String getNumber()
	{
		return number;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}

	public String getExpiry()
	{
		return expiry;
	}

	public void setExpiry(String expiry)
	{
		this.expiry = expiry;
	}

	@JsonProperty(value = "expiry_month")
	public String getExpiryMonth()
	{
		return expiryMonth;
	}

	public void setExpiryMonth(String expiryMonth)
	{
		this.expiryMonth = expiryMonth;
	}

	@JsonProperty(value = "expiry_year")
	public String getExpiryYear()
	{
		return expiryYear;
	}

	public void setExpiryYear(String expiryYear)
	{
		this.expiryYear = expiryYear;
	}

	@JsonProperty(value = "security_code")
	public String getSecurityCode()
	{
		return securityCode;
	}

	public void setSecurityCode(String securityCode)
	{
		this.securityCode = securityCode;
	}

	public Address getAddress()
	{
		return address;
	}

	public void setAddress(Address address)
	{
		this.address = address;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getIssue()
	{
		return issue;
	}

	public void setIssue(String issue)
	{
		this.issue = issue;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this.token != null && obj != null && obj instanceof Card)
			return token.equals(((Card) obj).getToken());

		return super.equals(obj);
	}

	@Override
	public int hashCode()
	{
		if (this.token != null)
			return token.hashCode();

		return super.hashCode();
	}
}