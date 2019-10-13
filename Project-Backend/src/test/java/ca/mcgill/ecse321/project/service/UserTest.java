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
import ca.mcgill.ecse321.project.service.*;

@RunWith(SpringRunner.class)
@SpringBootTest

public class UserTest {
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
	
	@SuppressWarnings("deprecation")
	@Test
	public void testCreateUser() {

		String name = "cmc";
		String email = "alpha.gamma@mail.mcgill.ca";
		double phoneNum = 2143945876;
		int age = 18;

		try {
			service.createUser(name, email, age,phoneNum);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<User> allUsers = service.getAllUsers();

		assertEquals(1, allUsers.size());
		assertEquals(name, allUsers.get(0).getName());
		assertEquals(email, allUsers.get(0).getEmail());
		assertEquals(age, allUsers.get(0).getAge());
		assertEquals(phoneNum,allUsers.get(0).getPhoneNumber());
	}
	
	@Test
	public void testUpdateStudent() {

		String name = "cmc";
		String email = "alpha.gamma@mail.mcgill.ca";
		double phoneNum = 2143945876;
		int age = 18;

		try {
			service.createUser(name, email, age,phoneNum);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<User> allUsers = service.getAllUsers();

		assertEquals(1, allUsers.size());
		
		name = "amc";
		String newEmail = "cats.dogs@mail.mcgill.ca";
		phoneNum = 2143945000;
		age = 20;
		
		try {
			service.updateUser(name, email, newEmail, age,phoneNum);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		
		assertEquals(1, allUsers.size());
		assertEquals(name, allUsers.get(0).getName());
		assertEquals(newEmail, allUsers.get(0).getEmail());
		assertEquals(age, allUsers.get(0).getAge());
		assertEquals(phoneNum,allUsers.get(0).getPhoneNumber());
	}
	
}
