package ca.mcgill.ecse321.project.dto;

public class RatingDTO extends ReviewDTO{

	private int rating;
	private int reviewId;
	private RoleDTO role;
	private CourseOfferingDTO co;
	
	//Data transfer object rating.
	public RatingDTO() {
	}

	public RatingDTO(int rating, int reviewId) {
		this.rating = rating; 
	}

	//get rating
	public int getRating() {
		return rating;
	}
	
	public void setRatingValue(int rating) {
		this.rating = rating;
	}

	public int getReviewId() {
		return reviewId;
	}
	
	public int setReviewId() {
		return reviewId;
	}
	
	public RoleDTO getRoleDTO() {
		return role;
	}
	
	public CourseOfferingDTO getCourseOfferingDTO() {
		return co;
	}

	public void setRoleDTO(RoleDTO role) {
		this.role = role;
	}
	
	public void setCourseOfferingDTO(CourseOfferingDTO co) {
		this.co = co;
	}
}
