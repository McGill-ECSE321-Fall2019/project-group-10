package ca.mcgill.ecse321.project.dto;

import ca.mcgill.ecse321.project.model.CourseOffering;
import ca.mcgill.ecse321.project.model.Role;

public class ReviewDTO {
	private int reviewID;
	private CourseOffering courseOffering;
	private Role role;
	
	//Data transfer object rating.
	public ReviewDTO() {
	}

	public ReviewDTO(CourseOffering courseOffering, int reviewID, Role role) {
		this.courseOffering = courseOffering;
		this.reviewID = reviewID;
		this.role = role;
	}

	//get rating
	public CourseOffering getCourseOffering() {
		return courseOffering;
	}
	
	public int getReviewID() {
		return reviewID;
	}
	
	public Role getRole() {
		return role;
	}
	
	public void setCourseOffering(CourseOffering co) {
		this.courseOffering = co;
	}
	
	public void setReviewID(int id) {
		this.reviewID = id;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}
}
