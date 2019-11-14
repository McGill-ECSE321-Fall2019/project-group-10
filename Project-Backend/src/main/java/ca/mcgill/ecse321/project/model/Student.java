package ca.mcgill.ecse321.project.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.ManyToMany;

@Entity
public class Student extends Role{
	private Set<Session> session;

	@ManyToMany(fetch = FetchType.EAGER, mappedBy="student", cascade = CascadeType.ALL)
	public Set<Session> getSession() {
		return this.session;
	}

	public void setSession(Set<Session> sessions) {
		this.session = sessions;
	}
	
	public void addSession(Session s) {
		if(this.session == null) {
			this.session = new HashSet<>();
		}
		this.session.add(s);
	}

}
