package ca.mcgill.ecse321.project.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Course{
   private String description;

public void setDescription(String value) {
    this.description = value;
}
public String getDescription() {
    return this.description;
}
private String courseName;

public void setCourseName(String value) {
    this.courseName = value;
}
public String getCourseName() {
    return this.courseName;
}
private int courseID;

public void setCourseID(int value) {
    this.courseID = value;
}
public int getCourseID() {
    return this.courseID;
}
   private University university;
   
   @ManyToOne(optional=false)
   public University getUniversity() {
      return this.university;
   }
   
   public void setUniversity(University university) {
      this.university = university;
   }
   
   }
