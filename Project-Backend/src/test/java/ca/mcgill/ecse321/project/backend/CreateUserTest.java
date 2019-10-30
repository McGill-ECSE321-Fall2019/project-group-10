package ca.mcgill.ecse321.project.backend;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;

import ca.mcgill.ecse321.project.ErrorStrings;
import ca.mcgill.ecse321.project.dao.AvailabilityRepository;
import ca.mcgill.ecse321.project.dao.CourseOfferingRepository;
import ca.mcgill.ecse321.project.dao.CourseRepository;
import ca.mcgill.ecse321.project.dao.ReviewRepository;
import ca.mcgill.ecse321.project.dao.RoomRepository;
import ca.mcgill.ecse321.project.dao.SessionRepository;
import ca.mcgill.ecse321.project.dao.StudentRepository;
import ca.mcgill.ecse321.project.dao.TutorRepository;
import ca.mcgill.ecse321.project.dao.UniversityRepository;
import ca.mcgill.ecse321.project.dao.UserRepository;
import ca.mcgill.ecse321.project.model.Role;
import ca.mcgill.ecse321.project.model.Student;
import ca.mcgill.ecse321.project.model.TSUser;
import ca.mcgill.ecse321.project.service.TutoringAppService;

@RunWith(MockitoJUnitRunner.class)
public class CreateUserTest {

	@Mock 
	private AvailabilityRepository availabilityRepository;
	@Mock
	private CourseOfferingRepository courseOfferingRepository;  
	@Mock
	private CourseRepository courseRepository;
	@Mock
	private ReviewRepository reviewRepository;  
	@Mock
	private TutorRepository tutorRepository; 
	@Mock
	private StudentRepository studentRepository; 
	@Mock
	private RoomRepository roomRepository; 
	@Mock
	private SessionRepository sessionRepository; 
	@Mock
	private UniversityRepository universityRepository; 
	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private TutoringAppService service;


	// constants for TSUser
	private static final String USER_EMAIL = "user.tester@mcgill.ca";
	private static final String USER_EMAIL_BAD = "user.tester";
	private static final String USER_EMAIL_UNAVAILABLE = "unavUser@mcgill.ca";
	private static final String USER_NAME = "username";
	private static final int USER_AGE = 18;
	private static final int BAD_USER_AGE = 11;
	private static final String USER_PHONE_NUMBER = "5142221000";
	private static final String USER_PHONE_NUMBER_2 = "514-222-1000";
	private static final String BAD_USER_PHONE_NUMBER = "+15142221000";

	//	Constants for Students
	private static final String STUDENT_USERNAME = "teddy";
	private static final String STUDENT_PASSWORD = "123";
	private static final String STUDENT_EMAIL = "user.tester@mcgill.ca";
	private static final String BAD_STUDENT_EMAIL = "student.tester";

	private static final int STUDENT_AGE = 18;
	private static final String STUDENT_PHONENUMBER = "5146754321";

	@Before
	public void setMockOutput() {

		// run all setups for mock outputs

		setMockOutputUser();
		setMockOutputStudent();
	}

	//********************************************* MOCK OUTPUTS *********************************************//

	// mock output for User
	private void setMockOutputUser() {
		when(userRepository.findTSuserByEmail((anyString()))).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(USER_EMAIL)) {
				//create a Tutor with the right name
				TSUser t = new TSUser();
				t.setName(USER_NAME);
				t.setAge(USER_AGE);
				t.setEmail(USER_EMAIL);
				t.setPhoneNumber(USER_PHONE_NUMBER);
				return t;
			} 

			else if(invocation.getArgument(0).equals(USER_EMAIL_BAD)){
				return null;
			} 
			else if(invocation.getArgument(0).equals(USER_EMAIL_UNAVAILABLE)) {
				TSUser t = new TSUser();
				t.setName(USER_EMAIL_UNAVAILABLE);
				t.setAge(USER_AGE);
				t.setEmail(USER_EMAIL);
				t.setPhoneNumber(USER_PHONE_NUMBER);

				return t;
			} 
			else {
				return null;
			}
		});
	}


	
	
	private void setMockOutputStudent() {
		when(studentRepository.findStudentByUsername((anyString()))).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(STUDENT_USERNAME)) {
				//create a Tutor with the right name
				
				Student s = new Student();
				s.setUsername(STUDENT_USERNAME);
				s.setPassword(STUDENT_PASSWORD);
				s.getUser().setAge(STUDENT_AGE);
				s.getUser().setEmail(STUDENT_EMAIL);
				s.getUser().setPhoneNumber(STUDENT_PHONENUMBER);
				return s;
			} 
			else {
				return null;
			}
		});
	}


	//************************************************* TESTS *************************************************//

	//	Creates a user with valid parameters
	@Test
	public void testCreateValidUser() {

		TSUser s = null;

		try {
			s = service.createUser(USER_NAME, USER_EMAIL, USER_AGE,USER_PHONE_NUMBER);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		assertEquals(USER_NAME,s.getName());
		assertEquals(USER_EMAIL,s.getEmail());
		assertEquals(USER_AGE,s.getAge());
		assertEquals(USER_PHONE_NUMBER,s.getPhoneNumber());		
	}

	//	tests to create a new user with a different type of user phone number
	@Test
	public void testCreateValidUser2() {

		TSUser s = null;

		try {
			s = service.createUser(USER_NAME, USER_EMAIL, USER_AGE,USER_PHONE_NUMBER_2);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		assertEquals(USER_NAME,s.getName());
		assertEquals(USER_EMAIL,s.getEmail());
		assertEquals(USER_AGE,s.getAge());
		assertEquals(USER_PHONE_NUMBER_2,s.getPhoneNumber());		
	}


	//Test Null User Name
	@Test
	public void testCreateUserNullName (){

		String error = null;
		try {
			service.createUser(null, USER_EMAIL, USER_AGE,USER_PHONE_NUMBER);
		} 
		catch (IllegalArgumentException e) {

			error = e.getMessage();
		}
		//check it was the correct error

		assertEquals(error, ErrorStrings.Invalid_User_Name);

	}
	//Creates a user who is underage (Age <12)
	@Test
	public void testCreateUserInvalidAge() {

		String error = null;
		try {
			service.createUser(USER_NAME, USER_EMAIL, BAD_USER_AGE,USER_PHONE_NUMBER);
		} catch (IllegalArgumentException e) {

			error = e.getMessage();
		}
		//check it was the correct error
		assertEquals(error, ErrorStrings.Invalid_User_AgeTooYoung);

	}

	//tests if the phone number is null
	@Test
	public void testCreateUserNullPhoneNumber() {

		String error = null;
		try {
			service.createUser(USER_NAME, USER_EMAIL, USER_AGE,null);
		}
		catch (IllegalArgumentException e) {

			error = e.getMessage();
		}
		//check it was the correct error
		assertEquals(error, ErrorStrings.Invalid_User_PhoneNumber);
	}

	//tests if the phone number is invalid
	@Test
	public void testCreateUserInvalidPhoneNumber() {

		String error = null;
		try {
			service.createUser(USER_NAME, USER_EMAIL, USER_AGE,BAD_USER_PHONE_NUMBER);
		}
		catch (IllegalArgumentException e) {

			error = e.getMessage();
		}
		//check it was the correct error
		assertEquals(error, ErrorStrings.Invalid_User_PhoneNumber);
	}




	//	----------------------------------------------------- Student tests ------------------------------------------------
//	tests to see if the student is a valid student
	@Test
	public void testCreateValidStudent() {
		Student s = null;
		try {
			s = service.createStudent(STUDENT_USERNAME,STUDENT_PASSWORD,STUDENT_EMAIL);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		assertEquals(STUDENT_USERNAME,s.getUsername());
		assertEquals(STUDENT_PASSWORD,s.getPassword());
		assertEquals(USER_EMAIL,s.getUser().getEmail());

	}
	@Test
	public void testCreateStudentNullName() {
		String error = null;
		try {
			service.createStudent(null, STUDENT_PASSWORD, STUDENT_EMAIL);
		}

		catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		//		checks if the error is correct
		assertEquals(error, ErrorStrings.Invalid_Student_Username);
	}


	@Test
	public void testCreateStudentInvalidName() {
		String error = null;
		try {
			service.createStudent("", STUDENT_PASSWORD, STUDENT_EMAIL);
		}

		catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		//		checks if the error is correct
		assertEquals(error, ErrorStrings.Invalid_Student_Username);
	}

	@Test
	public void testCreateStudentNullPassword() {
		String error = null;
		try {
			service.createStudent(STUDENT_USERNAME, null, STUDENT_EMAIL);
		}

		catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		//		checks if the error is correct
		assertEquals(error, ErrorStrings.Invalid_Student_Password);
	}

	@Test
	public void testCreateStudentEmptyPassword() {
		String error = null;
		try {
			service.createStudent(STUDENT_USERNAME, "", STUDENT_EMAIL);
		}

		catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		//		checks if the error is correct
		assertEquals(error, ErrorStrings.Invalid_Student_Password);
	}

	@Test
	public void testCreateStudentNullEmail() {
		String error = null;
		try {
			service.createStudent(STUDENT_USERNAME, STUDENT_PASSWORD, null);
		}

		catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		//		checks if the error is correct
		assertEquals(error, ErrorStrings.Invalid_Student_UserEmail);
	}

	@Test
	public void testCreateStudentInvalidEmail() {
		String error = null;
		try {
			service.createStudent(STUDENT_USERNAME, STUDENT_PASSWORD, BAD_STUDENT_EMAIL);
		}

		catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		//		checks if the error is correct
		assertEquals(error, ErrorStrings.Invalid_Student_UserEmail);
	}

	



}


