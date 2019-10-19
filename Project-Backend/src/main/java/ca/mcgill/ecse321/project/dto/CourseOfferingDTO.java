package ca.mcgill.ecse321.project.dto;

import ca.mcgill.ecse321.project.model.*;

public class CourseOfferingDTO {

	private Term term;
	private int year;	
	
	//Data transfer object session.
	public CourseOfferingDTO() {
	}

	public CourseOfferingDTO(Term term, int year) {
		this.term = term;
		this.year = year;
	}

	//get rating
	public Term getIsAllowed() {
		return term;
	}
	
	public int getDescription() {
		return year;
	}


}
