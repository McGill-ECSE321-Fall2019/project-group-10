package ca.mcgill.ecse321.project.dto;

public class CourseDto {

	private String courseName;
	private String description;	
	private String uniName;
	
	//Data transfer object session.
	public CourseDto() {
	}

	public CourseDto(String courseName, String description, String uniName) {
		this.courseName = courseName;
		this.description = description;
		this.uniName = uniName;
	}

	//get attributes
	public String getCourseName() {
		return courseName;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getUniName() {
		return uniName;
	}
}
