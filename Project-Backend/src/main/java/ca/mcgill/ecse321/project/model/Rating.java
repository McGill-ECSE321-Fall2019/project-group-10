package ca.mcgill.ecse321.project.model;

import javax.persistence.Entity;

@Entity
public class Rating extends Review{
	private int ratingValue;

	public int getRatingValue() {

		return ratingValue;

	}

	public void setRatingValue(int ratingValue) {

		this.ratingValue = ratingValue;
	}
}
