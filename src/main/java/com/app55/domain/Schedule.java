package com.app55.domain;

import org.codehaus.jackson.annotate.JsonProperty;

public class Schedule
{
	public static final String	TIMEUNIT_MONTHLY	= "monthly";
	public static final String	TIMEUNIT_WEEKLY		= "weekly";
	public static final String	TIMEUNIT_DAILY		= "daily";
	public static final String	TIMEUNIT_ONCE		= "once";

	private String				id;
	private String				timeUnit;
	private Integer				units;
	private Integer				day;
	private String				start;
	private String				end;
	private String				next;

	public Schedule()
	{
	}

	public Schedule(String id)
	{
		this.id = id;
	}

	public Schedule(String id, String start, String end)
	{
		this.id = id;
		this.start = start;
		this.end = end;
	}

	public Schedule(String timeUnit, String start)
	{
		this.timeUnit = timeUnit;
		this.start = start;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	@JsonProperty(value = "time_unit")
	public String getTimeUnit()
	{
		return timeUnit;
	}

	public void setTimeUnit(String timeUnit)
	{
		this.timeUnit = timeUnit;
	}

	public Integer getUnits()
	{
		return units;
	}

	public void setUnits(Integer units)
	{
		this.units = units;
	}

	public Integer getDay()
	{
		return day;
	}

	public void setDay(Integer day)
	{
		this.day = day;
	}

	public String getStart()
	{
		return start;
	}

	public void setStart(String start)
	{
		this.start = start;
	}

	public String getEnd()
	{
		return end;
	}

	public void setEnd(String end)
	{
		this.end = end;
	}

	public String getNext()
	{
		return next;
	}

	public void setNext(String next)
	{
		this.next = next;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this.id != null && obj != null && obj instanceof Schedule)
			return id.equals(((Schedule) obj).getId());

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