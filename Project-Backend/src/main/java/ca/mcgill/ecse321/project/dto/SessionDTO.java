package ca.mcgill.ecse321.project.dto;

import java.sql.Date;
import java.sql.Time;
import java.util.List;


public class SessionDTO {

	private Date date;
	private Time time;
	private Double amountPaid;
	private List<StudentDTO> students;
	private CourseOfferingDTO courseOffering;
	private RoomDTO room;
	private TutorDTO tutor;
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

	public SessionDTO(Time time, double amountPaid, Date date, TutorDTO tutor, List<StudentDTO> students, CourseOfferingDTO courseOffering, RoomDTO room, boolean isConfirmed) {
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

	public void setStudentsDTO(List<StudentDTO> students) {
		this.students = students;
	}

	public void setCourseOfferingDTO(CourseOfferingDTO courseOffering) {
		this.courseOffering = courseOffering;
	}

	public void setRoomDTO(RoomDTO room) {
		this.room = room;
	}

	public void setTutorDTO(TutorDTO tutor) {
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
	
	public CourseOfferingDTO getCourseOfferingDTO() {
		return courseOffering;
	}
	
	public List<StudentDTO> getStudentsDTO(){
		return students;
	}
	
	public RoomDTO getRoomDTO() {
		
		return room;
		
	}
	
	public TutorDTO getTutorDTO() {
		
		return tutor;
		
	}
	
}
