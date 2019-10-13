package ca.mcgill.ecse321.project.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;
import java.sql.Date;
import java.sql.Time;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ca.mcgill.ecse321.project.service.TutoringAppService;
import ca.mcgill.ecse321.project.model.*;
import ca.mcgill.ecse321.project.dao.*;
import ca.mcgill.ecse321.project.service.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SessionTest {
	
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
		service.createUniversity("McGill", "3040 University", 1);
		service.createCourse("Intro to Software","ECSE 321", 2, 1);
		service.createCourseOffering(3, "fall", 2019, 2);
		service.createUser("aName", "email", 22, 5145555555.0);
		service.createUser("aName_student", "email_student", 22, 5145555555.0);
		service.createTutor("username", "password", "aName", 12, 3, Education.highschool);
		service.createStudent("studentUser", "password2", "email_student");
	}
	
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
	public void testCreateSession() {
		assertEquals(0, service.getAllSessions().size());
		
		long millis=System.currentTimeMillis();  		
		Date date = new java.sql.Date(millis);
		Time time = new java.sql.Time(millis);
		int amountPaid = 23;
		int id = 4;

		try {
			service.createSession(3, date, time, amountPaid, id, "studentUser", "username");
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<Session> allSessions = service.getAllSessions();

		assertEquals(1, allSessions.size());
		assertEquals(id, allSessions.get(0).getSessionID());
		assertEquals(time, allSessions.get(0).getTime());
		assertEquals(amountPaid, allSessions.get(0).getAmountPaid());
		assertEquals(date, allSessions.get(0).getDate());
		assertEquals(3, allSessions.get(0).getCourseOffering().getCourseOfferingID());
		assertEquals("studentUser", allSessions.get(0).getStudent().get(0).getUsername());
		assertEquals("username", allSessions.get(0).getTutor().getUsername());
	}
	
	@Test
	public void testUpdateSession() {
		assertEquals(0, service.getAllSessions().size());
		
		long millis=System.currentTimeMillis();  		
		Date date = new java.sql.Date(millis);
		Time time = new java.sql.Time(millis);
		int amountPaid = 23;
		int id = 4;

		try {
			service.createSession(3, date, time, amountPaid, id, "studentUser", "username");
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		
		List<Session> allSessions = service.getAllSessions();

		assertEquals(1, allSessions.size());
		assertEquals(id, allSessions.get(0).getSessionID());
		assertEquals(time, allSessions.get(0).getTime());
		assertEquals(amountPaid, allSessions.get(0).getAmountPaid());
		assertEquals(date, allSessions.get(0).getDate());
		assertEquals(3, allSessions.get(0).getCourseOffering().getCourseOfferingID());
		assertEquals("studentUser", allSessions.get(0).getStudent().get(0).getUsername());
		assertEquals("username", allSessions.get(0).getTutor().getUsername());
		
		date = new java.sql.Date(millis-20);
		time = Time.valueOf("10:10:10");
		amountPaid = 24;
		id = 5;
		
		try {
			service.updateSession(4, 3, date, time, amountPaid, id, "studentUser", "username");
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		
		allSessions = service.getAllSessions();
		
		assertEquals(1, allSessions.size());
		assertEquals(id, allSessions.get(0).getSessionID());
		assertEquals(time, allSessions.get(0).getTime());
		assertEquals(amountPaid, allSessions.get(0).getAmountPaid());
		assertEquals(date, allSessions.get(0).getDate());
		assertEquals(3, allSessions.get(0).getCourseOffering().getCourseOfferingID());
		assertEquals("studentUser", allSessions.get(0).getStudent().get(0).getUsername());
		assertEquals("username", allSessions.get(0).getTutor().getUsername());
		
	}
	
	@Test
	public void testDeleteSession() {
		assertEquals(0, service.getAllSessions().size());
		
		long millis=System.currentTimeMillis();  		
		Date date = new java.sql.Date(millis);
		Time time = new java.sql.Time(millis);
		int amountPaid = 23;
		int id = 4;

		try {
			service.createSession(3, date, time, amountPaid, id, "studentUser", "username");
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		
		List<Session> allSessions = service.getAllSessions();
		assertEquals(1, allSessions.size());
		
		try {
			service.deleteSession(id);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		 allSessions = service.getAllSessions();

		assertEquals(0, allSessions.size());
	}
	
	@Test
	public void testCreateAvailabilityNullCourseOffering() {
		assertEquals(0, service.getAllSessions().size());

		long millis=System.currentTimeMillis();  		
		Date date = new java.sql.Date(millis);
		Time time = new java.sql.Time(millis);
		int amountPaid = 23;
		int id = 4;

		String error = null;

		try {
			service.createSession(100, date, time, amountPaid, id, "studentUser", "username");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		// assertEquals("Session course offering cannot be empty!", error);

		// check no change in memory
		assertEquals(0, service.getAllSessions().size());

	}
	
	@Test
	public void testCreateAvailabilityNullTutor() {
		assertEquals(0, service.getAllSessions().size());

		long millis=System.currentTimeMillis();  		
		Date date = new java.sql.Date(millis);
		Time time = new java.sql.Time(millis);
		int amountPaid = 23;
		int id = 4;

		String error = null;

		try {
			service.createSession(3, date, time, amountPaid, id, "studentUser", "wrongusername");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		// assertEquals("Session course offering cannot be empty!", error);

		// check no change in memory
		assertEquals(0, service.getAllSessions().size());

	}
	
	@Test
	public void testCreateAvailabilityNullStudent() {
		assertEquals(0, service.getAllSessions().size());

		long millis=System.currentTimeMillis();  		
		Date date = new java.sql.Date(millis);
		Time time = new java.sql.Time(millis);
		int amountPaid = 23;
		int id = 4;

		String error = null;

		try {
			service.createSession(3, date, time, amountPaid, id, "wrongstudentUser", "username");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		// assertEquals("Session course offering cannot be empty!", error);

		// check no change in memory
		assertEquals(0, service.getAllSessions().size());
	}
	
}