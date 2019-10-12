package ca.mcgill.ecse321.project.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class User{
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
private int phoneNumber;

public void setPhoneNumber(int value) {
    this.phoneNumber = value;
}
public int getPhoneNumber() {
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
