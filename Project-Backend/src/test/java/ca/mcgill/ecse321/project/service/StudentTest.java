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
	public void testCreateStudent() {

		String username = "cmc";
		String password = "dogs";

		try {
			service.createStudent(username, password, "test.tester@mcgill.ca");
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<Student> allStudents = service.getAllStudents();

		assertEquals(1, allStudents.size());
		assertEquals(username, allStudents.get(0).getUsername());
		assertEquals(password, allStudents.get(0).getPassword());
		assertEquals("email", allStudents.get(0).getUser().getEmail());
	}
	
	@Test
	public void testUpdateStudent() {

		String username = "cmc";
		String password = "dogs";

		try {
			service.createStudent(username, password, "test.tester@mcgill.ca");
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<Student> allStudents = service.getAllStudents();

		assertEquals(1, allStudents.size());
		
		username = "amc";
		password = "cats";
		
		try {
			service.updateStudent("cmc", username, password, "test.tester@mcgill.ca");
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		
		assertEquals(username, allStudents.get(0).getUsername());
		assertEquals(password, allStudents.get(0).getPassword());
	}
	
	@Test
	public void testDeleteStudent() {

		String username = "cmc";
		String password = "dogs";

		try {
			service.createStudent(username, password, "test.tester@mcgill.ca");
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		
		try {
			service.deleteStudent(username);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		// check that the correct error was generated
		List<Student> allStudents = service.getAllStudents();

		assertEquals(0, allStudents.size());
	}
	
	@Test
	public void testCreateStudentNullUsername() {

		String username = null;
		String password = "dogs";
		
		String error = null;

		try {
			service.createStudent(username, password, "test.tester@mcgill.ca");
		} catch (IllegalArgumentException e) {
			// Check that error occurred
			error = e.getMessage();
		}
		// check that the correct error was generated
		assertEquals("Please insert a username...", error);
		List<Student> allStudents = service.getAllStudents();

		assertEquals(0, allStudents.size());
	}
	
	@Test
	public void testCreateStudentNullPassword() {

		String username = "cmc";
		String password = null;
		
		String error = null;

		try {
			service.createStudent(username, password, "test.tester@mcgill.ca");
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		// check that the correct error was generated
		assertEquals("Please insert a password...", error);
		List<Student> allStudents = service.getAllStudents();

		assertEquals(0, allStudents.size());
	}
	
	@Test
	public void testCreateStudentNullUser() {

		String username = "cmc";
		String password = "dogs";
		
		String error = null;

		try {
			service.createStudent(username, password, "tester@mcgill.ca");
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
	public void testCreateStudentBadEmail() {

		String username = "cmc";
		String password = null;
		
		String error = null;

		try {
			service.createStudent(username, password, "test");
		} catch (IllegalArgumentException e) {
			// Check that error occurred
			error = e.getMessage();
		}
		// check that the correct error was generated
		assertEquals("Please insert a proper email...", error);
		List<Student> allStudents = service.getAllStudents();

		assertEquals(0, allStudents.size());
	}
	
}