package ca.mcgill.ecse321.project.dto;

import ca.mcgill.ecse321.project.model.CourseOffering;
import ca.mcgill.ecse321.project.model.Role;

public class TextDTO extends ReviewDTO{

	private boolean isAllowed;
	private String description;	
	private int reviewId;
	private Role role;
	private CourseOffering co;
	
	//Data transfer object session.
	public TextDTO() {
	}

	public TextDTO(int reviewId) {
		this.reviewId = reviewId;
	}

	public int getReviewId() {
		return reviewId;
	}
	//get rating
	public boolean getIsAllowed() {
		return isAllowed;
	}
	
	public String getDescription() {
		return description;
	}
	
	public Role getRole() {
		return role;
	}
	
	public CourseOffering getCourseOffering() {
		return co;
	}
	
	public void setIsAllowed(boolean isAllowed) {
		this.isAllowed = isAllowed;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}
	
	public void setCourseOffering(CourseOffering co) {
		this.co = co;
	}

}
