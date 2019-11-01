package ca.mcgill.ecse321.project.service;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ca.mcgill.ecse321.project.model.*;
import ca.mcgill.ecse321.project.ErrorStrings;
import ca.mcgill.ecse321.project.dao.*;
import ca.mcgill.ecse321.project.service.TutoringAppService;

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
	@Autowired
	private TutorRepository tutorRepository; 
	@Autowired
	private StudentRepository studentRepository; 
	
	private String email = "test.test@test.com";
	private String name = "aName";
	private String phone = "5145555555";
	private int age = 22;
	
//	@Before
//	public void setUp(){
//		service.createUser(name, email, age,phone );
//	}
	
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
	
	//finds the user by user name
	@Test
	public void testFindUserByEmail() {
		List<TSUser> u = new ArrayList<TSUser>();
		TSUser user = new TSUser();
		user = service.createUser(name, email, age, phone);
		u.add(user);
		try {
			
			service.findUserByEmail(email);
		}
		catch (IllegalArgumentException e){
			fail();
		}
	}
	
	// test to create a new user
	@Test
	public void testCreateUser() {
//		Set up information for the creating a user
		String name = "cmc";
		String email = "alpha.gamma@mail.mcgill.ca";
		String phoneNum = "2143945876";
		int age = 18;

		try {
			service.createUser(name, email, age,phoneNum); //creates a user using the service class
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		// gets the list of all the users and stores it in a new list called allUsers
		List<TSUser> allUsers = service.getAllUsers();

		// check that all the attributes are correct
		assertEquals(1, allUsers.size()); //checks if the user is added
		assertEquals(name, allUsers.get(0).getName()); //checks if the name of the user matches with the name set
		assertEquals(email, allUsers.get(0).getEmail()); //checks if the email of the user matches with the email set
		assertEquals(age, allUsers.get(0).getAge()); //checks if the age of the user matches with the age set
		assertEquals(phoneNum,allUsers.get(0).getPhoneNumber()); //checks if the phoneNumber of the user matches with the phoneNum set
	}
	
	// update a user attributes
	@Test
	public void testUpdateUser() {
		// Sets up the information for creating a user
		String name = "cmc";
		String email = "alpha.gamma@mail.mcgill.ca";
		String phoneNum = "2143945876";
		int age = 18;

		// create a user so we can update it
		try {
			service.createUser(name, email, age,phoneNum);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		// gets the list of all the users and stores it in a new list called allUsers
		List<TSUser> allUsers = service.getAllUsers();

		assertEquals(1, allUsers.size()); //checks if the user is added to the list
		
//		Sets up the information for updating the user
		name = "amc";
		String newEmail = "cats.dogs@mail.mcgill.ca";
		phoneNum = "2143945000";
		age = 20;
		
		try {
			service.updateUser(name, email, newEmail, age,phoneNum);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		
		allUsers = service.getAllUsers();
		
		// check that the attributes have been updated
		assertEquals(1, allUsers.size()); //checks if the user still exist in the list
		assertEquals(name, allUsers.get(0).getName()); //check if the name of the user is updated
		assertEquals(newEmail, allUsers.get(0).getEmail()); //check if the email of the user is updated
		assertEquals(age, allUsers.get(0).getAge()); // checks if the age of the user is updated
		assertEquals(phoneNum,allUsers.get(0).getPhoneNumber()); // checks if the phone number of the user is updated
	}
	
	// check to delete a user
	@Test
	public void deleteUser() {
//		Set up information for creating a user
		
		String name = "cmc";
		String email = "alpha.gamma@mail.mcgill.ca";
		String phoneNum = "2143945876";
		int age = 18;

		try {
			service.createUser(name, email, age,phoneNum);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		// check that the user was created
		List<TSUser> allUsers = service.getAllUsers();

		assertEquals(1, allUsers.size()); //checks if the user is being added to the list
		
		try {
			service.deleteUser(email);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		allUsers = service.getAllUsers();

		// check that there is no user
		assertEquals(0, allUsers.size()); //checks if the user is being deleted from the list
	}
	
	// test to check if the user age is a valid integer
	@Test
	public void testCreateUserInvalidAge() {
//		Set up for creating a user
		String name = "cmc";
		String email = "alpha.gamma@mail.mcgill.ca";
		String phoneNum = "2143945876";
		int age = 11;
		

		String error = null;
		try {
			service.createUser(name, email, age,phoneNum);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}

		// check that the correct error was generated
		assertEquals(ErrorStrings.Invalid_User_AgeTooYoung, error); //checks if the error message matches with the message set
		List<TSUser> allUsers = service.getAllUsers(); //gets the list of all the users
		assertEquals(0, allUsers.size()); // makes sure an underage user is been created
		
		}
	
//	checks for the validity for phone number
	@Test
	public void testCreateUserInvalidPhoneNum() {
//		set up for creating a user
		String name = "cmc";
		String email = "alpha.gamma@mail.mcgill.ca";
		String phoneNum = "2143-89";
		int age = 18;
		

		String error = null;
		try {
			service.createUser(name, email, age,phoneNum);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		// check that the correct error was generated

		assertEquals(ErrorStrings.Invalid_User_PhoneNumber, error); //checks the error message
		List<TSUser> allUsers = service.getAllUsers(); //gets the list of all the user
		assertEquals(0, allUsers.size()); //checks the size of the list
		
		}
	
	
//	test to check if the user has an empty or null name
	@Test
	public void testCreateUserNullName() {
//		set up information for creating a user
		String name = null;
		String email = "alpha.gamma@mail.mcgill.ca";
		String phoneNum = "2143945876";
		int age = 18;
		

		String error = null;
		try {
			service.createUser(name, email, age,phoneNum);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		// check that the correct error was generated

		assertEquals(ErrorStrings.Invalid_User_Name, error); 
		List<TSUser> allUsers = service.getAllUsers(); //gets the list of all the users
		assertEquals(0, allUsers.size()); // checks the size of the userList
		
		}
	
	
//	creates a user with a null email address
	@Test
	public void testCreateUserNullEmail() {
//		Sets up information
		String name = "cmc";
		String email = null;
		String phoneNum = "2143945876";
		int age = 18;
		

		String error = null;
		try {
			service.createUser(name, email, age,phoneNum);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		// check that the correct error was generated

		assertEquals(ErrorStrings.Invalid_User_Email, error);
		List<TSUser> allUsers = service.getAllUsers(); //gets the list
		assertEquals(0, allUsers.size()); //checks the size of the list
		
		}
//	creates a user with an invalid email
	@Test
	public void testCreateUserInvalidEmail() {
// 		sets up information for creating a user
		String name = "cmc";
		String email = "alpha";
		String phoneNum = "2143945876";
		int age = 18;
		

		String error = null;
		try {
			service.createUser(name, email, age,phoneNum);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		// check that the correct error was generated

		assertEquals(ErrorStrings.Invalid_User_Email, error);
		List<TSUser> allUsers = service.getAllUsers(); //gets the list of the users
		assertEquals(0, allUsers.size()); //checks the size of the list
		
		}
	
}
