package ca.mcgill.ecse321.project.dto;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import ca.mcgill.ecse321.project.model.*;

public class SessionDTO {

	private Date date;
	private Time time;
	private Double amountPaid;
	private List<Student> students;
	private CourseOffering courseOffering;
	private Room room;
	private Tutor tutor;
	private boolean isConfirmed;
	
	public boolean isConfirmed() {
		return isConfirmed;
	}

	public void setConfirmed(boolean isConfirmed) {
		this.isConfirmed = isConfirmed;
	}

	//Data transfer object session.
	public SessionDTO() {
	}

	public SessionDTO(Time time, double amountPaid, Date date, Tutor tutor, List<Student> students, CourseOffering courseOffering, Room room, boolean isConfirmed) {
		this.date = date;
		this.time = time;
		this.amountPaid = amountPaid;
		this.courseOffering = courseOffering;
		this.students = students;
		this.room = room;
		this.tutor = tutor;
		this.isConfirmed = isConfirmed;
				
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public void setAmountPaid(Double amountPaid) {
		this.amountPaid = amountPaid;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public void setCourseOffering(CourseOffering courseOffering) {
		this.courseOffering = courseOffering;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public void setTutor(Tutor tutor) {
		this.tutor = tutor;
	}

	//get rating
	public Date getDate() {
		return date;
	}
	
	public Time getTime() {
		return time;
	}
	
	public double getAmountPaid() {
		return amountPaid;
	}
	
	public CourseOffering getCourseOffering() {
		return courseOffering;
	}
	
	public List<Student> getStudents(){
		return students;
	}
	
	public Room getRoom() {
		
		return room;
		
	}
	
	public Tutor getTutor() {
		
		return tutor;
		
	}
	
}
