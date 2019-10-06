
public class Courses {
   private String description;
   
   public void setDescription(String value) {
      this.description = value;
   }
   
   public String getDescription() {
      return this.description;
   }
   
   private String courseName;
   
   public void setCourseName(String value) {
      this.courseName = value;
   }
   
   public String getCourseName() {
      return this.courseName;
   }
   
   private int courseID;
   
   public void setCourseID(int value) {
      this.courseID = value;
   }
   
   public int getCourseID() {
      return this.courseID;
   }
   
   /**
    * <pre>
    *           1..*     1..1
    * Courses ------------------------- University
    *           courses        &lt;       university
    * </pre>
    */
   private University university;
   
   public void setUniversity(University value) {
      this.university = value;
   }
   
   public University getUniversity() {
      return this.university;
   }
   
   }
