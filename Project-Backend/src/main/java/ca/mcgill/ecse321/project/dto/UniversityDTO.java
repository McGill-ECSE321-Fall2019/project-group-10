package ca.mcgill.ecse321.project.dto;

public class UniversityDTO {

	private String name;
	private String address;

	
	//Data transfer object user.
	public UniversityDTO() {
	}

	public UniversityDTO(String name, String address) {
		this.name = name; 
		this.address = address;
	}

	//get username
	public String getAddress() {
		return address;
	}
	
	//get password
	public String getName() {
		return name;
	}


}