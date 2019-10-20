package ca.mcgill.ecse321.project.service;

import org.junit.After;
import org.junit.Before;
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
	

	@Before
	public void setUp(){
		// user necessary to create a tutor which is needed for availabilities
		//service.createUser("aName", "test.object@mcgill.ca", 22, "5145555555");
		//service.createTutor("username", "password", "aName", 12, 3, Education.highschool);
	}

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

		long millis=System.currentTimeMillis();  		
		Date date = new java.sql.Date(millis);
		Time time = new java.sql.Time(millis);
		int id = 1;
		
		try {
			service.createAvailability(date, time, "username");
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<Availability> allAvailabilities = service.getAllAvailabilities();

		// check that it was created and all the attributes are correct
		assertEquals(1, allAvailabilities.size());
		assertEquals(id, allAvailabilities.get(0).getId());	
		assertEquals(date, allAvailabilities.get(0).getDate());
		assertEquals(time, allAvailabilities.get(0).getTime());
		//assertEquals("username", allAvailabilities.get(0).getTutor().getUsername());

		date = Date.valueOf("2010-09-10");
		id = 2;
		time = Time.valueOf("10:10:10");


		try {
			service.updateAvailability(1, date, time, "username");
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		// check that all the attributes are correct
		assertEquals(1, allAvailabilities.size());
		assertEquals(id, allAvailabilities.get(0).getId());	
		assertEquals(date, allAvailabilities.get(0).getDate());
		assertEquals(time, allAvailabilities.get(0).getTime());
		//assertEquals("username", allAvailabilities.get(0).getTutor().getUsername());

	}

	
	@Test
	public void testDeleteAvailability() {
		assertEquals(0, service.getAllAvailabilities().size());

		long millis=System.currentTimeMillis();  		
		Date date = new java.sql.Date(millis);
		int id = 1;
		Time time = new java.sql.Time(millis);

		try {
			service.createAvailability(date, time, "username");
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		
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

		long millis=System.currentTimeMillis();  		
		Date date = null;
		Time time = new java.sql.Time(millis);
		int id = 1;
	
		
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

		long millis=System.currentTimeMillis();  		
		Date date = new java.sql.Date(millis);
		Time time = null;
		int id = 2;
		
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
	public void testCreateAvailabilityInvalidID() {
		assertEquals(0, service.getAllAvailabilities().size());

		long millis=System.currentTimeMillis();  		
		Date date = new java.sql.Date(millis);
		Time time = Time.valueOf("");
		int id = -1;
		

		String error = null;

		try {
			service.createAvailability(date, time, "username");

		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Incorrect id value for the availability...", error);

		// check no change in memory
		assertEquals(0, service.getAllAvailabilities().size());

	}
	
	@Test
	public void testCreateAvailabilityNullTutor() {
		assertEquals(0, service.getAllAvailabilities().size());

		long millis=System.currentTimeMillis();  		
		Date date = new java.sql.Date(millis);
		int id = 1;
		Time time = new java.sql.Time(millis);

		String error = null;

		try {
			service.createAvailability(date, time, "unknown");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Please specify a valid Tutor", error);

		// check no change in memory
		assertEquals(0, service.getAllAvailabilities().size());

	}
}
