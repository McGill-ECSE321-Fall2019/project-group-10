package ca.mcgill.ecse321.project.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class Room{
	private Set<Session> session;
	//private boolean isAvailable;

	@OneToMany(mappedBy="room" )
	public Set<Session> getSession() {
		return this.session;
	}

	public void setSession(Set<Session> session1s) {
		this.session = session1s;
	}

	private int roomNumber;

	public void setRoomNumber(int value) {
		this.roomNumber = value;
	}

	public int getRoomNumber() {
		return this.roomNumber;
	}

	private int id;
	public void setId(int id) {
		this.id = id;
	}

	@Id
	@GeneratedValue
	public int getId() {
		return this.id;
	}
	
	//public boolean isAvailable() {
	//	return isAvailable;
	//}

}
