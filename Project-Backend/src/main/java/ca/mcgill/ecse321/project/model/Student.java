package ca.mcgill.ecse321.project.model;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;

import java.util.Set;
import javax.persistence.ManyToMany;

@Entity
public class Student extends Role{
	private Set<Session> session;

	@ManyToMany(fetch = FetchType.EAGER, mappedBy="student")
	public Set<Session> getSession() {
		return this.session;
	}

	public void setSession(Set<Session> sessions) {
		this.session = sessions;
	}

}
