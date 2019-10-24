package ca.mcgill.ecse321.project.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class CourseOffering{

	private Term term;

	public void setTerm(Term value) {
		this.term = value;
	}
	public Term getTerm() {

		return this.term;
	}
	private int year;

	public void setYear(int value) {
		this.year = value;
	}
	public int getYear() {
		return this.year;
	}
	private Course course;

	@ManyToOne(optional=false)
	public Course getCourse() {
		return this.course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	private List<Session> session;

	@OneToMany(mappedBy="courseOffering" )
	public List<Session> getSession() {
		return this.session;
	}

	public void setSession(List<Session> sessions) {
		this.session = sessions;
	}

	private Set<Review> review;

	@OneToMany(mappedBy="courseOffering" )
	public Set<Review> getReview() {
		return this.review;
	}

	public void setReview(Set<Review> reviews) {
		this.review = reviews;
	}

	private int courseOfferingID;

	public void setCourseOfferingID(int value) {
		this.courseOfferingID = value;
	}

	@Id
	@GeneratedValue
	public int getCourseOfferingID() {
		return this.courseOfferingID;
	}

	private List<Tutor> tutors;

	@ManyToMany
	public List<Tutor> getTutors(){
		return this.tutors;
	}
	
	public void addTutor(Tutor t) {
		if(tutors == null) {
			tutors = new ArrayList<>();
		}
		this.tutors.add(t);
	}

	public void setTutors(List<Tutor> tutor) {
		this.tutors = tutor;
	}

}
