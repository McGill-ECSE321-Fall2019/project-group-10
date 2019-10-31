package ca.mcgill.ecse321.project.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.OneToMany;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.ManyToMany;

@SuppressWarnings("unused")
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

	@OneToMany(mappedBy="tutor", cascade = CascadeType.ALL)
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
	
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	public Set<Availability> getAvailability() {
		return this.availability;
	}

	public void setAvailability(Set<Availability> availabilitys) {
		this.availability = availabilitys;
	}

	private List<CourseOffering> courseOfferings;

	@ManyToMany(mappedBy="tutors", cascade = CascadeType.ALL)
	public List<CourseOffering> getCourseOfferings(){
		return this.courseOfferings;
	}

	public void setCourseOfferings(List<CourseOffering> courseOfferings) {
		this.courseOfferings = courseOfferings;
	}

	public void addAvailability(Availability a) {
		// TODO Auto-generated method stub
		if(availability == null) {
			availability = new HashSet<>();
		}
		this.availability.add(a);
	}


}
