package com.app55.domain;

import org.codehaus.jackson.annotate.JsonProperty;

public final class User
{
	private Long	id;
	private String	email;
	private String	password;
	private String	confirmPassword;
	private String	phone;
	private Boolean active;

	public User()
	{
	}

	public User(long id)
	{
		this.id = id;
	}

	public User(String email)
	{
		this.email = email;
	}

	public User(String email, String password)
	{
		this.email = email;
		this.password = password;
	}

	public User(String email, String phone, String password, String confirmPassword)
	{
		this.email = email;
		this.phone = phone;
		this.password = password;
		this.confirmPassword = confirmPassword;
	}

	public User(String email, String password, String confirmPassword)
	{
		this.email = email;
		this.password = password;
		this.confirmPassword = confirmPassword;
	}

	public User(long id, String email)
	{
		this.id = id;
		this.email = email;
	}

	public User(long id, String password, String confirmPassword)
	{
		this.id = id;
		this.password = password;
		this.confirmPassword = confirmPassword;
	}

	public User(long id, String email, String password, String confirmPassword)
	{
		this.id = id;
		this.email = email;
		this.password = password;
		this.confirmPassword = confirmPassword;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long value)
	{
		if (value != null && value < 1L)
			throw new IllegalArgumentException();

		this.id = value;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	@JsonProperty(value = "password_confirm")
	public String getConfirmPassword()
	{
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword)
	{
		this.confirmPassword = confirmPassword;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this.id != null && obj instanceof User)
			return id.equals(((User) obj).getId());

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