package ca.mcgill.ecse321.project.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import java.util.List;
import java.util.Set;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;

@Entity
public class Tutor extends Role{

	@Enumerated(EnumType.STRING)
	private Education education;

	public Education getEducation() {
		return this.education;
	}

	public void setEducation(Education education) {

		this.education = education;

	}

	private Set<Session> session;

	@OneToMany(mappedBy="tutor" )
	public Set<Session> getSession() {
		return this.session;
	}

	public void setSession(Set<Session> session1s) {
		this.session = session1s;
	}

	private double hourlyRate;

	public void setHourlyRate(double value) {
		this.hourlyRate = value;
	}
	public double getHourlyRate() {
		return this.hourlyRate;
	}
	private int experience;

	public void setExperience(int value) {
		this.experience = value;
	}
	public int getExperience() {
		return this.experience;
	}

	private Set<Availability> availability;

	@OneToMany
	public Set<Availability> getAvailability() {
		return this.availability;
	}

	public void setAvailability(Set<Availability> availabilitys) {
		this.availability = availabilitys;
	}

	private List<CourseOffering> courseOfferings;

	@ManyToMany(mappedBy="tutors" )
	public List<CourseOffering> getCourseOfferings(){
		return this.courseOfferings;
	}

	public void setCourseOfferings(List<CourseOffering> courseOfferings) {
		this.courseOfferings = courseOfferings;
	}


}
