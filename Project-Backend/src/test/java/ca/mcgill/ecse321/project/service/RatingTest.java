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
//import needed for tutoring app service 
import ca.mcgill.ecse321.project.service.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RatingTest {
	
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
		// needed objects for a rating to be created 
		service.createUser("aName", "test.tester@mcgill.ca", 22, "5145555555");
		service.createTutor("cmc", "dogs", "test.tester@mcgill.ca", 12, 3, Education.bachelor);
		service.createUniversity("McGill", "3040 University");
		service.createCourse("Intro to Software","ECSE 321", service.getAllUniversities().get(0).getUniversityID());
		service.createCourseOffering(Term.Summer, 2019, service.getAllCourses().get(0).getCourseID());
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
	public void testCreateRating() {

		int ratingValue = 1;
		String revieweeUsername = "cmc";
		int coID = service.getAllCourses().get(0).getCourseID();		

		try {
			service.createRating(ratingValue, revieweeUsername, coID);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<Rating> allRatings = service.getAllRatings();
		// check that all the attributes are correct
		assertEquals(1, allRatings.size());
		assertEquals(ratingValue, allRatings.get(0).getRatingValue());
		assertEquals(revieweeUsername, allRatings.get(0).getWrittenAbout().getUsername());
		assertEquals(coID, allRatings.get(0).getCourseOffering().getCourseOfferingID());
	}
	
	@Test
	public void testUpdateRating() {

		int ratingValue = 1;
		String revieweeUsername = "cmc";
		int coID = service.getAllCourses().get(0).getCourseID();	

		try {
			service.createRating(ratingValue, revieweeUsername, coID);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<Rating> allRatings = service.getAllRatings();

		assertEquals(1, allRatings.size());
		
		ratingValue = 2;
		
		try {
			service.updateRating(allRatings.get(0).getReviewID(), ratingValue, revieweeUsername, coID);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		
		allRatings = service.getAllRatings();
		
		assertEquals(1, allRatings.size());
		assertEquals(ratingValue, allRatings.get(0).getRatingValue());
	}
	
	@Test
	public void testDeleteRating() {

		int ratingValue = 1;
		String revieweeUsername = "cmc";
		int coID = service.getAllCourses().get(0).getCourseID();

		try {
			service.createRating(ratingValue, revieweeUsername, coID);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		
		List<Rating> allRatings = service.getAllRatings();
		
		try {
			service.deleteRating(allRatings.get(0).getReviewID());
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		allRatings = service.getAllRatings();

		assertEquals(0, allRatings.size());
	}
	
	@Test
	public void testCreateRatingNullUsername() {

		int ratingValue = 1;
		String revieweeUsername = null;
		int coID = service.getAllCourses().get(0).getCourseID();
		
		String error = null;

		try {
			service.createRating(ratingValue, revieweeUsername, coID);
		} catch (IllegalArgumentException e) {
			// Check that  error occurred
			error = e.getMessage();
		}
		// check that the correct error was generated
		assertEquals(error, "Please insert a reviewee username...");
		List<Rating> allRatings = service.getAllRatings();

		assertEquals(0, allRatings.size());
	}
	
	@Test
	public void testCreateRatingNullCourseOffering() {

		int ratingValue = 1;
		String revieweeUsername = "cmc";
		int coID = 4;
		
		String error = null;

		try {
			service.createRating(ratingValue, revieweeUsername, coID);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		// check that the correct error was generated
		assertEquals(error, "Please enter a valid Course Offering");
		List<Rating> allRatings = service.getAllRatings();

		assertEquals(0, allRatings.size());
	}
	
	@Test
	public void testCreateRatingOutOfBounds() {

		int ratingValue = -1;
		String revieweeUsername = "cmc";
		int coID = service.getAllCourses().get(0).getCourseID();
		
		String error = null;

		try {
			service.createRating(ratingValue, revieweeUsername, coID);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		// check that the correct error was generated
		assertEquals(error, "Rating value must be between 1 and 5");
		List<Rating> allRatings = service.getAllRatings();

		assertEquals(0, allRatings.size());
	}
	
	@Test
	public void testCreateRatingInvalidID() {

		int ratingValue = 1;
		String revieweeUsername = "cmc";
		int coID = 3;
		
		String error = null;

		try {
			service.createRating(ratingValue, revieweeUsername, coID);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		// check that the correct error was generated
		assertEquals(error, "Incorrect id value");
		List<Rating> allRatings = service.getAllRatings();

		assertEquals(0, allRatings.size());
	}
	
}