package com.app55.domain;

import org.codehaus.jackson.annotate.JsonProperty;

public class Address
{
	private String	street;
	private String	street2;
	private String	city;
	private String	postalCode;
	private String	country;

	public Address()
	{
	}

	public Address(String street, String city, String postalCode, String country)
	{
		this.street = street;
		this.city = city;
		this.postalCode = postalCode;
		this.country = country;
	}

	public Address(String street, String street2, String city, String postalCode, String country)
	{
		this(street, city, postalCode, country);
		this.street2 = street2;
	}

	public String getStreet()
	{
		return street;
	}

	public void setStreet(String street)
	{
		this.street = street;
	}

	public String getStreet2()
	{
		return street2;
	}

	public void setStreet2(String street2)
	{
		this.street2 = street2;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	@JsonProperty(value = "postal_code")
	public String getPostalCode()
	{
		return postalCode;
	}

	public void setPostalCode(String postalCode)
	{
		this.postalCode = postalCode;
	}

	public String getCountry()
	{
		return country;
	}

	public void setCountry(String country)
	{
		this.country = country;
	}
}