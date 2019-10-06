import java.util.Set;
import java.util.HashSet;

public class Student extends Role {
   /**
    * <pre>
    *           1..15     0..*
    * Student ------------------------- Session
    *           student        &gt;       session
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
    * Student ------------------------> TutorReview
    *           student        &gt;       author
    * </pre>
    */
   private Set<TutorReview> author;
   
   public Set<TutorReview> getAuthor() {
      if (this.author == null) {
         this.author = new HashSet<TutorReview>();
      }
      return this.author;
   }
   
   }
