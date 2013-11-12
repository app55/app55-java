package com.app55.message;

import com.app55.domain.Schedule;

import java.util.List;

public class ScheduleListResponse extends Response
{
	private List<Schedule>	schedules;

	public List<Schedule> getSchedules()
	{
		return schedules;
	}

	public void setSchedules(List<Schedule> schedules)
	{
		this.schedules = schedules;
	}
}