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
import ca.mcgill.ecse321.project.ErrorStrings;
import ca.mcgill.ecse321.project.dao.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CourseOfferingTest {

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
	@Autowired
	private TutorRepository tutorRepository; 
	@Autowired
	private StudentRepository studentRepository; 

	@Before
	public void setUp(){
		service.createUniversity("McGill", "3040 University");
		service.createCourse("Intro to Software","ECSE 321", service.getAllUniversities().get(0).getUniversityID());
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
		tutorRepository.deleteAll();
		studentRepository.deleteAll();
		userRepository.deleteAll();
	}

	//	creates a course offering 
	@Test
	public void testCreateCourseOffering() {
		assertEquals(0, service.getAllCourseOfferings().size());
		//		sets up information for courseOffering
		int year = 2019;
		int courseID = service.getAllCourses().get(0).getCourseID();

		try {
			service.createCourseOffering(Term.Fall, year, courseID);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<CourseOffering> allCO = service.getAllCourseOfferings(); //gets the list of all the courseOffering
		// check that all the attributes are correct
		assertEquals(1, allCO.size()); //checks if the list is populating
		assertEquals(year, allCO.get(0).getYear()); //checks for the Year the course is offered
		assertEquals(Term.Fall, allCO.get(0).getTerm()); //check which term is the course being offered
		assertEquals(courseID, allCO.get(0).getCourse().getCourseID()); // checks for the courseID
	}
//	deletes the course 
	@Test
	public void testDeleteCourseOffering() {
		assertEquals(0, service.getAllCourseOfferings().size());
//		sets up information for the course
		int year = 2019;
		int courseID = service.getAllCourses().get(0).getCourseID();

		try {
			service.createCourseOffering(Term.Summer, year, courseID);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<CourseOffering> allCO = service.getAllCourseOfferings(); //gets the list of all the courses

		try {
			service.deleteCourseOffering(allCO.get(0).getCourseOfferingID());
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		allCO = service.getAllCourseOfferings();

		assertEquals(0, allCO.size()); //checks the size of the list
	}
	
//	test to update the course
	@Test
	public void testUpdateCourseOffering() {
		assertEquals(0, service.getAllCourseOfferings().size());
		
//		sets up information for the course
		int year = 2019;
		int courseID = service.getAllCourses().get(0).getCourseID();

		try {
			service.createCourseOffering(Term.Summer, year, courseID);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<CourseOffering> allCO = service.getAllCourseOfferings(); //gets the list of all the courses

		assertEquals(1, allCO.size()); //checks the size of the list
		assertEquals(year, allCO.get(0).getYear()); //checks for the Year the course is offered
		assertEquals(Term.Summer, allCO.get(0).getTerm());//check which term is the course being offered
		assertEquals(courseID, allCO.get(0).getCourse().getCourseID());// checks for the courseID
		year = 2020;

		try {
			service.updateCourseOffering(allCO.get(0).getCourseOfferingID(), Term.Fall, year, courseID);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		allCO = service.getAllCourseOfferings();

		assertEquals(1, allCO.size());
		assertEquals(year, allCO.get(0).getYear());
		assertEquals(Term.Fall, allCO.get(0).getTerm());
	}
	
//	test to check if the course offering has a null term
	@Test
	public void testCreateCourseOfferingNullTerm() {
		assertEquals(0, service.getAllCourseOfferings().size());
    
		int year = 2019;
		int courseID = service.getAllCourses().get(0).getCourseID();

		String error = null;

		try {
      // change term to null
			service.createCourseOffering(null, year, courseID);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}

		// check error
		assertEquals(ErrorStrings.Invalid_CourseOffering_Term, error);

		List<CourseOffering> allCO = service.getAllCourseOfferings(); //gets the list of all the courses
		assertEquals(0, allCO.size()); //checks the size of the list
	}

//	test to create a course with an invalid year
	@Test
	public void testCreateCourseOfferingInvalidYear() {
		assertEquals(0, service.getAllCourseOfferings().size());

//		sets up the information 
		int year = 1800;
		int courseID = service.getAllCourses().get(0).getCourseID();

		String error = null;

		try {
			service.createCourseOffering(Term.Summer, year, courseID);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		// check error
		assertEquals(ErrorStrings.Invalid_CourseOffering_Year, error);
		List<CourseOffering> allCO = service.getAllCourseOfferings(); //gets the size of the list
		assertEquals(0, allCO.size()); //checks the list is populating with invalid information 
	}

//	creates a course with a null course 
	@Test
	public void testCreateCourseOfferingNullCourse() {
		assertEquals(0, service.getAllCourseOfferings().size());
//		sets up information for creating a course
		int year = 2019;
		int courseID = 3;

		String error = null;

		try {
			service.createCourseOffering(Term.Winter, year, courseID);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}

		// check error
		assertEquals(ErrorStrings.Invalid_CourseOffering_CantFindCourseOffering, error);

		List<CourseOffering> allCO = service.getAllCourseOfferings(); //gets the list of all the courses
		// check no change in database
		assertEquals(0, allCO.size()); //checks if the list is not being populated
	}

}
