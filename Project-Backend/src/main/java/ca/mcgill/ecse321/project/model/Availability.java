package ca.mcgill.ecse321.project.model;
import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
public class Availability{
    
    private int id;
    
    public void setId(int value){
        this.id = value;
    } 
    
    @Id
    @GeneratedValue
    public int getId(){
        return this.id;
    }
private Date date;

public void setDate(Date value) {
    this.date = value;
}
public Date getDate() {
    return this.date;
}

private Time time;

public void setTime(Time value) {
    this.time = value;
}
public Time getTime() {
    return this.time;
}

	private Tutor tutor;
	
	@ManyToOne(optional=false)
	public Tutor getTutor() {
	  return this.tutor;
	}
	
	public void setTutor(Tutor tutor) {
	  this.tutor = tutor;
	}
}
