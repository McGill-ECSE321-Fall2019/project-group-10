//package ca.mcgill.ecse321.project.service;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.fail;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.when;
//
//import java.sql.Date;
//import java.sql.Time;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.invocation.InvocationOnMock;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import ca.mcgill.ecse321.project.dao.*;
//import ca.mcgill.ecse321.project.model.*;
//import ca.mcgill.ecse321.project.service.TutoringAppService;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class BackendTestWithPersistence {
//
//	
//	@Autowired
//	private TutoringAppService service;
//	@Autowired
//	private CourseOfferingRepository courseOfferingRepository;  
//	@Autowired
//	private CourseRepository courseRepository;
//	@Autowired
//	private ReviewRepository reviewRepository;  
//	@Autowired
//	private TutorRepository tutorRepository; 
//	@Autowired
//	private StudentRepository studentRepository; 
//	@Autowired
//	private RoomRepository roomRepository; 
//	@Autowired
//	private SessionRepository sessionRepository; 
//	@Autowired
//	private UniversityRepository universityRepository; 
//	@Autowired
//	private UserRepository userRepository;
//
//	
//	// constants for university
//	private static final String UNI_NAME = "McGill";
//	private static final String UNI_ADDR = "304 Sherbrooke";
//	private static final String UNI_NAME_BAD = "Concordia";
//	
//	// constants for course
//	private static final String COURSE_NAME = "ECSE321";
//	private static final String COURSE_INFO = "Intro to soft eng";	
//	private static final String COURSE_NAME_BAD = "ECSE443";
//	
//	// constants for course offering
//	private static final Term CO_TERM = Term.Fall;
//	private static final int CO_YEAR = 2019;
//	private static final int CO_ID = 1;
//	private static final int CO_ID_BAD = 2;
//	private static final int CO_ID_EMPTY = 3;
//	
//	// constants for tutor
//	private static final String TUTOR_NAME = "username";
//	private static final String TUTOR_NAME_BAD = "bad username";
//	
//	// constant for room
//	private static final int ROOM_NUM = 203;
//	
//	// constants for session
//	private static final Date SESSION_DATE = Date.valueOf("2018-02-01");
//	private static final Time SESSION_TIME = Time.valueOf("11:11:11");
//	private static final Date SESSION_DATE_DIFF = Date.valueOf("2020-03-02");
//	private static final Time SESSION_TIME_DIFF = Time.valueOf("03:02:32");
//	
//	
//	@After
//	public void deleteAll() {
//		// clear in order of dependencies
//		sessionRepository.deleteAll();
//		roomRepository.deleteAll();
//		reviewRepository.deleteAll();
//		courseOfferingRepository.deleteAll();
//		courseRepository.deleteAll();
//		universityRepository.deleteAll();
//		tutorRepository.deleteAll();
//		userRepository.deleteAll();
//	}
//
//	//************************************************* TESTS *************************************************//
//
//	// check that the service can retrieve all universities properly
//	@Test
//	public void getAllUniversities() {
//		List<University> uniList = new ArrayList<>();
//		
//		// get all universities
//		try {
//			uniList = service.getAllUniversities();
//		} catch(IllegalArgumentException e) { fail();}
//		
//		// check that only 1 and its the right one
//		assertEquals(1, uniList.size());
//		University u = uniList.get(0);
//		assertEquals(UNI_NAME, u.getName());
//		assertEquals(UNI_ADDR, u.getAddress());
//	}
//	
//	// check that we can view all the courses at a school
//	@Test
//	public void getCoursesByUniversityPositive() {
//		List<Course> courses = new ArrayList<>();
//		// get all the courses associated with the given university
//		try {
//			courses = service.getAllCoursesByUniversity(UNI_NAME);
//		} catch(IllegalArgumentException e) { fail();}
//		
//		// check that we have one course and that it is the right one
//		assertEquals(1, courses.size());
//		assertEquals(COURSE_NAME, courses.get(0).getCourseName());	
//	}
//	
//	// Test for getting the courses for a university that doesn't exist
//	@Test
//	public void getCoursesByUniversityBadUni() {
//		List<Course> courses = new ArrayList<>();
//		String error = null;
//		
//		// get all the courses associated with a bad university name
//		try {
//			courses = service.getAllCoursesByUniversity(UNI_NAME_BAD);
//		} catch(IllegalArgumentException e) { error = e.getMessage();}
//		
//		// check that we have zero courses and that it is the right error message
//		assertEquals(0, courses.size());
//		assertEquals("No courses offered for this university", error);		
//	}
//	
//	// check that we can view all the courses offerings for a course
//	@Test
//	public void getCoursesOfferingsPositive() {
//		
//		List<CourseOffering> courseOs = new ArrayList<>();
//		// get all the courses offerings associated with the given course
//		try {
//			courseOs = service.getAllCourseOfferingsByCourse(COURSE_NAME, UNI_NAME);
//		} catch(IllegalArgumentException e) { fail();}
//		
//		// check that we have one course offering and that it is the right one
//		assertEquals(1, courseOs.size());
//		assertEquals(CO_TERM, courseOs.get(0).getTerm());
//		assertEquals(CO_YEAR, courseOs.get(0).getYear());
//	}
//	
//	// test for university that doesn't exist
//	@Test
//	public void getCoursesOfferingsBadUni() {
//		
//		List<CourseOffering> courseOs = new ArrayList<>();
//		String error = null;
//		
//		// get all the courses offerings associated with the given course
//		try {
//			courseOs = service.getAllCourseOfferingsByCourse(COURSE_NAME, UNI_NAME_BAD);
//		} catch(IllegalArgumentException e) { error = e.getMessage();}
//		
//		// check that we have one course offering and that it is the right one
//		assertEquals(0, courseOs.size());
//		assertEquals("No courses offerings offered for this course", error);
//	}
//	
//	// test for course that doesn't exist
//	@Test
//	public void getCoursesOfferingsBadCourse() {
//		
//		List<CourseOffering> courseOs = new ArrayList<>();
//		String error = null;
//		
//		// get all the courses offerings associated with the given course
//		try {
//			courseOs = service.getAllCourseOfferingsByCourse(COURSE_NAME_BAD, UNI_NAME);
//		} catch(IllegalArgumentException e) { error = e.getMessage();}
//		
//		// check that we have one course offering and that it is the right one
//		assertEquals(0, courseOs.size());
//		assertEquals("No courses offerings offered for this course", error);
//	}
//	
//	// check that we can view all the tutors for a course offering
//	@Test
//	public void getTutorsPositive() {
//		
//		List<Tutor> tutors = new ArrayList<>();
//		// get all the tutors associated with the given course offering
//		try {
//			tutors = service.getAllTutorsByCourseOffering(CO_ID);
//		} catch(IllegalArgumentException e) { fail();}
//		
//		// check that we have one tutor and that it is the right one
//		assertEquals(1, tutors.size());
//		assertEquals(TUTOR_NAME, tutors.get(0).getUsername());
//	}
//	
//	// test if course offering exists
//	@Test
//	public void getTutorsBadCO() {
//		
//		List<Tutor> tutors = new ArrayList<>();
//		String error = null;
//		// get all the tutors associated with the given course offering
//		try {
//			tutors = service.getAllTutorsByCourseOffering(CO_ID_BAD);
//		} catch(IllegalArgumentException e) { error = e.getMessage();}
//		
//		// check that we have no tutors and correct error message
//		assertEquals(0, tutors.size());
//		assertEquals("This course offering does not exist", error);
//	}
//	
//	// test if there are no tutors for the course offering
//	@Test
//	public void getTutorsEmpty() {
//		
//		List<Tutor> tutors = new ArrayList<>();
//		String error = null;
//		// get all the tutors associated with the given course offering
//		try {
//			tutors = service.getAllTutorsByCourseOffering(CO_ID_EMPTY);
//		} catch(IllegalArgumentException e) { error = e.getMessage();}
//		
//		// check that we have no tutors and correct error message
//		assertEquals(0, tutors.size());
//		assertEquals("No tutors for this course offering", error);
//	}
//	
//	// check that we can display the tutor information
//	@Test
//	public void getTutorInfoPositive() {
//		
//		Tutor t = new Tutor();
//		// get the tutor by username
//		try {
//			t = service.findTutorByUsername(TUTOR_NAME);
//		} catch(IllegalArgumentException e) { fail();}
//		
//		// check that it is the right tutor received
//		assertEquals(TUTOR_NAME, t.getUsername());
//	}
//	
//	// check for a tutor username that doesn't exist
//	@Test
//	public void getTutorInfoBadUsername() {
//		
//		Tutor t = new Tutor();
//		String error = null;
//		
//		// get the tutor by username
//		try {
//			t = service.findTutorByUsername(TUTOR_NAME_BAD);
//		} catch(IllegalArgumentException e) { error = e.getMessage();}
//		
//		// check that no tutor was returned and the correct error message
//		assertEquals(true, t.getUsername() == null);
//		assertEquals("No tutor by that username", error);
//	}
//	
//	// check that the service can retrieve all rooms properly
//	@Test
//	public void getAllRoomsPositive() {
//		List<Room> roomList = new ArrayList<>();
//		
//		// get all rooms
//		try {
//			roomList = service.getAllRooms();
//		} catch(IllegalArgumentException e) { fail();}
//		
//		// check that only 1 and its the right one
//		assertEquals(1, roomList.size());
//		Room r = roomList.get(0);
//		assertEquals(ROOM_NUM, r.getRoomNumber());
//	}
//	
//	// check that there is a conflict of time and date
//	@Test
//	public void checkConflictFalse() {
//		boolean isAvail = false;
//		
//		// check for conflict
//		try {
//			isAvail = service.isRoomAvailable(SESSION_DATE, SESSION_TIME);
//		} catch(IllegalArgumentException e) { fail();}
//		
//		// check that it is not available
//		assert(!isAvail);
//	}
//	
//	// check that there is no conflict by changing the date
//	@Test
//	public void checkConflictTrueDate() {
//		boolean isAvail = false;
//		
//		// check for conflict
//		try {
//			isAvail = service.isRoomAvailable(SESSION_DATE_DIFF, SESSION_TIME);
//		} catch(IllegalArgumentException e) { fail();}
//		
//		// check that it is available
//		assert(isAvail);
//	}
//	
//	// check that there is no conflict by changing the time
//	@Test
//	public void checkConflictTrueTime() {
//		boolean isAvail = false;
//		
//		// check for conflict
//		try {
//			isAvail = service.isRoomAvailable(SESSION_DATE, SESSION_TIME_DIFF);
//		} catch(IllegalArgumentException e) { fail();}
//		
//		// check that it is available
//		assert(isAvail);
//	}
//	
//	// check that there is no conflict when only 1 of 2 rooms has a conflict
//	@Test
//	public void checkConflictTrueMultipleRooms() {
//		boolean isAvail = false;
//		
//		// run the correct mock output
//		//setMockOutputMultipleRoomsDiffTime();
//		
//		// check for conflict
//		try {
//			isAvail = service.isRoomAvailable(SESSION_DATE, SESSION_TIME);
//		} catch(IllegalArgumentException e) { fail();}
//		
//		// check that it is available
//		assert(isAvail);
//	}
//	
//	// check that there is a conflict when all rooms have a conflict
//	@Test
//	public void checkConflictFalseMultipleRooms() {
//		boolean isAvail = false;
//		
//		// run the correct mock output
//		//setMockOutputMultipleRoomsSameTime();
//		
//		// check for conflict
//		try {
//			isAvail = service.isRoomAvailable(SESSION_DATE, SESSION_TIME);
//		} catch(IllegalArgumentException e) { fail();}
//		
//		// check that it is not available
//		assert(!isAvail);
//	}
//	
//	// check when there are no sessions created
//	@Test
//	public void checkConflictTrueNoSessions() {
//		boolean isAvail = false;
//		
//		// run the correct mock output
//		//setMockOutputRoomNoSessions();
//		
//		// check for conflict
//		try {
//			isAvail = service.isRoomAvailable(SESSION_DATE, SESSION_TIME);
//		} catch(IllegalArgumentException e) { fail();}
//		
//		// check it is available
//		assert(isAvail);
//	}
//	
//	// check when there are no rooms created
//	@Test
//	public void checkConflictNoRooms() {
//		boolean isAvail = false;
//		
//		// run the correct mock output
//		//setMockOutputRoomNoRooms();
//		
//		// check for conflict
//		try {
//			isAvail = service.isRoomAvailable(SESSION_DATE, SESSION_TIME);
//		} catch(IllegalArgumentException e) { fail();}
//		
//		// check correct error message
//		assert(!isAvail);
//	}
//	
//}
