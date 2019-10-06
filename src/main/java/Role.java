import java.util.Set;
import java.util.HashSet;

public class Role {
   private String username;
   
   public void setUsername(String value) {
      this.username = value;
   }
   
   public String getUsername() {
      return this.username;
   }
   
   private String password;
   
   public void setPassword(String value) {
      this.password = value;
   }
   
   public String getPassword() {
      return this.password;
   }
   
   /**
    * <pre>
    *           0..2     1..1
    * Role ------------------------- User
    *           role        &lt;       user
    * </pre>
    */
   private User user;
   
   public void setUser(User value) {
      this.user = value;
   }
   
   public User getUser() {
      return this.user;
   }
   
   /**
    * <pre>
    *           1..1     0..*
    * Role ------------------------- Review
    *           role        &gt;       review
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
