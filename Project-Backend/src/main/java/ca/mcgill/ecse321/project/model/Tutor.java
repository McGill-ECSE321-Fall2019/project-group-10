package ca.mcgill.ecse321.project.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Tutor extends Role{

	private Education education;

	@OneToOne(mappedBy="tutor")
	public Education getEducation() {
		return this.education;
	}
	
	public void setEducation(Education education) {
		
		this.education = education;
		
	}

	private Set<Session> session1;

   
   @OneToMany(mappedBy="tutor" )
   public Set<Session> getSession1() {
      return this.session1;
   }
   
   public void setSession1(Set<Session> session1s) {
      this.session1 = session1s;
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
   
   @OneToMany(mappedBy="tutor" )
   public Set<Availability> getAvailability() {
      return this.availability;
   }
   
   public void setAvailability(Set<Availability> availabilitys) {
      this.availability = availabilitys;
   }
   
   private Set<String/*No type specified*/> session;
   
   @OneToMany
   public Set<String/*No type specified*/> getSession() {
      return this.session;
   }
   
   public void setSession(Set<String/*No type specified*/> sessions) {
      this.session = sessions;
   }
   
   }
