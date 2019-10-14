package ca.mcgill.ecse321.project.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

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
	
	@Before
	public void setUp(){
		service.createUniversity("McGill", "3040 University", 1);
		service.createCourse("Intro to Software","ECSE 321", 2, 1);
		service.createCourseOffering(3, "fall", 2019, 2);
		service.createUser("aName", "tutor.tester@mcgill.ca", 22, "5145555555");
		service.createUser("aName_student", "student.tester@mcgill.ca", 22, "5145555555");
		service.createTutor("username", "password", "tutor.tester@mcgill.ca", 12, 3, Education.highschool);
		service.createStudent("studentUser", "password2", "student.tester@mcgill.ca");
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
		double amountPaid = 23;
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
		assertEquals(amountPaid, allSessions.get(0).getAmountPaid(), 0.05);
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
		double amountPaid = 23;
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
		assertEquals(amountPaid, allSessions.get(0).getAmountPaid(), 0.05);
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
		assertEquals(amountPaid, allSessions.get(0).getAmountPaid(), 0.05);
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
		double amountPaid = 23;
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
		double amountPaid = 23;
		int id = 4;

		String error = null;

		try {
			service.createSession(100, date, time, amountPaid, id, "studentUser", "username");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Please input a valid course offering", error);

		// check no change in memory
		assertEquals(0, service.getAllSessions().size());

	}
	
	@Test
	public void testCreateAvailabilityNullTutor() {
		assertEquals(0, service.getAllSessions().size());

		long millis=System.currentTimeMillis();  		
		Date date = new java.sql.Date(millis);
		Time time = new java.sql.Time(millis);
		double amountPaid = 23;
		int id = 4;

		String error = null;

		try {
			service.createSession(3, date, time, amountPaid, id, "studentUser", "wrongusername");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Please input a valid tutor", error);

		// check no change in memory
		assertEquals(0, service.getAllSessions().size());

	}
	
	@Test
	public void testCreateAvailabilityNullStudent() {
		assertEquals(0, service.getAllSessions().size());

		long millis=System.currentTimeMillis();  		
		Date date = new java.sql.Date(millis);
		Time time = new java.sql.Time(millis);
		double amountPaid = 23;
		int id = 4;

		String error = null;

		try {
			service.createSession(3, date, time, amountPaid, id, "wrongstudentUser", "username");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Please input a valid student", error);

		// check no change in memory
		assertEquals(0, service.getAllSessions().size());
	}
	
	@Test
	public void testCreateAvailabilityInvalidID() {
		assertEquals(0, service.getAllSessions().size());

		long millis=System.currentTimeMillis();  		
		Date date = new java.sql.Date(millis);
		Time time = new java.sql.Time(millis);
		double amountPaid = 23;
		int id = -4;

		String error = null;

		try {
			service.createSession(3, date, time, amountPaid, id, "studentUser", "username");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Incorrect id value for the session update...", error);

		// check no change in memory
		assertEquals(0, service.getAllSessions().size());
	}
	
	@Test
	public void testCreateAvailabilityInvalidAmountPaid() {
		assertEquals(0, service.getAllSessions().size());

		long millis=System.currentTimeMillis();  		
		Date date = new java.sql.Date(millis);
		Time time = new java.sql.Time(millis);
		double amountPaid = -23;
		int id = 4;

		String error = null;

		try {
			service.createSession(3, date, time, amountPaid, id, "studentUser", "username");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("So your student is paying you?? Please provide positive amount paid...", error);

		// check no change in memory
		assertEquals(0, service.getAllSessions().size());
	}
	
	@Test
	public void testCreateAvailabilityNullTime() {
		assertEquals(0, service.getAllSessions().size());

		long millis=System.currentTimeMillis();  		
		Date date = new java.sql.Date(millis);
		Time time = null;
		double amountPaid = 23;
		int id = 4;

		String error = null;

		try {
			service.createSession(3, date, time, amountPaid, id, "studentUser", "username");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Invalid time parameters...", error);

		// check no change in memory
		assertEquals(0, service.getAllSessions().size());
	}
	
	@Test
	public void testCreateAvailabilityNullDate() {
		assertEquals(0, service.getAllSessions().size());

		long millis=System.currentTimeMillis();  		
		Date date = null;
		Time time = new java.sql.Time(millis);
		double amountPaid = 23;
		int id = 4;

		String error = null;

		try {
			service.createSession(3, date, time, amountPaid, id, "studentUser", "username");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Invalid time parameters...", error);

		// check no change in memory
		assertEquals(0, service.getAllSessions().size());
	}
	
}