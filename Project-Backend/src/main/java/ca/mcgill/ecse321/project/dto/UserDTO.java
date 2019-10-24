package ca.mcgill.ecse321.project.dto;

public class UserDTO {

	private int age;
	private String name;
	private String email;
	private String phoneNumber;

	
	//Data transfer object user.
	public UserDTO() {
	}

	public UserDTO(int age, String name, String email, String phoneNumber) {
		this.age = age; 
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}

	//Get age
	public int getAge() {
		return age;
	}

	//get name
	public String getName() {
		return name;
	}
	
	//get email
	public String getEmail() {
		return email;
	}
	
	//get phone number
	public String getPhoneNumber() {
		return phoneNumber;
	}

	//Get age
	public void setAge(int age) {
		this.age = age;
	}
		//get name
	public void setName(String name) {
		this.name = name;
	}
	
	//get email
	public void setEmail(String email) {
		this.email = email;
	}
	
	//get phone number
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber =  phoneNumber;
	}

}