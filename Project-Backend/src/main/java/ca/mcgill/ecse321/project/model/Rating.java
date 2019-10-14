package ca.mcgill.ecse321.project.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class Rating extends Review{
	private int ratingValue;
	
	public int getRatingValue() {
		
		return ratingValue;
		
	}
	
	public void setRatingValue(int ratingValue) {
		
		if(ratingValue > 5 || ratingValue < 1) {
			
			throw new RuntimeException();
			
		} else {
		
			this.ratingValue = ratingValue;
			
		}
	}
}
