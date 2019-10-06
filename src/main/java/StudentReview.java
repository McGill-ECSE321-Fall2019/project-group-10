import java.util.Set;
import java.util.HashSet;

public class StudentReview extends Review {
   /**
    * <pre>
    *           0..*     0..*
    * StudentReview ------------------------- Tutor
    *           author        &lt;       tutor
    * </pre>
    */
   private Set<Tutor> tutor;
   
   public Set<Tutor> getTutor() {
      if (this.tutor == null) {
         this.tutor = new HashSet<Tutor>();
      }
      return this.tutor;
   }
   
   }
