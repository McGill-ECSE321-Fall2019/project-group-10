package ca.mcgill.ecse321.project.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class Room{
	private Set<Session> session;

	@OneToMany(mappedBy="room" )
	public Set<Session> getSession() {
		return this.session;
	}

	public void setSession(Set<Session> session1s) {
		this.session = session1s;
	}
	
	public void addSession(Session s) {
		if(session == null) {
			session = new HashSet<>();
		}
		this.session.add(s);
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

}
