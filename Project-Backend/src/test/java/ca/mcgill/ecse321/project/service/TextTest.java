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
public class TextTest {
	
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
		service.createUser("aName", "email", 22, "5145555555");
		service.createTutor("cmc", "dogs", "email", 12, 3, Education.bachelor);
		service.createUniversity("McGill", "3040 University", 1);
		service.createCourse("Intro to Software","ECSE 321", 2, 1);
		service.createCourseOffering(3, "fall", 2019, 2);
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
	public void testCreateText() {

		int id = 5;
		String description = "great tutor";
		boolean isAllowed = true;
		String revieweeUsername = "cmc";
		int coID = 3;		

		try {
			service.createText(id, description, isAllowed, revieweeUsername, coID);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<Text> allTexts = service.getAllTexts();

		assertEquals(1, allTexts.size());
		assertEquals(id, allTexts.get(0).getReviewID());
		assertEquals(description, allTexts.get(0).getDescription());
		assertEquals(isAllowed, allTexts.get(0).getIsAllowed());
		assertEquals(revieweeUsername, allTexts.get(0).getWrittenAbout().getUsername());
		assertEquals(coID, allTexts.get(0).getCourseOffering().getCourseOfferingID());
	}
	
	@Test
	public void testUpdateText() {

		int id = 5;
		String description = "great tutor";
		boolean isAllowed = true;
		String revieweeUsername = "cmc";
		int coID = 3;	

		try {
			service.createText(id, description, isAllowed, revieweeUsername, coID);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<Text> allTexts = service.getAllTexts();

		assertEquals(1, allTexts.size());
		
		id = 6;
		description = "horrible tutor";
		isAllowed = false;
		
		try {
			service.updateText(5, id, description, isAllowed, revieweeUsername, coID);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		
		assertEquals(1, allTexts.size());
		assertEquals(id, allTexts.get(0).getReviewID());
		assertEquals(description, allTexts.get(0).getDescription());
		assertEquals(isAllowed, allTexts.get(0).getIsAllowed());
	}
	
	@Test
	public void testDeleteText() {

		int id = 5;
		String description = "great tutor";
		boolean isAllowed = true;
		String revieweeUsername = "cmc";
		int coID = 3;

		try {
			service.createText(id, description, isAllowed, revieweeUsername, coID);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		
		try {
			service.deleteText(id);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<Text> allTexts = service.getAllTexts();

		assertEquals(0, allTexts.size());
	}
	
	@Test
	public void testCreateTextNullUsername() {

		int id = 5;
		String description = "great tutor";
		boolean isAllowed = true;
		String revieweeUsername = null;
		int coID = 3;
		
		String error = null;

		try {
			service.createText(id, description, isAllowed, revieweeUsername, coID);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		// check error
		assertEquals("Please insert a reviewee username...", error);

		List<Text> allTexts = service.getAllTexts();

		assertEquals(0, allTexts.size());
	}
	
	@Test
	public void testCreateTextNullCourseOffering() {

		int id = 5;
		String description = "great tutor";
		boolean isAllowed = true;
		String revieweeUsername = "cmc";
		int coID = 4;
		
		String error = null;

		try {
			service.createText(id, description, isAllowed, revieweeUsername, coID);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}

		// check error
		assertEquals("Please enter a valid Course Offering", error);
		List<Text> allTexts = service.getAllTexts();
		assertEquals(0, allTexts.size());
	}
	
	@Test
	public void testCreateTextNullDescription() {

		int id = 5;
		String description = null;
		boolean isAllowed = true;
		String revieweeUsername = "cmc";
		int coID = 3;
		
		String error = null;

		try {
			service.createText(id, description, isAllowed, revieweeUsername, coID);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		// check that the correct error was generated
		assertEquals(error, "Please insert a brief description...");
		List<Text> allTexts = service.getAllTexts();

		assertEquals(0, allTexts.size());
	}
	
	@Test
	public void testCreateTextInvalidID() {

		int id = -5;
		String description = "great session";
		boolean isAllowed = true;
		String revieweeUsername = "cmc";
		int coID = 3;
		
		String error = null;

		try {
			service.createText(id, description, isAllowed, revieweeUsername, coID);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		// check that the correct error was generated
		assertEquals(error, "Incorrect id value");
		List<Text> allTexts = service.getAllTexts();

		assertEquals(0, allTexts.size());
	}
}