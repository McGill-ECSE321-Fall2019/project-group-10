package ca.mcgill.ecse321.project.model;

import javax.persistence.*;

import ca.mcgill.ecse321.project.model.TSUser;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.Set;

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


	@Column(unique = true)
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

	@Transient
	public boolean isLoggedIn() { return loggedIn;}

	public void logIn() { loggedIn = true; }

	public void logOut() { loggedIn = false; }

}
