package ca.mcgill.ecse321.project.dto;


public class TextDTO extends ReviewDTO{

	private boolean isAllowed;
	private String description;	
	
	//Data transfer object session.
	public TextDTO() {
	}

	public TextDTO(boolean isAllowed, String description) {
		this.isAllowed = isAllowed;
		this.description = description;
	}

	//get rating
	public boolean getIsAllowed() {
		return isAllowed;
	}
	
	public String getDescription() {
		return description;
	}


}
