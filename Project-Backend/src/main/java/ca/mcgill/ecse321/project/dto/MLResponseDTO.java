package ca.mcgill.ecse321.project.dto;

public class MLResponseDTO {

	private String response;
	private String errorPhrase;
	
	public MLResponseDTO() {
	}
	
	public MLResponseDTO(String response, String errorPhrase) {
		this.response = response;
		this.errorPhrase = errorPhrase;
	}
	
	public String getResponse() {
		return response;
	}
	
	public void setResponse(String response) {
		this.response = response;
	}
	
	public String getErrorPhrase() {
		return errorPhrase;
	}
	
	public void setErrorPhrase(String errorPhrase) {
		this.errorPhrase = errorPhrase;
	}
	
}
