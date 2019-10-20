package ca.mcgill.ecse321.project.dto;

public class StudentDTO {

	private String username;
	private String password;

	
	//Data transfer object user.
	public StudentDTO() {
	}

	public StudentDTO(String username, String password) {
		this.username = username; 
		this.password = password;
	}

	//get username
	public String getUsername() {
		return username;
	}
	
	//get password
	public String getPassword() {
		return password;
	}


}