package ca.mcgill.ecse321.project.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.sql.Date;
import java.sql.Time;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Session{
	private Date date;
	private Time time;
	//private boolean isActive = true;

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
	private List<Student> student;

	@ManyToMany(fetch = FetchType.EAGER)
	public List<Student> getStudent() {
		return this.student;
	}

	public void setStudent(List<Student> students) {
		this.student = students;
	}

	private Tutor tutor;

	@ManyToOne(fetch = FetchType.EAGER, optional=false)
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

	@ManyToOne
	public Room getRoom() {
		return this.room;
	}

	public void setRoom(Room room) {
		this.room = room;
		Set<Session> sessions = room.getSession();
		if(sessions == null) {
			Set<Session> session = new HashSet<>();
			session.add(this);
			room.setSession(session);
		}
		else {
			sessions.add(this);
			room.setSession(sessions);
		}
	}

	//public boolean getIsActive() {
	//	return isActive;
	//}
	
	//public void setActivity(boolean isActive) {
	//	this.isActive = isActive;
	//}
	
}
