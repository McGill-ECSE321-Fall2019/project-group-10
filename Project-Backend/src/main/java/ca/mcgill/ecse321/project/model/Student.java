package ca.mcgill.ecse321.project.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.ManyToMany;

@Entity
public class Student extends Role{
   private Set<Session> session;
   
   
   public Set<Session> getSession() {
      return this.session;
   }
   
   @ManyToMany(mappedBy="student")
   public void setSession(Set<Session> sessions) {
      this.session = sessions;
   }
   
   }
