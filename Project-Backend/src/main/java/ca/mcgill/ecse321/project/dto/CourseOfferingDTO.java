package ca.mcgill.ecse321.project.dto;

import ca.mcgill.ecse321.project.model.*;

public class CourseOfferingDTO {

	private Term term;
	private int year;
	private String cname;
	private String uniName;
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
	public Term getIsAllowed() {
		return term;
	}
	
	public int getDescription() {
		return year;
	}
	
	public int getID() {
		return id;
	}


}
