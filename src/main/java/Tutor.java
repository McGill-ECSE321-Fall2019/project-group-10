import java.util.Set;
import java.util.HashSet;

public class Tutor extends Role {
   private int hourlyRate;
   
   public void setHourlyRate(int value) {
      this.hourlyRate = value;
   }
   
   public int getHourlyRate() {
      return this.hourlyRate;
   }
   
   private int experience;
   
   public void setExperience(int value) {
      this.experience = value;
   }
   
   public int getExperience() {
      return this.experience;
   }
   
   private Education educationLevel;
   
   public void setEducationLevel(Education value) {
      this.educationLevel = value;
   }
   
   public Education getEducationLevel() {
      return this.educationLevel;
   }
   
   /**
    * <pre>
    *           1..1     0..*
    * Tutor ------------------------- Availability
    *           tutor        &gt;       availability
    * </pre>
    */
   private Set<Availability> availability;
   
   public Set<Availability> getAvailability() {
      if (this.availability == null) {
         this.availability = new HashSet<Availability>();
      }
      return this.availability;
   }
   
   /**
    * <pre>
    *           1..1     0..*
    * Tutor ------------------------- Session
    *           tutor        &gt;       session
    * </pre>
    */
   private Set<Session> session;
   
   public Set<Session> getSession() {
      if (this.session == null) {
         this.session = new HashSet<Session>();
      }
      return this.session;
   }
   
   /**
    * <pre>
    *           0..*     0..*
    * Tutor ------------------------- StudentReview
    *           tutor        &gt;       author
    * </pre>
    */
   private Set<StudentReview> author;
   
   public Set<StudentReview> getAuthor() {
      if (this.author == null) {
         this.author = new HashSet<StudentReview>();
      }
      return this.author;
   }
   
   }
