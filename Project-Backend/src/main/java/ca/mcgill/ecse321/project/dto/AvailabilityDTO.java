package ca.mcgill.ecse321.project.dto;

import java.sql.Date;
import java.sql.Time;


public class AvailabilityDTO {

	private Date date;
	private Time time;
	
	//Data transfer object session.
	public AvailabilityDTO() {
	}

	public AvailabilityDTO(Date date, Time time) {
		this.date = date;
		this.time = time;
	}

	//get rating
	public Date getDate() {
		return date;
	}
	
	public Time getTime() {
		return time;
	}


}
