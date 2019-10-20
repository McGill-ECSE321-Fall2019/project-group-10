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
	

	@Before
	public void setUp(){
		service.createUser("aName", "test.tester@mcgill.ca", 22, "5145555555");
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
	public void testCreateTutor() {

		String username = "cmc";
		String password = "dogs";
		double hr  = 12;
		int exp = 3;

		try {
			service.createTutor(username, password, "test.tester@mcgill.ca", hr, exp, Education.masters);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<Tutor> allTutors = service.getAllTutors();

		assertEquals(1, allTutors.size());
		assertEquals(username, allTutors.get(0).getUsername());
		assertEquals(password, allTutors.get(0).getPassword());
		assertEquals(hr, allTutors.get(0).getHourlyRate(), 0.05);
		assertEquals(exp, allTutors.get(0).getExperience());
		assertEquals(Education.masters, allTutors.get(0).getEducation());
		assertEquals("test.tester@mcgill.ca", allTutors.get(0).getUser().getEmail());
	}
	
	@Test
	public void testUpdateTutor() {

		String username = "cmc";
		String password = "dogs";
		double hr  = 12;
		int exp = 3;

		try {
			service.createTutor(username, password, "test.tester@mcgill.ca", hr, exp, Education.masters);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<Tutor> allTutors = service.getAllTutors();

		assertEquals(1, allTutors.size());
		
		username = "amc";
		password = "cats";
		hr  = 14;
		exp = 4;
		
		try {
			service.updateTutor("cmc", username, password, "test.tester@mcgill.ca", hr, exp, Education.masters);
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
	public void testDeleteTutor() {

		String username = "cmc";
		String password = "dogs";
		double hr  = 12;
		int exp = 3;

		try {
			service.createTutor(username, password, "test.tester@mcgill.ca", hr, exp, Education.masters);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		
		try {
			service.deleteTutor(username);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		// check that the tutor was deleted
		List<Tutor> allTutors = service.getAllTutors();

		assertEquals(0, allTutors.size());
	}
	
	@Test
	public void testCreateTutorNullUsername() {

		String username = null;
		String password = "dogs";
		double hr  = 12;
		int exp = 3;
		
		String error = null;

		try {
			service.createTutor(username, password, "test.tester@mcgill.ca", hr, exp, Education.masters);
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
	public void testCreateTutorNullPassword() {

		String username = "cmc";
		String password = null;
		double hr  = 12;
		int exp = 3;
		
		String error = null;

		try {
			service.createTutor(username, password, "test.tester@mcgill.ca", hr, exp, Education.masters);
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
	public void testCreateTutorNullUser() {

		String username = "cmc";
		String password = "dogs";
		double hr  = 12;
		int exp = 3;
		
		String error = null;

		try {
			service.createTutor(username, password, "emailwrong@email.ca", hr, exp, Education.masters);
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
	public void testCreateTutorWrongEmail() {

		String username = "cmc";
		String password = "test";
		double hr  = 12;
		int exp = 3;
		
		String error = null;

		try {
			service.createTutor(username, password, "emailwrong", hr, exp, Education.masters);
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
	public void testCreateTutorInvalidHR() {

		String username = "cmc";
		String password = "dogs";
		double hr  = -12;
		int exp = 3;
		
		String error = null;

		try {
			service.createTutor(username, password, "test.tester@mcgill.ca", hr, exp, Education.masters);
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
	public void testCreateTutorInvalidExp() {

		String username = "cmc";
		String password = "dogs";
		double hr  = 12;
		int exp = -3;
		
		String error = null;

		try {
			service.createTutor(username, password, "test.tester@mcgill.ca", hr, exp, Education.masters);
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
	public void testCreateTutorInvalidEducation() {

		String username = "cmc";
		String password = "dogs";
		double hr  = 12;
		int exp = 3;
		
		String error = null;

		try {
			service.createTutor(username, password, "test.tester@mcgill.ca", hr, exp, null);
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