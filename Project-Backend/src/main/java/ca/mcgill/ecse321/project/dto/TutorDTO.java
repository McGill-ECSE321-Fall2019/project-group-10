package ca.mcgill.ecse321.project.dto;
import java.util.List;

import ca.mcgill.ecse321.project.model.*;


public class TutorDTO {

	private double hourlyRate;
	private int experience;
	private Education education;
	private List<RatingDTO> ratings;
	private List<TextDTO> texts;
	private List<AvailabilityDTO> avails;
	private UserDTO user;
	private String username;
	
	
	//Data transfer object session.
	public TutorDTO() {
	}

	public TutorDTO(String username, Education education, double hourlyRate, int experience) {
		this.username = username;
		this.experience = experience;
		this.hourlyRate = hourlyRate;
		this.education = education;
	}

	//get rating
	public String getUsername() {
		return this.username;
	}
	
	public double getHourlyRate() {
		return hourlyRate;
	}
	
	public Education getEducation() {
		return education;
	}
	
	public int getExperience() {
		return experience;
	}
	
	public List<RatingDTO> getRatings() {
		return this.ratings;
	}
	
	public List<TextDTO> getTexts() {
		return this.texts;
	}
	
	public List<AvailabilityDTO> getAvails() {
		return this.avails;
	}
	
	public void setRatings(List<RatingDTO> ratings) {
		this.ratings = ratings;
	}
	
	public void setTexts(List<TextDTO> texts) {
		this.texts = texts;
	}
	
	public void setAvails(List<AvailabilityDTO> avails) {
		this.avails = avails;
	}
	
	public UserDTO getUser() {
		return this.user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

}
