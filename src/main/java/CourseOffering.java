import java.util.Set;
import java.util.HashSet;

public class CourseOffering {
   private String term;
   
   public void setTerm(String value) {
      this.term = value;
   }
   
   public String getTerm() {
      return this.term;
   }
   
   private int year;
   
   public void setYear(int value) {
      this.year = value;
   }
   
   public int getYear() {
      return this.year;
   }
   
   /**
    * <pre>
    *           0..*     1..1
    * CourseOffering ------------------------> Courses
    *           courseOffering        &gt;       courses
    * </pre>
    */
   private Courses courses;
   
   public void setCourses(Courses value) {
      this.courses = value;
   }
   
   public Courses getCourses() {
      return this.courses;
   }
   
   /**
    * <pre>
    *           1..1     0..*
    * CourseOffering ------------------------- Session
    *           courseOffering        &lt;       session
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
    *           1..1     0..*
    * CourseOffering ------------------------- Review
    *           courseOffering        &lt;       review
    * </pre>
    */
   private Set<Review> review;
   
   public Set<Review> getReview() {
      if (this.review == null) {
         this.review = new HashSet<Review>();
      }
      return this.review;
   }
   
   }
