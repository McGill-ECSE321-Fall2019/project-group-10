package ca.mcgill.ecse321.project.dto;

public class RoomDTO {

	private int roomNumber;
	private String roomType;


	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public RoomDTO() {
	}

	public RoomDTO(int roomNumber, String roomType) {
		this.roomNumber = roomNumber; 
		this.roomType = roomType;
	}

	public RoomDTO(int roomNumber) {
		this.roomNumber = roomNumber; 
		this.roomType = "regular";
	}

	public int getRoomNumber() {
		return roomNumber;
	}

	public String getRoomType() {
		return roomType;
	}


}