package ca.mcgill.ecse321.project.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.sql.Date;
import java.sql.Time;

import java.util.List;
import java.util.Set;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Session{
	private Date date;
	private Time time;

	public void setDate(Date value) {
		this.date = value;
	}
	public Date getDate() {
		return this.date;
	}
	public void setTime(Time value) {
		this.time = value;
	}
	public Time getTime() {
		return this.time;
	}
	private double amountPaid;

	public void setAmountPaid(Double value) {
		this.amountPaid = value;
	}
	public double getAmountPaid() {
		return this.amountPaid;
	}
	private int sessionID;

	public void setSessionID(int value) {
		this.sessionID = value;
	}

	@Id
	@GeneratedValue
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

	   private CourseOffering courseOffering;

	   @ManyToOne(optional=false)
	   public CourseOffering getCourseOffering() {
	      return this.courseOffering;
	   }

	   public void setCourseOffering(CourseOffering co2) {
	      this.courseOffering = co2;
	   }


	   private Room room;

	   @ManyToOne(optional=false)
	   public Room getRoom() {
	      return this.room;
	   }
	   
	   public void setRoom(Room room) {
	      this.room = room;
	   }
   
   }
