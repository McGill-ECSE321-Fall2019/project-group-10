package ca.mcgill.ecse321.project.dto;

public class RatingDTO {

	private int rating;

	
	//Data transfer object rating.
	public RatingDTO() {
	}

	public RatingDTO(int rating) {
		this.rating = rating; 
	}

	//get rating
	public int getRating() {
		return rating;
	}


}
