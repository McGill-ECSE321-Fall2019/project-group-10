
public class Availability {
   private int date;
   
   public void setDate(int value) {
      this.date = value;
   }
   
   public int getDate() {
      return this.date;
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
    *           0..*     1..1
    * Availability ------------------------- Tutor
    *           availability        &lt;       tutor
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
