package ca.mcgill.ecse321.project.service;

import org.junit.After;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ca.mcgill.ecse321.project.model.*;
import ca.mcgill.ecse321.project.dao.*;
import ca.mcgill.ecse321.project.service.TutoringAppService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CourseTest {
	
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
		service.createUniversity("McGill", "3040 University");
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
	public void testCreateCourse() {

		String description = "new";
		String courseName = "COM SCI 101";

		try {
			service.createCourse(description, courseName, service.getAllUniversities().get(0).getUniversityID());
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<Course> allCourses = service.getAllCourses();
		// check that all the attributes are correct
		assertEquals(1, allCourses.size());
		assertEquals(description, allCourses.get(0).getDescription());
		assertEquals(courseName, allCourses.get(0).getCourseName());
		assertEquals("McGill", allCourses.get(0).getUniversity().getName());
	}
	
	@Test
	public void testUpdateCourse() {

		String description = "new";
		String courseName = "COM SCI 101";

		try {
			service.createCourse(description, courseName, service.getAllUniversities().get(0).getUniversityID());
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<Course> allCourses = service.getAllCourses();

		assertEquals(1, allCourses.size());
		assertEquals(description, allCourses.get(0).getDescription());
		assertEquals(courseName, allCourses.get(0).getCourseName());
		assertEquals("McGill", allCourses.get(0).getUniversity().getName());
		
		description = "updated";
		courseName = "COM SCI 102";
		int id = allCourses.get(0).getCourseID();
		
		try {
			service.updateCourse(id, description, courseName, service.getAllUniversities().get(0).getUniversityID());
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		allCourses = service.getAllCourses();

		assertEquals(1, allCourses.size());
		assertEquals(description, allCourses.get(0).getDescription());
		assertEquals(courseName, allCourses.get(0).getCourseName());
		assertEquals("McGill", allCourses.get(0).getUniversity().getName());
	}
	
	@Test
	public void testDeleteCourse() {
		assertEquals(0, service.getAllCourses().size());

		String description = "new";
		String courseName = "COM SCI 101";

		try {
			service.createCourse(description, courseName, service.getAllUniversities().get(0).getUniversityID());
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<Course> allCourses = service.getAllCourses();
		
		try {
			service.deleteCourse(allCourses.get(0).getCourseID());
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		allCourses = service.getAllCourses();

		assertEquals(0, allCourses.size());
	}
	
	@Test
	public void testCreateCourseNullDescription() {

		String description = null;
		String courseName = "COM SCI 101";
		
		String error = null;

		try {
			service.createCourse(description, courseName, service.getAllUniversities().get(0).getUniversityID());
		} catch (IllegalArgumentException e) {
			// Check that error occurred
			error = e.getMessage();
		}
		// check that the correct error was generated
		assertEquals("Please insert a brief description...", error);
		List<Course> allCourses = service.getAllCourses();
		assertEquals(0, allCourses.size());

	}
	
	@Test
	public void testCreateCourseNullCourseName() {

		String description = "new";
		String courseName = null;

		String error = null;
		
		try {
			service.createCourse(description, courseName, service.getAllUniversities().get(0).getUniversityID());
		} catch (IllegalArgumentException e) {
			// Check that error occurred
			error = e.getMessage();
		}
		// check that the correct error was generated
		assertEquals("Please insert a course name to search...", error);
		List<Course> allCourses = service.getAllCourses();
		assertEquals(0, allCourses.size());

	}

	@Test
	public void testCreateCourseNoUniversity() {

		String description = "new";
		String courseName = "COM SCI 101";
		
		String error = null;

		try {
			service.createCourse(description, courseName, 2);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		// check that the correct error was generated
		assertEquals("Please specify a valid University", error);
		List<Course> allCourses = service.getAllCourses();
		assertEquals(0, allCourses.size());

	}
}
