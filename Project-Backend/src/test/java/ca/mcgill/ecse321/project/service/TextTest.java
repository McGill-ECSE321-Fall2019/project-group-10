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
		service.createUser("aName", "email@mcgill.ca", 22, "5145555555");
		service.createTutor("cmc", "dogs", "email@mcgill.ca", 12, 3, Education.bachelor);
		service.createUniversity("McGill", "3040 University");
		service.createCourse("Intro to Software","ECSE 321", service.getAllUniversities().get(0).getUniversityID());
		service.createCourseOffering("fall", 2019, service.getAllCourses().get(0).getCourseID());
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

	//	test to create text
	@Test
	public void testCreateText() {
		//	sets up information for creating a text
		String description = "great tutor";
		boolean isAllowed = true;
		String revieweeUsername = "cmc";
		int coID = service.getAllCourseOfferings().get(0).getCourseOfferingID();		

		try {
			service.createText(description, isAllowed, revieweeUsername, coID);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<Text> allTexts = service.getAllTexts(); //gets the list of all the texts

		assertEquals(1, allTexts.size()); //tests if the size of the list is being updated
		assertEquals(description, allTexts.get(0).getDescription()); //checks the description of the text
		assertEquals(isAllowed, allTexts.get(0).getIsAllowed()); 
		assertEquals(revieweeUsername, allTexts.get(0).getWrittenAbout().getUsername()); //checks the username of the reviewee
		assertEquals(coID, allTexts.get(0).getCourseOffering().getCourseOfferingID()); //checks the ID of the courseOffering
	}

	//	test to update the text
	@Test
	public void testUpdateText() {
		//		sets up the information to create Reviews
		String description = "great tutor";
		boolean isAllowed = true;
		String revieweeUsername = "cmc";
		int coID = service.getAllCourseOfferings().get(0).getCourseOfferingID();	

		try {
			service.createText(description, isAllowed, revieweeUsername, coID);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<Text> allTexts = service.getAllTexts(); //checks the list of all the texts

		assertEquals(1, allTexts.size()); //checks the size of the review list

		description = "horrible tutor";
		isAllowed = false;

		try {
			service.updateText(service.getAllTexts().get(0).getReviewID(),description, isAllowed, revieweeUsername, coID);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		allTexts = service.getAllTexts();

		assertEquals(1, allTexts.size()); //checks the size of the review
		assertEquals(description, allTexts.get(0).getDescription()); //checks the size of the description
		assertEquals(isAllowed, allTexts.get(0).getIsAllowed()); 
	}

	//	test to delete the text
	@Test
	public void testDeleteText() {
		//		sets up information for the reviews
		String description = "great tutor";
		boolean isAllowed = true;
		String revieweeUsername = "cmc";
		int coID = service.getAllCourseOfferings().get(0).getCourseOfferingID();

		try {
			service.createText(description, isAllowed, revieweeUsername, coID);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<Text> allTexts = service.getAllTexts(); //gets the list of the reviews

		try {
			service.deleteText(allTexts.get(0).getReviewID());
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		allTexts = service.getAllTexts(); 

		assertEquals(0, allTexts.size()); //checks the size of the review
	}


	//	test to create text with null username
	@Test
	public void testCreateTextNullUsername() {
		//		sets up the information to create a review
		String description = "great tutor";
		boolean isAllowed = true;
		String revieweeUsername = null;
		int coID = service.getAllCourseOfferings().get(0).getCourseOfferingID();

		String error = null;

		try {
			service.createText(description, isAllowed, revieweeUsername, coID);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		// check error
		assertEquals("Please insert a reviewee username...", error);

		List<Text> allTexts = service.getAllTexts(); // gets the list of all the text 

		assertEquals(0, allTexts.size()); // checks the size of the list
	}


	//	test to create a text with null course offering
	@Test
	public void testCreateTextNullCourseOffering() {
		//		test to create a review
		String description = "great tutor";
		boolean isAllowed = true;
		String revieweeUsername = "cmc";
		int coID = 4;

		String error = null;

		try {
			service.createText(description, isAllowed, revieweeUsername, coID);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}

		// check error
		assertEquals("Please enter a valid Course Offering", error);
		List<Text> allTexts = service.getAllTexts(); //get the list of all the review
		assertEquals(0, allTexts.size()); //checks the size of the list
	}

	//	test to create a text with null description
	@Test
	public void testCreateTextNullDescription() {
		//		sets up a new review
		String description = null;
		boolean isAllowed = true;
		String revieweeUsername = "cmc";
		int coID = service.getAllCourseOfferings().get(0).getCourseOfferingID();

		String error = null;

		try {
			service.createText(description, isAllowed, revieweeUsername, coID);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		// check that the correct error was generated
		assertEquals(error, "Please insert a brief description...");
		List<Text> allTexts = service.getAllTexts(); //gets the list of all the reviews

		assertEquals(0, allTexts.size()); //checks the size of the list
	}

}