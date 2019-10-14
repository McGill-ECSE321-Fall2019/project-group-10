package ca.mcgill.ecse321.project.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import java.util.Set;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class Room{
	private Set<Session> session;
	   
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
	
	@Id
	public int getRoomNumber() {
	    return this.roomNumber;
	}
   
  }
