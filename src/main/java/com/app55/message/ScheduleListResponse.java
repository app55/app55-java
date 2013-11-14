package com.app55.message;

import java.util.List;

import com.app55.domain.Schedule;

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