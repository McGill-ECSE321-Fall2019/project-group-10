package ca.mcgill.ecse321.project.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public abstract class Review{
   private int reviewID;
   
   public void setReviewID(int value) {
      this.reviewID = value;
   }
   
   public int getReviewID() {
      return this.reviewID;
   }
   
   private CourseOffering courseOffering;
   
   @ManyToOne(optional=false)
   public CourseOffering getCourseOffering() {
      return this.courseOffering;
   }
   
   public void setCourseOffering(CourseOffering courseOffering) {
      this.courseOffering = courseOffering;
   }
   
   private Role writtenAbout;
   
   @ManyToOne(optional=false)
   public Role getWrittenAbout() {
      return this.writtenAbout;
   }
   
   public void setWrittenAbout(Role writtenAbout) {
      this.writtenAbout = writtenAbout;
   }
   
   }
