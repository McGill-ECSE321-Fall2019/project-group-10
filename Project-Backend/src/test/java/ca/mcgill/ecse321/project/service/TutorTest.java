package ca.mcgill.ecse321.project.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
 
import ca.mcgill.ecse321.project.service.TutoringAppService;
import ca.mcgill.ecse321.project.model.*;
import ca.mcgill.ecse321.project.dao.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TutorTest {
	
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

	@Before
	public void setUp(){
		service.createUser("aName", EMAIL, 22, "5145555555");
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
	//Test if creating tutor works
	public void testCreateTutor() {

		try {
			service.createTutor(USERNAME, PASSWORD, EMAIL, HR, EXP, Education.masters);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<Tutor> allTutors = service.getAllTutors();

		assertEquals(1, allTutors.size());
		assertEquals(USERNAME, allTutors.get(0).getUsername());
		assertEquals(PASSWORD, allTutors.get(0).getPassword());
		assertEquals(HR, allTutors.get(0).getHourlyRate(), 0.05);
		assertEquals(EXP, allTutors.get(0).getExperience());
		assertEquals(Education.masters, allTutors.get(0).getEducation());
		assertEquals(EMAIL, allTutors.get(0).getUser().getEmail());
	}
	
	@Test
	//Test if updating tutor works
	public void testUpdateTutor() {

		try {
			service.createTutor(USERNAME, PASSWORD, EMAIL, HR, EXP, Education.masters);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<Tutor> allTutors = service.getAllTutors();

		assertEquals(1, allTutors.size());
		
		String username = "amc";
		String password = "cats";
		double hr  = 14;
		int exp = 4;
		
		try {
			service.updateTutor(USERNAME, username, password, EMAIL, hr, exp, Education.masters);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		
		allTutors = service.getAllTutors();

		assertEquals(username, allTutors.get(0).getUsername());
		assertEquals(password, allTutors.get(0).getPassword());
		assertEquals(hr, allTutors.get(0).getHourlyRate(), 0.05);
		assertEquals(exp, allTutors.get(0).getExperience());
	}
	
	@Test
	//Test if deleting tutor works
	public void testDeleteTutor() {

		try {
			service.createTutor(USERNAME, PASSWORD, EMAIL, HR, EXP, Education.masters);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		
		try {
			service.deleteTutor(USERNAME);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		// check that the tutor was deleted
		List<Tutor> allTutors = service.getAllTutors();

		assertEquals(0, allTutors.size());
	}
	
	@Test
	//Test that creating a tutor without username fails
	public void testCreateTutorNullUsername() {

		String error = null;

		try {
			service.createTutor(null, PASSWORD, EMAIL, HR, EXP, Education.masters);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		// check that the correct error was generated
		assertEquals("Please insert a username...", error);
		List<Tutor> allTutors = service.getAllTutors();

		assertEquals(0, allTutors.size());
	}
	
	@Test
	//Test that creating a tutor w/out a password fails
	public void testCreateTutorNullPassword() {
		
		String error = null;

		try {
			service.createTutor(USERNAME, null, EMAIL, HR, EXP, Education.masters);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		// check that the correct error was generated
		assertEquals("Please insert a password...", error);
		List<Tutor> allTutors = service.getAllTutors();

		assertEquals(0, allTutors.size());
	}
	
	@Test
	//Test that creating a user with an email not linked to a user fails
	public void testCreateTutorNullUser() {

		String error = null;

		try {
			service.createTutor(USERNAME, PASSWORD, "emailwrong@email.ca", HR, EXP, Education.masters);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}

		// check that the correct error was generated

		assertEquals("Please input a valid user", error);
		List<Tutor> allTutors = service.getAllTutors();

		assertEquals(0, allTutors.size());
	}
	
	@Test
	//Test that creating a tutor with an email in the wrong format fails
	public void testCreateTutorWrongEmail() {
		
		String error = null;

		try {
			service.createTutor(USERNAME, PASSWORD, "emailwrong", HR, EXP, Education.masters);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		// check that the correct error was generated

		assertEquals("Please insert a proper email...", error);
		List<Tutor> allTutors = service.getAllTutors();

		assertEquals(0, allTutors.size());
	}
	
	@Test
	//Test that creating a tutor with an invalid HR fails
	public void testCreateTutorInvalidHR() {

		double hr  = -12;
		
		String error = null;

		try {
			service.createTutor(USERNAME, PASSWORD, "test.tester@mcgill.ca", hr, EXP, Education.masters);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		// check that the correct error was generated

		assertEquals("Don't think you want to pay the student for your session...", error);
		List<Tutor> allTutors = service.getAllTutors();

		assertEquals(0, allTutors.size());
	}
	
	@Test
	//Test that creating a tutor with an invalid exp fails
	public void testCreateTutorInvalidExp() {

		int exp = -3;
		
		String error = null;

		try {
			service.createTutor(USERNAME, PASSWORD, EMAIL, HR, exp, Education.masters);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		// check that the correct error was generated

		assertEquals("Please input a valid number of years for your experience...", error);
		List<Tutor> allTutors = service.getAllTutors();

		assertEquals(0, allTutors.size());
	}
	
	@Test
	//Test that creating a tutor w/out specifying education level fails
	public void testCreateTutorInvalidEducation() {

		String error = null;

		try {
			service.createTutor(USERNAME, PASSWORD, EMAIL, HR, EXP, null);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		// check that the correct error was generated

		assertEquals("Please provide your education level...", error);
		List<Tutor> allTutors = service.getAllTutors();

		assertEquals(0, allTutors.size());
	}
}