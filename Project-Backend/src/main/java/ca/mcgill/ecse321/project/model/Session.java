package ca.mcgill.ecse321.project.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Session{
   private int date;

public void setDate(int value) {
    this.date = value;
}
public int getDate() {
    return this.date;
}
private int amountPaid;

public void setAmountPaid(int value) {
    this.amountPaid = value;
}
public int getAmountPaid() {
    return this.amountPaid;
}
private int sessionID;

public void setSessionID(int value) {
    this.sessionID = value;
}
public int getSessionID() {
    return this.sessionID;
}
   private Set<Student> student;
   
   @ManyToMany
   public Set<Student> getStudent() {
      return this.student;
   }
   
   public void setStudent(Set<Student> students) {
      this.student = students;
   }
   
   private Tutor tutor;
   
   @ManyToOne(optional=false)
   public Tutor getTutor() {
      return this.tutor;
   }
   
   public void setTutor(Tutor tutor) {
      this.tutor = tutor;
   }
   
   private Room room;
   
   @ManyToOne
   public Room getRoom() {
      return this.room;
   }
   
   public void setRoom(Room room) {
      this.room = room;
   }
   
   }
