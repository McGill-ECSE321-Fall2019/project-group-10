package ca.mcgill.ecse321.project.dto;

import ca.mcgill.ecse321.project.model.*;

public class CourseOfferingDTO {

	private Term term;
	private int year;
	private String cname;
	private String uniName;
	
	//Data transfer object session.
	public CourseOfferingDTO() {
	}

	public CourseOfferingDTO(Term term, int year, String cname, String uniName) {
		this.term = term;
		this.year = year;
		this.cname = cname;
		this.uniName = uniName;
	}

	//get attributes
	public Term getIsAllowed() {
		return term;
	}
	
	public int getDescription() {
		return year;
	}
	
	public String getCourse() {
		return cname;
	}
	
	public String getUniversity() {
		return uniName;
	}


}
