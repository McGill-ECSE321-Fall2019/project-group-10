package ca.mcgill.ecse321.project.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.mockito.internal.matchers.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ca.mcgill.ecse321.project.service.TutoringAppService;
import ca.mcgill.ecse321.project.model.*;
import ca.mcgill.ecse321.project.dao.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentTest {
	
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
	//Test if creating and retrieving a student works
	public void testCreateStudent() {

		try {
			service.createStudent(USERNAME, PASSWORD, EMAIL);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<Student> allStudents = service.getAllStudents();

		assertEquals(1, allStudents.size());
		assertEquals(USERNAME, allStudents.get(0).getUsername());
		assertEquals(PASSWORD, allStudents.get(0).getPassword());
		assertEquals(EMAIL, allStudents.get(0).getUser().getEmail());
	}
	
	@Test
	//Test if updating student works
	public void testUpdateStudent() {

		try {
			service.createStudent(USERNAME, PASSWORD, EMAIL);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<Student> allStudents = service.getAllStudents();

		assertEquals(1, allStudents.size());
		
		String username = "amc";
		String password = "cats";
		
		try {
			service.updateStudent(USERNAME, username, password, EMAIL);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		
		allStudents = service.getAllStudents();

		assertEquals(username, allStudents.get(0).getUsername());
		assertEquals(password, allStudents.get(0).getPassword());
	}
	
	@Test
	//Test if deleting student works
	public void testDeleteStudent() {

		try {
			service.createStudent(USERNAME, PASSWORD, EMAIL);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		
		try {
			service.deleteStudent(USERNAME);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		// check that the correct error was generated
		List<Student> allStudents = service.getAllStudents();

		assertEquals(0, allStudents.size());
	}
	
	@Test
	//Test if creating a student w/out a username fails correctly
	public void testCreateStudentNullUsername() {

		String error = "";

		try {
			service.createStudent(null, PASSWORD, EMAIL);
		} catch (IllegalArgumentException e) {
			// Check that error occurred
			error = e.getMessage();
		}
		List<Student> allStudents = service.getAllStudents();

		// check that the correct error was generated
		assertEquals("Please insert a username...", error);
		assertEquals(0, allStudents.size());
	}
	
	@Test
	//Test if creating a student w/out a password fails correctly
	public void testCreateStudentNullPassword() {

		String error = "";

		try {
			service.createStudent(USERNAME, null, EMAIL);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}

		List<Student> allStudents = service.getAllStudents();

		// check that the correct error was generated
		assertEquals("Please insert a password...", error);
		assertEquals(0, allStudents.size());
	}
	
	@Test
	//Test if creating a student that doesn't have an email linked to user fails
	public void testCreateStudentNullUser() {

		String error = null;

		try {
			service.createStudent(USERNAME, PASSWORD, "wrongEmail@gmail.com");
		} catch (IllegalArgumentException e) {
			// Check that error occurred
			error = e.getMessage();
		}
		// check that the correct error was generated
		assertEquals("Please input a valid user", error);
		List<Student> allStudents = service.getAllStudents();

		assertEquals(0, allStudents.size());
	}
	@Test
	//Test if creating a student with an email of the wrong format fails
	public void testCreateStudentWrongEmail() {

		String error = null;

		try {
			service.createStudent(USERNAME, null, "test");
		} catch (IllegalArgumentException e) {
			// Check that error occurred
			error = e.getMessage();
		}

		List<Student> allStudents = service.getAllStudents();

		// check that the correct error was generated
		assertEquals("Please insert a proper email...", error);
		assertEquals(0, allStudents.size());
	}
	
}