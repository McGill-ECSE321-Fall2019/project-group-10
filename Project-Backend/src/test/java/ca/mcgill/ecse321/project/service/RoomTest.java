package ca.mcgill.ecse321.project.service;

import org.junit.After; 
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ca.mcgill.ecse321.project.model.*;
import ca.mcgill.ecse321.project.dao.*;
import ca.mcgill.ecse321.project.service.*;

@RunWith(SpringRunner.class)
@SpringBootTest

public class RoomTest {
	
	@Mock
	private TutoringAppService service;

	@Mock 
	private AvailabilityRepository availabilityRepository;
	@Mock
	private CourseOfferingRepository courseOfferingRepository;  
	@Mock
	private CourseRepository courseRepository;
	@Mock
	private ReviewRepository reviewRepository;  
	@Mock
	private RoleRepository roleRepository; 
	@Mock
	private RoomRepository roomRepository; 
	@Mock
	private SessionRepository sessionRepository; 
	@Mock
	private UniversityRepository universityRepository; 
	@Mock
	private UserRepository userRepository;
	
	@After
	public void clearDatabase() {
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
	
	@Test
	public void testCreateRoom() {

		int roomNumber = 1;

		try {
			service.createRoom(roomNumber);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<Room> allRooms = service.getAllRooms();

		assertEquals(1, allRooms.size());
		assertEquals(roomNumber, allRooms.get(0).getRoomNumber());
		}
	
	
	@Test
	public void testDeleteRoom() {
		assertEquals(0, service.getAllCourseOfferings().size());
		
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

		List<Room> allRooms = service.getAllRooms();
		assertEquals(0, allRooms.size());
	}
	
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

		List<Room> allRooms = service.getAllRooms();

		assertEquals(1, allRooms.size());
		assertEquals(roomNumber, allRooms.get(0).getRoomNumber());
		int newRoomNumber  = 2;
		
		try {
			service.updateRoom(roomNumber, newRoomNumber);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		allRooms = service.getAllRooms();
		assertEquals(1, allRooms.size());
		assertEquals(newRoomNumber, allRooms.get(0).getRoomNumber());
		}
	
	
	@Test
	public void testCreateRoomNullID() {

		int roomNumber = -1;
	
		String error = null;

		try {
			service.createRoom(roomNumber);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}

		assertEquals("Room number cannot be negative", error);
		List<Room> allRooms = service.getAllRooms();

		assertEquals(0, allRooms.size());

	}
	
	
}
