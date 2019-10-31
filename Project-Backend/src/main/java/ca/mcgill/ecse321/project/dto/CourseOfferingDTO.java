package ca.mcgill.ecse321.project.dto;

import ca.mcgill.ecse321.project.model.*;

public class CourseOfferingDTO {

	private Term term;
	private int year;
	private int id;
	
	//Data transfer object session.
	public CourseOfferingDTO() {
	}

	public CourseOfferingDTO(Term term, int year, int id) {
		this.term = term;
		this.year = year;
		this.id = id;
	}

	//get attributes
	public Term getTerm() {
		return term;
	}
	
	public int getYear() {
		return year;
	}
	
	public int getID() {
		return id;
	}
	public void setYear(int year) {
		this.year = year;
	}
}
