package ca.mcgill.ecse321.project.dto;

import ca.mcgill.ecse321.project.model.CourseOffering;
import ca.mcgill.ecse321.project.model.Role;

@SuppressWarnings("unused")
public class RoleDTO extends ReviewDTO{

	private String username;
	private String password;
	
	//Data transfer object session.
	public RoleDTO() {
	}

	public RoleDTO(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}
	//get rating
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
}
