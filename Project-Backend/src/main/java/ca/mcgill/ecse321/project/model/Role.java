package ca.mcgill.ecse321.project.model;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import ca.mcgill.ecse321.project.model.TSUser;
import javax.persistence.ManyToOne;

import java.util.Set;
import javax.persistence.OneToMany;

@Entity
@Inheritance(strategy = InheritanceType.JOINED )
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
   
   public void setUsername(String value) {
      this.username = value;
   }

   public String getUsername() {
      return this.username;
   }
   
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
   
   }
