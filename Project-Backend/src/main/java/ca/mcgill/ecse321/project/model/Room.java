package ca.mcgill.ecse321.project.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class Room{
private Set<Session> session1;
   
   @OneToMany(mappedBy="room" )
   public Set<Session> getSession1() {
      return this.session1;
   }
   
   public void setSession1(Set<Session> session1s) {
      this.session1 = session1s;
   }
   
   private int roomNumber;

public void setRoomNumber(int value) {
    this.roomNumber = value;
}

@Id
public int getRoomNumber() {
    return this.roomNumber;
}
   private Set<String/*No type specified*/> session;
   
   @OneToMany
   public Set<String/*No type specified*/> getSession() {
      return this.session;
   }
   
   public void setSession(Set<String/*No type specified*/> sessions) {
      this.session = sessions;
   }
   
   }
