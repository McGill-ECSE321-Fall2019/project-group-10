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
		assertEquals(phoneNum,allUsers.get(0).getPhoneNumber(), 0.05);
	}
	
	@Test
	public void testUpdateUser() {

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
		assertEquals(phoneNum,allUsers.get(0).getPhoneNumber(), 0.05);
	}
	
	@Test
	public void deleteUser() {
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
		
		try {
			service.deleteUser(email);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		allUsers = service.getAllUsers();

		assertEquals(0, allUsers.size());
	}
	
	@Test
	public void testCreateUniversityInvalidAge() {

		String name = "cmc";
		String email = "alpha.gamma@mail.mcgill.ca";
		double phoneNum = 2143945876;
		int age = 11;
		

		String error = null;
		try {
			service.createUser(name, email, age,phoneNum);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}

		assertEquals("Must be above the age of 12 for this tutoring service...", error);
		List<User> allUsers = service.getAllUsers();
		assertEquals(0, allUsers.size());
		
		}
	
	@Test
	public void testCreateUniversityInvalidPhoneNum() {

		String name = "cmc";
		String email = "alpha.gamma@mail.mcgill.ca";
		double phoneNum = 2143-89;
		int age = 18;
		

		String error = null;
		try {
			service.createUser(name, email, age,phoneNum);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}

		assertEquals("Invalid phone number...", error);
		List<User> allUsers = service.getAllUsers();
		assertEquals(0, allUsers.size());
		
		}
	
	@Test
	public void testCreateUniversityNullName() {

		String name = null;
		String email = "alpha.gamma@mail.mcgill.ca";
		double phoneNum = 2143945876;
		int age = 18;
		

		String error = null;
		try {
			service.createUser(name, email, age,phoneNum);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}

		assertEquals("Invalid name...", error);
		List<User> allUsers = service.getAllUsers();
		assertEquals(0, allUsers.size());
		
		}
	
	@Test
	public void testCreateUserNullEmail() {

		String name = "cmc";
		String email = null;
		double phoneNum = 2143945876;
		int age = 18;
		

		String error = null;
		try {
			service.createUser(name, email, age,phoneNum);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}

		assertEquals("Please insert a proper email...", error);
		List<User> allUsers = service.getAllUsers();
		assertEquals(0, allUsers.size());
		
		}
	
	@Test
	public void testCreateUserInvalidEmail() {

		String name = "cmc";
		String email = "alpha";
		double phoneNum = 2143945876;
		int age = 18;
		

		String error = null;
		try {
			service.createUser(name, email, age,phoneNum);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}

		assertEquals("Please insert a proper email...", error);
		List<User> allUsers = service.getAllUsers();
		assertEquals(0, allUsers.size());
		
		}
	
}
