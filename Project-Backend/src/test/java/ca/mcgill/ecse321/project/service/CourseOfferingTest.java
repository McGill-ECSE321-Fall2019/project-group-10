package ca.mcgill.ecse321.project.service;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ca.mcgill.ecse321.project.service.TutoringAppService;
import ca.mcgill.ecse321.project.model.*;
import ca.mcgill.ecse321.project.dao.*;
import ca.mcgill.ecse321.project.service.*;

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
	
	@Before
	public void setUp(){
		service.createUniversity("McGill", "3040 University", 1);
		service.createCourse("Intro to Software","ECSE 321", 2, 1);
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
	public void testCreateCourseOffering() {
		assertEquals(0, service.getAllCourseOfferings().size());
		
		int id = 3;
		String term = "fall";
		int year = 2019;
		int courseID = 2;

		try {
			service.createCourseOffering(id, term, year, courseID);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<CourseOffering> allCO = service.getAllCourseOfferings();

		assertEquals(1, allCO.size());
		assertEquals(id, allCO.get(0).getCourseOfferingID());
		assertEquals(year, allCO.get(0).getYear());
		assertEquals(term, allCO.get(0).getTerm());
		assertEquals(courseID, allCO.get(0).getCourse().getCourseID());
	}
	
	@Test
	public void testDeleteCourseOffering() {
		assertEquals(0, service.getAllCourseOfferings().size());
		
		int id = 3;
		String term = "fall";
		int year = 2019;
		int courseID = 2;

		try {
			service.createCourseOffering(id, term, year, courseID);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		
		try {
			service.deleteCourseOffering(id);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<CourseOffering> allCO = service.getAllCourseOfferings();

		assertEquals(0, allCO.size());
	}
	
	@Test
	public void testCreateCourseOffering() {
		assertEquals(0, service.getAllCourseOfferings().size());
		
		int id = 3;
		String term = "fall";
		int year = 2019;
		int courseID = 2;

		try {
			service.createCourseOffering(id, term, year, courseID);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<CourseOffering> allCO = service.getAllCourseOfferings();

		assertEquals(1, allCO.size());
		assertEquals(id, allCO.get(0).getCourseOfferingID());
		assertEquals(year, allCO.get(0).getYear());
		assertEquals(term, allCO.get(0).getTerm());
		assertEquals(courseID, allCO.get(0).getCourse().getCourseID());
		
		id = 4;
		term = "winter";
		year = 2020;

		try {
			service.updateCourseOffering(3, id, term, year, courseID);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		allCO = service.getAllCourseOfferings();

		assertEquals(1, allCO.size());
		assertEquals(id, allCO.get(0).getCourseOfferingID());
		assertEquals(year, allCO.get(0).getYear());
		assertEquals(term, allCO.get(0).getTerm());
	}
	
	@Test
	public void testCreateCourseOfferingNullID() {
		assertEquals(0, service.getAllCourseOfferings().size());
		
		int id = null;
		String term = "fall";
		int year = 2019;
		int courseID = 2;

		try {
			service.createCourseOffering(id, term, year, courseID);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<CourseOffering> allCO = service.getAllCourseOfferings();

		assertEquals(0, allCO.size());
	}
	
	@Test
	public void testCreateCourseOfferingNullTerm() {
		assertEquals(0, service.getAllCourseOfferings().size());
		
		int id = 3;
		String term = null;
		int year = 2019;
		int courseID = 2;

		try {
			service.createCourseOffering(id, term, year, courseID);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<CourseOffering> allCO = service.getAllCourseOfferings();

		assertEquals(0, allCO.size());
	}
	
	@Test
	public void testCreateCourseOfferingNullYear() {
		assertEquals(0, service.getAllCourseOfferings().size());
		
		int id = 3;
		String term = "fall";
		int year = null;
		int courseID = 2;

		try {
			service.createCourseOffering(id, term, year, courseID);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<CourseOffering> allCO = service.getAllCourseOfferings();

		assertEquals(0, allCO.size());
	}
	
	@Test
	public void testCreateCourseOfferingNullCourse() {
		assertEquals(0, service.getAllCourseOfferings().size());
		
		int id = 3;
		String term = "fall";
		int year = 2019;
		int courseID = 3;

		try {
			service.createCourseOffering(id, term, year, courseID);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<CourseOffering> allCO = service.getAllCourseOfferings();

		assertEquals(0, allCO.size());
	}
}
