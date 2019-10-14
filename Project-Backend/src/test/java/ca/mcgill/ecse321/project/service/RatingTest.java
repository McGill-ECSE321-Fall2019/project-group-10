package ca.mcgill.ecse321.project.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.*;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ca.mcgill.ecse321.project.service.TutoringAppService;
import ca.mcgill.ecse321.project.model.*;
import ca.mcgill.ecse321.project.dao.*;
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
		service.createUser("aName", "test.tester@mcgill.ca", 22, "5145555555");
		service.createTutor("cmc", "dogs", "test.tester@mcgill.ca", 12, 3, Education.bachelor);
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
	public void testCreateRating() {

		int id = 5;
		int ratingValue = 1;
		String revieweeUsername = "cmc";
		int coID = 3;		

		try {
			service.createRating(id, ratingValue, revieweeUsername, coID);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<Rating> allRatings = service.getAllRatings();

		assertEquals(1, allRatings.size());
		assertEquals(id, allRatings.get(0).getReviewID());
		assertEquals(ratingValue, allRatings.get(0).getRatingValue());
		assertEquals(revieweeUsername, allRatings.get(0).getWrittenAbout().getUsername());
		assertEquals(coID, allRatings.get(0).getCourseOffering().getCourseOfferingID());
	}
	
	@Test
	public void testUpdateRating() {

		int id = 5;
		int ratingValue = 1;
		String revieweeUsername = "cmc";
		int coID = 3;	

		try {
			service.createRating(id, ratingValue, revieweeUsername, coID);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<Rating> allRatings = service.getAllRatings();

		assertEquals(1, allRatings.size());
		
		id = 6;
		ratingValue = 2;
		
		try {
			service.updateRating(5, id, ratingValue, revieweeUsername, coID);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		
		assertEquals(1, allRatings.size());
		assertEquals(id, allRatings.get(0).getReviewID());
		assertEquals(ratingValue, allRatings.get(0).getRatingValue());
	}
	
	@Test
	public void testDeleteRating() {

		int id = 5;
		int ratingValue = 1;
		String revieweeUsername = "cmc";
		int coID = 3;

		try {
			service.createRating(id, ratingValue, revieweeUsername, coID);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		
		try {
			service.deleteRating(id);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<Rating> allRatings = service.getAllRatings();

		assertEquals(0, allRatings.size());
	}
	
	@Test
	public void testCreateRatingNullUsername() {

		int id = 5;
		int ratingValue = 1;
		String revieweeUsername = null;
		int coID = 3;
		
		String error = null;

		try {
			service.createRating(id, ratingValue, revieweeUsername, coID);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}

		assertEquals(error, "Please insert a reviewee username...");
		List<Rating> allRatings = service.getAllRatings();

		assertEquals(0, allRatings.size());
	}
	
	@Test
	public void testCreateRatingNullCourseOffering() {

		int id = 5;
		int ratingValue = 1;
		String revieweeUsername = "cmc";
		int coID = 4;
		
		String error = null;

		try {
			service.createRating(id, ratingValue, revieweeUsername, coID);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}

		assertEquals(error, "Please enter a valid Course Offering");
		List<Rating> allRatings = service.getAllRatings();

		assertEquals(0, allRatings.size());
	}
	
	@Test
	public void testCreateRatingOutOfBounds() {

		int id = 5;
		int ratingValue = -1;
		String revieweeUsername = "cmc";
		int coID = 3;
		
		String error = null;

		try {
			service.createRating(id, ratingValue, revieweeUsername, coID);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}

		assertEquals(error, "Rating value must be between 1 and 5");
		List<Rating> allRatings = service.getAllRatings();

		assertEquals(0, allRatings.size());
	}
	
	@Test
	public void testCreateRatingInvalidID() {

		int id = -5;
		int ratingValue = 1;
		String revieweeUsername = "cmc";
		int coID = 3;
		
		String error = null;

		try {
			service.createRating(id, ratingValue, revieweeUsername, coID);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}

		assertEquals(error, "Incorrect id value");
		List<Rating> allRatings = service.getAllRatings();

		assertEquals(0, allRatings.size());
	}
	
}