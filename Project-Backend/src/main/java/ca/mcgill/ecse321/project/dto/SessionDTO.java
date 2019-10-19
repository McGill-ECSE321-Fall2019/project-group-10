package ca.mcgill.ecse321.project.dto;

import java.sql.Date;
import java.sql.Time;

public class SessionDTO {

	private Date date;
	private Time time;
	private Double amountPaid;
	
	
	//Data transfer object session.
	public SessionDTO() {
	}

	public SessionDTO(Time time, double amountPaid, Date date) {
		this.date = date;
		this.time = time;
		this.amountPaid = amountPaid;
	}

	//get rating
	public Date getDate() {
		return date;
	}
	
	public Time getTime() {
		return time;
	}
	
	public double getAmountPaid() {
		return amountPaid;
	}

}
