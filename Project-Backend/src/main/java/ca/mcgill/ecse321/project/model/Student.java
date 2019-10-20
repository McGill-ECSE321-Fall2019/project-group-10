package ca.mcgill.ecse321.project.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.util.Set;
import javax.persistence.ManyToMany;

@Entity
public class Student extends Role{
   
    private int id;
    
    @Id
    @GeneratedValue
    public void setId(int value){
        this.id = value;
    } 

    public int getId(){
        return this.id;
    }
	
	private Set<Session> session;
   
   @ManyToMany(mappedBy="student")
   public Set<Session> getSession() {
      return this.session;
   }
   
   public void setSession(Set<Session> sessions) {
      this.session = sessions;
   }
   
   }
