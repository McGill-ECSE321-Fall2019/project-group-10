import java.util.Set;
import java.util.HashSet;

public class University {
   private String name;
   
   public void setName(String value) {
      this.name = value;
   }
   
   public String getName() {
      return this.name;
   }
   
   private String address;
   
   public void setAddress(String value) {
      this.address = value;
   }
   
   public String getAddress() {
      return this.address;
   }
   
   private int universityID;
   
   public void setUniversityID(int value) {
      this.universityID = value;
   }
   
   public int getUniversityID() {
      return this.universityID;
   }
   
   /**
    * <pre>
    *           1..1     1..*
    * University ------------------------- Courses
    *           university        &gt;       courses
    * </pre>
    */
   private Set<Courses> courses;
   
   public Set<Courses> getCourses() {
      if (this.courses == null) {
         this.courses = new HashSet<Courses>();
      }
      return this.courses;
   }
   
   }
