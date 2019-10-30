package ca.mcgill.ecse321.project.dto;

public class TextDTO extends ReviewDTO{

	private boolean isAllowed;
	private String description;	
	private int reviewId;
	private RoleDTO role;
	private CourseOfferingDTO co;
	
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
	
	public RoleDTO getRoleDTO() {
		return role;
	}
	
	public CourseOfferingDTO getCourseOfferingDTO() {
		return co;
	}
	
	public void setIsAllowed(boolean isAllowed) {
		this.isAllowed = isAllowed;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setRole(RoleDTO role) {
		this.role = role;
	}
	
	public void setCourseOffering(CourseOfferingDTO co) {
		this.co = co;
	}

}
