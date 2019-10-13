package ca.mcgill.ecse321.project.model;
import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Availability{
   private Date date;

public void setDate(Date value) {
    this.date = value;
}
public Date getDate() {
    return this.date;
}
private int time;

public void setTime(int value) {
    this.time = value;
}
public int getTime() {
    return this.time;
}
private Tutor tutor;

@ManyToOne(optional=false)
public Tutor getTutor() {
   return this.tutor;
}

public void setTutor(Tutor tutor) {
   this.tutor = tutor;
}

private int availabilityID;

public void setAvailabilityID(int value) {
    this.availabilityID = value;
}
public int getAvailabilityID() {
    return this.availabilityID;
}
}
