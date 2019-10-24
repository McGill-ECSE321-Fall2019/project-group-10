package ca.mcgill.ecse321.project.dto;

public class StudentDTO {

	private String username;
	private String password;

	
	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

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