package ca.mcgill.ecse321.project.service;

import org.junit.After; 
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ca.mcgill.ecse321.project.model.*;
import ca.mcgill.ecse321.project.ErrorStrings;
import ca.mcgill.ecse321.project.dao.*;

@RunWith(SpringRunner.class)
@SpringBootTest

public class RoomTest {

	@Autowired
	private TutoringAppService service;
	@Autowired 
	private AvailabilityRepository availabilityRepository;
	@Autowired
	private CourseOfferingRepository courseOfferingRepository;  
	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private ReviewRepository reviewRepository;  
	@Autowired
	private RoleRepository roleRepository; 
	@Autowired
	private RoomRepository roomRepository; 
	@Autowired
	private SessionRepository sessionRepository; 
	@Autowired
	private UniversityRepository universityRepository; 
	@Autowired
	private UserRepository userRepository;

	@After
	public void clearDatabase() {
		// clear the databases in order of dependencies
		sessionRepository.deleteAll();
		roomRepository.deleteAll();
		reviewRepository.deleteAll();
		courseOfferingRepository.deleteAll();
		courseRepository.deleteAll();
		universityRepository.deleteAll();
		availabilityRepository.deleteAll();
		roleRepository.deleteAll();
		userRepository.deleteAll();
	}

//	creates a new room for the session
	@Test
	public void testCreateRoom() {
//		sets up the information 
		int roomNumber = 1;

		try {
			service.createRoom(roomNumber);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<Room> allRooms = service.getAllRooms(); //gets the list of all the rooms

		// check that it was created and all the attributes are correct
		assertEquals(1, allRooms.size()); //checks the the room size is set
		assertEquals(roomNumber, allRooms.get(0).getRoomNumber()); //checks if the room number is set
	}

//	test to delete a room
	@Test
	public void testDeleteRoom() {
		assertEquals(0, service.getAllCourseOfferings().size());
//		sets up information for the room
		int roomNumber = 1;

		try {
			service.createRoom(roomNumber);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		try {
			service.deleteRoom(roomNumber);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		// check that the correct error was generated
		List<Room> allRooms = service.getAllRooms(); //gets the list of all rooms
		assertEquals(0, allRooms.size()); // checks the room is deleted
	}

//	test to update the room
	@Test
	public void testUpdateRoom() {
		assertEquals(0, service.getAllCourseOfferings().size());

		int roomNumber = 1;


		try {
			service.createRoom(roomNumber);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<Room> allRooms = service.getAllRooms(); //gets the list of all the rooms

		assertEquals(1, allRooms.size()); //gets the size of the room
		assertEquals(roomNumber, allRooms.get(0).getRoomNumber()); //checks the roomNumber
		int newRoomNumber  = 2;

		try {
			service.updateRoom(roomNumber, newRoomNumber);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		// check that the correct error was generated
		allRooms = service.getAllRooms(); //gets the list of all the rooms
		assertEquals(1, allRooms.size()); //checks the size of the list
		assertEquals(newRoomNumber, allRooms.get(0).getRoomNumber()); //checks if the room number is updated
	}

//	test to check if the room ID is null
	@Test
	public void testCreateRoomNullID() {
//		sets up information to create a room
		int roomNumber = -1;

		String error = null;

		try {
			service.createRoom(roomNumber);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}

		// check that the correct error was generated
		assertEquals(ErrorStrings.Invalid_Room_NegativeNumber, error);
		List<Room> allRooms = service.getAllRooms(); //gets the list of all the rooms

		assertEquals(0, allRooms.size()); // checks the size of the list

	}


}
