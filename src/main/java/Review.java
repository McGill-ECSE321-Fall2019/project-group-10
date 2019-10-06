
public class Review {
   private String description;
   
   public void setDescription(String value) {
      this.description = value;
   }
   
   public String getDescription() {
      return this.description;
   }
   
   private int reviewID;
   
   public void setReviewID(int value) {
      this.reviewID = value;
   }
   
   public int getReviewID() {
      return this.reviewID;
   }
   
   /**
    * <pre>
    *           0..*     1..1
    * Review ------------------------- CourseOffering
    *           review        &gt;       courseOffering
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
    *           0..*     1..1
    * Review ------------------------- Role
    *           review        &lt;       role
    * </pre>
    */
   private Role role;
   
   public void setRole(Role value) {
      this.role = value;
   }
   
   public Role getRole() {
      return this.role;
   }
   
   }
