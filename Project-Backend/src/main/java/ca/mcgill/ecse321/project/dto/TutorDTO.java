package ca.mcgill.ecse321.project.dto;
import ca.mcgill.ecse321.project.model.*;


public class TutorDTO {

	private double hourlyRate;
	private int experience;
	private Education education;
	
	
	//Data transfer object session.
	public TutorDTO() {
	}

	public TutorDTO(Education education, double hourlyRate, int experience) {
		this.experience = experience;
		this.hourlyRate = hourlyRate;
		this.education = education;
	}

	//get rating
	public double getHourlyRate() {
		return hourlyRate;
	}
	
	public Education getEducation() {
		return education;
	}
	
	public int getExperience() {
		return experience;
	}

}
