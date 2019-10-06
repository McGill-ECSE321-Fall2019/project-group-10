import java.util.Set;
import java.util.HashSet;

public class Room {
   private int roomNumber;
   
   public void setRoomNumber(int value) {
      this.roomNumber = value;
   }
   
   public int getRoomNumber() {
      return this.roomNumber;
   }
   
   private RoomType roomType;
   
   public void setRoomType(RoomType value) {
      this.roomType = value;
   }
   
   public RoomType getRoomType() {
      return this.roomType;
   }
   
   /**
    * <pre>
    *           0..1     0..*
    * Room ------------------------- Session
    *           room        &gt;       session
    * </pre>
    */
   private Set<Session> session;
   
   public Set<Session> getSession() {
      if (this.session == null) {
         this.session = new HashSet<Session>();
      }
      return this.session;
   }
   
   }
