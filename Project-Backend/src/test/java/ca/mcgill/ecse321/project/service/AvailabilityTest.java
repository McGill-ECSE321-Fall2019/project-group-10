package ca.mcgill.ecse321.project.service;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.Date;
import java.sql.Time;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ca.mcgill.ecse321.project.model.*;
import ca.mcgill.ecse321.project.dao.*;

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
	
	private String USERNAME = "cmc";
	private String PASSWORD = "dogs";
	private String EMAIL = "test.tester@mcgill.ca";
	private double HR  = 12;
	private int EXP = 3;
	
	private java.sql.Date DATE = new Date(10121995);
	private java.sql.Time TIME = new Time(123);

	@After
	public void clearDatabase() {
		// clear in order of dependencies
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
		
		Date date = DATE;
		Time time = TIME;
		int id;
		
		try {
			service.createUser("aName", EMAIL, 22, "5145555555");
			service.createTutor(USERNAME, PASSWORD, EMAIL, HR, EXP, Education.masters);
			service.createAvailability(date, time, USERNAME);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<Availability> allAvailabilities = service.getAllAvailabilities();

		// check that it was created and all the attributes are correct
		assertEquals(1, allAvailabilities.size());
		assertEquals(date.toString(), allAvailabilities.get(0).getDate().toString());
		assertEquals(time.toString(), allAvailabilities.get(0).getTime().toString());

		date = new java.sql.Date(3333333333l);
		id = allAvailabilities.get(0).getId();
		time = new java.sql.Time(123456798l);


		try {
			service.updateAvailability(id, date, time, USERNAME);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		
		allAvailabilities = service.getAllAvailabilities();
		
		// check that all the attributes are correct
		assertEquals(1, allAvailabilities.size());
		assertEquals(date.toString(), allAvailabilities.get(0).getDate().toString());
		assertEquals(time.toString(), allAvailabilities.get(0).getTime().toString());

	}
	
	@Test
	public void testDeleteAvailability() {
		assertEquals(0, service.getAllAvailabilities().size());

		try {
			
			service.createUser("aName", EMAIL, 22, "5145555555");
			service.createTutor(USERNAME, PASSWORD, EMAIL, HR, EXP, Education.masters);
			service.createAvailability(DATE, TIME, USERNAME);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		
		int id = service.getAllAvailabilities().get(0).getId();
		
		try {
			service.deleteAvailability(id);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<Availability> allAvailabilities = service.getAllAvailabilities();

		assertEquals(0, allAvailabilities.size());
	}
	
	@Test
	public void testCreateAvailabilityNullDate() {
		assertEquals(0, service.getAllAvailabilities().size());
		
		Date date = null;
		Time time = TIME;
	
		
		String error = null;

		try {
			service.createAvailability(date, time, "username");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Invalid date parameters...", error);

		// check no change in memory
		assertEquals(0, service.getAllAvailabilities().size());

	}
	
	@Test
	public void testCreateAvailabilityNullTime() {
		assertEquals(0, service.getAllAvailabilities().size());
		
		Date date = DATE;
		Time time = null;
		
		String error = null;

		try {
			service.createAvailability(date, time, "username");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Invalid time parameters...", error);

		// check no change in memory
		assertEquals(0, service.getAllAvailabilities().size());

	}
	
	@Test
	public void testCreateAvailabilityNullTutor() {
		assertEquals(0, service.getAllAvailabilities().size());


		String error = null;

		try {
			service.createAvailability(DATE, TIME, "unknown");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Please specify a valid Tutor", error);

		// check no change in memory
		assertEquals(0, service.getAllAvailabilities().size());

	}
}
