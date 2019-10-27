package ca.mcgill.ecse321.project.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import ca.mcgill.ecse321.project.model.TSUser;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.ManyToOne;

import java.util.Set;
import javax.persistence.OneToMany;

@Inheritance(strategy = InheritanceType.JOINED)
@Entity
public abstract class Role{
	private int id;
	public void setId(int id) {
		this.id = id;
	}

	@Id
	@GeneratedValue
	public int getId() {
		return this.id;
	}

	private String username;

	@UniqueElements
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String value) {
		this.username = value;
	}

	public boolean isPassword(String password) { return password.equals(getPassword()); }

	private String password;

	public void setPassword(String value) {
		this.password = value;
	}

	public String getPassword() {
		return this.password;
	}

	private TSUser user;

	@ManyToOne(optional=false)
	public TSUser getUser() {
		return this.user;
	}

	public void setUser(TSUser user) {
		this.user = user;
	}

	private Set<Review> review;

	@OneToMany(mappedBy="writtenAbout" )
	public Set<Review> getReview() {
		return this.review;
	}

	public void setReview(Set<Review> reviews) {
		this.review = reviews;
	}

	private Set<Review> authoredReview;

	@OneToMany
	public Set<Review> getAuthoredReview() {
		return this.authoredReview;
	}

	public void setAuthoredReview(Set<Review> authoredReviews) {
		this.authoredReview = authoredReviews;
	}

	private boolean loggedIn;

	public boolean isLoggedIn() { return loggedIn;}
	
	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public void logIn() { loggedIn = true; }

	public void logOut() { loggedIn = false; }

}
