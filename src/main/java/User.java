import java.util.Set;
import java.util.HashSet;

public class User {
   private int age;
   
   public void setAge(int value) {
      this.age = value;
   }
   
   public int getAge() {
      return this.age;
   }
   
   private String name;
   
   public void setName(String value) {
      this.name = value;
   }
   
   public String getName() {
      return this.name;
   }
   
   private String email;
   
   public void setEmail(String value) {
      this.email = value;
   }
   
   public String getEmail() {
      return this.email;
   }
   
   private int phoneNumber;
   
   public void setPhoneNumber(int value) {
      this.phoneNumber = value;
   }
   
   public int getPhoneNumber() {
      return this.phoneNumber;
   }
   
   /**
    * <pre>
    *           1..1     0..2
    * User ------------------------- Role
    *           user        &gt;       role
    * </pre>
    */
   private Set<Role> role;
   
   public Set<Role> getRole() {
      if (this.role == null) {
         this.role = new HashSet<Role>();
      }
      return this.role;
   }
   
   }
