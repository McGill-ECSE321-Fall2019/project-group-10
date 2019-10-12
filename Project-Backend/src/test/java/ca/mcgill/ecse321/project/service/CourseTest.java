package ca.mcgill.ecse321.project.service;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ca.mcgill.ecse321.project.model.*;
import ca.mcgill.ecse321.project.dao.*;
import ca.mcgill.ecse321.project.service.*;

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
		service.createUniversity("McGill", "3040 University", 1);
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
		int id = 1000;

		try {
			service.createCourse(description, courseName, id, 1);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<Course> allCourses = courseRepository.getAllCourses();

		assertEquals(1, allCourses.size());
		assertEquals(id, allCourses.get(0).getCourseID());
		assertEquals(description, allCourses.get(0).getDescription());
		assertEquals(courseName, allCourses.get(0).getCourseName());
		assertEquals("McGill", allCourses.get(0).getUniversity().getName());
	}
	
	@Test
	public void testUpdateCourse() {

		String description = "new";
		String courseName = "COM SCI 101";
		int id = 1000;

		try {
			service.createCourse(description, courseName, id, 1);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<Course> allCourses = courseRepository.getAllCourses();

		assertEquals(1, allCourses.size());
		assertEquals(id, allCourses.get(0).getCourseID());
		assertEquals(description, allCourses.get(0).getDescription());
		assertEquals(courseName, allCourses.get(0).getCourseName());
		assertEquals("McGill", allCourses.get(0).getUniversity().getName());
		
		description = "updated";
		courseName = "COM SCI 102";
		id = 1001;
		
		try {
			service.updateCourse(1000, description, courseName, id, 1);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<Course> allCourses = courseRepository.getAllCourses();

		assertEquals(1, allCourses.size());
		assertEquals(id, allCourses.get(0).getCourseID());
		assertEquals(description, allCourses.get(0).getDescription());
		assertEquals(courseName, allCourses.get(0).getCourseName());
		assertEquals("McGill", allCourses.get(0).getUniversity().getName());
	}
	
	@Test
	public void testDeleteCourse() {
		assertEquals(0, service.getAllCourses().size());

		String description = "new";
		String courseName = "COM SCI 101";
		int id = 1000;

		try {
			service.createCourse(description, courseName, id, 1);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		try {
			service.deleteCourse(id);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<Course> allCourses = courseRepository.getAllCourses();

		assertEquals(0, allCourses.size());
	}
	
	@Test
	public void testCreateCourseNullDescription() {

		String description = null;
		String courseName = "COM SCI 101";
		int id = 1000;

		try {
			service.createCourse(description, courseName, id, 1);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<Course> allCourses = courseRepository.getAllCourses();

		assertEquals(0, allCourses.size());

	}
	
	@Test
	public void testCreateCourseNullCourseName() {

		String description = "new";
		String courseName = null;
		int id = 1000;

		try {
			service.createCourse(description, courseName, id, 1);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<Course> allCourses = courseRepository.getAllCourses();

		assertEquals(0, allCourses.size());

	}
	
	@Test
	public void testCreateCourseNullID() {

		String description = "new";
		String courseName = "COM SCI 101";
		int id = null;

		try {
			service.createCourse(description, courseName, id, 1);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<Course> allCourses = courseRepository.getAllCourses();

		assertEquals(0, allCourses.size());

	}

	@Test
	public void testCreateCourseNoUniversity() {

		String description = "new";
		String courseName = "COM SCI 101";
		int id = 1000;

		try {
			service.createCourse(description, courseName, id, 2);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<Course> allCourses = courseRepository.getAllCourses();

		assertEquals(0, allCourses.size());

	}
}
