package ca.mcgill.ecse321.project.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

@Entity
public class University{
   private String name;

public void setName(String value) {
    this.name = value;
}
public String getName() {
    return this.name;
}
private String address;

public void setAddress(String value) {
    this.address = value;
}
public String getAddress() {
    return this.address;
}
private int universityID;

public void setUniversityID(int value) {
    this.universityID = value;
}
public int getUniversityID() {
    return this.universityID;
}
   private Set<Course> courses;
   
   @OneToMany(mappedBy="university" , cascade={CascadeType.ALL})
   public Set<Course> getCourses() {
      return this.courses;
   }
   
   public void setCourses(Set<Course> coursess) {
      this.courses = coursess;
   }
   
   }
