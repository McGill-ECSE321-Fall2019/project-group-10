package ca.mcgill.ecse321.project.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class CourseOffering{
   private String term;

public void setTerm(String value) {
    this.term = value;
}
public String getTerm() {
    return this.term;
}
private int year;

public void setYear(int value) {
    this.year = value;
}
public int getYear() {
    return this.year;
}
private Course courses;

@ManyToOne(optional=false)
public Course getCourses() {
   return this.courses;
}

public void setCourses(Course courses) {
   this.courses = courses;
}

private Set<String/*No type specified*/> session;

@OneToMany
public Set<String/*No type specified*/> getSession() {
   return this.session;
}

public void setSession(Set<String/*No type specified*/> sessions) {
   this.session = sessions;
}

private Set<Review> review;

@OneToMany(mappedBy="courseOffering" )
public Set<Review> getReview() {
   return this.review;
}

public void setReview(Set<Review> reviews) {
   this.review = reviews;
}

private int courseOfferingID;

public void setCourseOfferingID(int value) {
    this.courseOfferingID = value;
}
public int getCourseOfferingID() {
    return this.courseOfferingID;
}
}
