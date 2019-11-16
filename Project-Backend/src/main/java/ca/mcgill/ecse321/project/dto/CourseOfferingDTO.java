package ca.mcgill.ecse321.project.dto;
import ca.mcgill.ecse321.project.model.*;

//Course Offering
public class CourseOfferingDTO {
	private Term term;
	private int year;
	private int id;
	private String courseName;

	//Data transfer object session.
	public CourseOfferingDTO() {
	}
	public CourseOfferingDTO(Term term, int year, int id) {
		this.term = term;
		this.year = year;
		this.id = id;
	}

	public CourseOfferingDTO(Term term, int year, int id, String courseName) {
		this.term = term;
		this.year = year;
		this.id = id;
		this.courseName = courseName;
	}

	//get attributes
	public Term getTerm() {
		return term;
	}
	
	public int getYear() {
		return year;
	}

	public String getCourseName() {
		return this.courseName;
	}

	public int getID() {
		return id;
	}
	public void setYear(int year) {
		this.year = year;
	}

	public void setCourseName(String courseName2) {
		// TODO Auto-generated method stub
		this.courseName = courseName2;

	}
}