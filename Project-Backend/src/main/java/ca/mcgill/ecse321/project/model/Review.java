package ca.mcgill.ecse321.project.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public abstract class Review{
	private int reviewID;

	public void setReviewID(int value) {
		this.reviewID = value;
	}
	@Id
	@GeneratedValue
	public int getReviewID() {
		return this.reviewID;
	}

	private CourseOffering courseOffering;

	@ManyToOne(optional=true)
	public CourseOffering getCourseOffering() {
		return this.courseOffering;
	}

	public void setCourseOffering(CourseOffering courseOffering) {
		this.courseOffering = courseOffering;
	}

	private Role writtenAbout;

	@ManyToOne(optional=true, cascade=CascadeType.ALL)
	public Role getWrittenAbout() {
		return this.writtenAbout;
	}

	public void setWrittenAbout(Role writtenAbout) {
		this.writtenAbout = writtenAbout;
//		Set<Review> reviews = writtenAbout.getReview();
//		if(reviews == null) {
//			Set<Review> review = new HashSet<>();
//			review.add(this);
//			writtenAbout.setReview(review);
//		}
//		else {
//			reviews.add(this);
//			writtenAbout.setReview(reviews);
//		}
	}

}
