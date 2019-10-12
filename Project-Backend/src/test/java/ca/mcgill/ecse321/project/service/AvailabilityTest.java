package ca.mcgill.ecse321.project.service;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.project.service.TutoringAppService;
import ca.mcgill.ecse321.project.model.*;
import ca.mcgill.ecse321.project.dao.*;
import ca.mcgill.ecse321.project.service.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AvailabilityTest {
	
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
	public void testCreateAvailability() {
		assertEquals(0, service.getAllAvailabilities().size());

		int date = 1009;
		int id = 1;
		int time = 10;
		
		Tutor tutor = new Tutor();

		try {
			service.createAvailability(date, time, id, tutor);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<Availability> allAvailabilities = service.getAllAvailabilities();

		assertEquals(1, allAvailabilities.size());
		assertEquals(id, allAvailabilities.get(0).getAvailabilityID());
	}
	
	@Test
	public void testCreateAvailabilityNullDate() {
		assertEquals(0, service.getAllAvailabilities().size());

		int date = (Integer) null;
		int id = 1;
		int time = 10;
		
		Tutor tutor = new Tutor();
		
		String error = null;

		try {
			service.createPerson(name);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		// assertEquals("Availability date cannot be empty!", error);

		// check no change in memory
		assertEquals(0, service.getAllAvailabilities().size());

	}
	
	@Test
	public void testCreateAvailabilityNullID() {
		assertEquals(0, service.getAllAvailabilities().size());

		int date = 09102019;
		int id = (Integer) null;
		int time = 10;
		
		Tutor tutor = new Tutor();
		
		String error = null;

		try {
			service.createPerson(name);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		// assertEquals("Availability id cannot be empty!", error);

		// check no change in memory
		assertEquals(0, service.getAllAvailabilities().size());

	}
	
	@Test
	public void testCreateAvailabilityNullTime() {
		assertEquals(0, service.getAllAvailabilities().size());

		int date = 09102019;
		int id = 1;
		int time = (Integer) null;
		
		Tutor tutor = new Tutor();
		
		String error = null;

		try {
			service.createPerson(name);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		// assertEquals("Availability time cannot be empty!", error);

		// check no change in memory
		assertEquals(0, service.getAllAvailabilities().size());

	}
	
	@Test
	public void testCreateAvailabilityNullTutor() {
		assertEquals(0, service.getAllAvailabilities().size());

		int date = 09102019;
		int id = 1;
		int time = 10;
		
		Tutor tutor = null;
		
		String error = null;

		try {
			service.createPerson(name);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		// assertEquals("Availability tutor cannot be empty!", error);

		// check no change in memory
		assertEquals(0, service.getAllAvailabilities().size());

	}

}
