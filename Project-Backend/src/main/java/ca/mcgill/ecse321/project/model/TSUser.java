package ca.mcgill.ecse321.project.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class TSUser{

	private int id;
	public void setId(int id) {
		this.id = id;
	}

	@Id
	@GeneratedValue
	public int getId() {
		return this.id;
	}

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
	private String phoneNumber;

	public void setPhoneNumber(String phoneNum) {
		this.phoneNumber = phoneNum;
	}
	public String getPhoneNumber() {
		return this.phoneNumber;
	}
	private Set<Role> role;

	@OneToMany(mappedBy="user" )
	public Set<Role> getRole() {
		return this.role;
	}

	public void setRole(Set<Role> roles) {
		this.role = roles;
	}

}
