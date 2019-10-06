import java.util.Set;
import java.util.HashSet;

public class Session {
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
   
   private int time;
   
   public void setTime(int value) {
      this.time = value;
   }
   
   public int getTime() {
      return this.time;
   }
   
   /**
    * <pre>
    *           0..*     0..1
    * Session ------------------------- Room
    *           session        &lt;       room
    * </pre>
    */
   private Room room;
   
   public void setRoom(Room value) {
      this.room = value;
   }
   
   public Room getRoom() {
      return this.room;
   }
   
   /**
    * <pre>
    *           0..*     1..1
    * Session ------------------------- CourseOffering
    *           session        &gt;       courseOffering
    * </pre>
    */
   private CourseOffering courseOffering;
   
   public void setCourseOffering(CourseOffering value) {
      this.courseOffering = value;
   }
   
   public CourseOffering getCourseOffering() {
      return this.courseOffering;
   }
   
   /**
    * <pre>
    *           0..*     1..15
    * Session ------------------------- Student
    *           session        &lt;       student
    * </pre>
    */
   private Set<Student> student;
   
   public Set<Student> getStudent() {
      if (this.student == null) {
         this.student = new HashSet<Student>();
      }
      return this.student;
   }
   
   /**
    * <pre>
    *           0..*     1..1
    * Session ------------------------- Tutor
    *           session        &lt;       tutor
    * </pre>
    */
   private Tutor tutor;
   
   public void setTutor(Tutor value) {
      this.tutor = value;
   }
   
   public Tutor getTutor() {
      return this.tutor;
   }
   
   }
