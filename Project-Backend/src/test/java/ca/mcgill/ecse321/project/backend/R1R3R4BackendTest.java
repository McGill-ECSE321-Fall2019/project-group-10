package ca.mcgill.ecse321.project.backend;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;

import ca.mcgill.ecse321.project.ErrorStrings;
import ca.mcgill.ecse321.project.dao.*;
import ca.mcgill.ecse321.project.model.*;
import ca.mcgill.ecse321.project.service.TutoringAppService;

@RunWith(MockitoJUnitRunner.class)
public class R1R3R4BackendTest {

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
	
	// constants for university
	private static final String UNI_NAME = "McGill";
	private static final String UNI_ADDR = "304 Sherbrooke";
	private static final String UNI_NAME_BAD = "Concordia";
	
	// constants for course
	private static final String COURSE_NAME = "ECSE321";
	private static final String COURSE_NAME_BAD = "ECSE443";
	
	// constants for course offering
	private static final Term CO_TERM = Term.Fall;
	private static final int CO_YEAR = 2019;
	private static final int CO_ID = 1;
	private static final int CO_ID_BAD = 2;
	private static final int CO_ID_EMPTY = 3;
	private static final int CO_ID_NONE = 4;
	
	// constants for tutor
	private static final String TUTOR_NAME = "username";
	private static final String TUTOR_NAME_BAD = "bad username";
	
	// constant for room
	private static final int ROOM_NUM = 203;
	
	// constants for session
	private static final Date SESSION_DATE = Date.valueOf("2018-02-01");
	private static final Time SESSION_TIME = Time.valueOf("11:11:11");
	private static final Date SESSION_DATE_DIFF = Date.valueOf("2020-03-02");
	private static final Time SESSION_TIME_DIFF = Time.valueOf("03:02:32");
	
	@Before
	public void setMockOutput() {
		
		// run all default setups for mock outputs

		setMockOutputUniversity();
		setMockOutputCourse();
		setMockOutputCourseOffering();
		setMockOutputTutor();
		setMockOutputRoom();
	}
	
	//********************************************* MOCK OUTPUTS *********************************************//
	
	// mock output for university
	private void setMockOutputUniversity() {
		when(universityRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			List<University> unis = new ArrayList<>();
			University uni = new University();
			uni.setName(UNI_NAME);
			uni.setAddress(UNI_ADDR);
			unis.add(uni);
			return unis;
		});	
	}
	
	// mock output for an empty university list
	private void setMockOutputUniversityEmpty() {
		when(universityRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			List<University> unis = new ArrayList<>();
			return unis;
		});	
	}
	
	// mock output for course
	private void setMockOutputCourse() {
		when(courseRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			// create a university
			University uni = new University();
			uni.setName(UNI_NAME);
			
			// create a course
			List<Course> cs = new ArrayList<>();
			Course c = new Course();
			c.setCourseName(COURSE_NAME);
			c.setUniversity(uni);
			cs.add(c);
			return cs;
		});
	}
	
	// mock output for course
	private void setMockOutputCourseNull() {
		when(courseRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			return null;
		});
	}
	
	// mock output for an empty course list
	private void setMockOutputCourseEmpty() {
		when(courseRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			// create a course list 
			List<Course> cs = new ArrayList<>();
			return cs;
		});
	}
	// mock output for a course without a university
	private void setMockOutputCourseNoUni() {
		when(courseRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			// create a course
			List<Course> cs = new ArrayList<>();
			Course c = new Course();
			c.setCourseName(COURSE_NAME);
			cs.add(c);
			return cs;
		});
	}
	
	// mock output for course offering null
	private void setMockOutputCourseOfferingNull() {
		when(courseOfferingRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			return null;
		});
	}
	// mock output for course offering empty
	private void setMockOutputCourseOfferingEmpty() {
		when(courseOfferingRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			List<CourseOffering> cos = new ArrayList<>();
			return cos;
		});
	}
	
	// mock output for course offering regular
	private void setMockOutputCourseOffering() {
		when(courseOfferingRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			// create a university
			University uni = new University();
			uni.setName(UNI_NAME);
			
			// create a course
			Course c = new Course();
			c.setCourseName(COURSE_NAME);
			c.setUniversity(uni);
			
			//create a course offering
			List<CourseOffering> cos = new ArrayList<>();
			CourseOffering co = new CourseOffering();
			co.setTerm(CO_TERM);
			co.setYear(CO_YEAR);
			co.setCourse(c);
			
			cos.add(co);
			return cos;
		});
		
		when(courseOfferingRepository.findCourseOfferingByCourseOfferingID((anyInt()))).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(CO_ID)) {
				//create a course offering
				CourseOffering co = new CourseOffering();
				co.setCourseOfferingID(CO_ID);
				
				//create a Tutor
				Tutor t = new Tutor();
				t.setUsername(TUTOR_NAME);
				List<Tutor> tutors = new ArrayList<>();
				tutors.add(t);
				
				co.setTutors(tutors);
	
				return co;
			} else if(invocation.getArgument(0).equals(CO_ID_EMPTY)){
				//create a course offering with no tutors
				CourseOffering co = new CourseOffering();
				co.setCourseOfferingID(CO_ID_EMPTY);
				return co;
			} else {
				return null;
			}
		});
	}
	
	// mock output for tutor
	private void setMockOutputTutor() {
		when(tutorRepository.findTutorByUsername((anyString()))).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(TUTOR_NAME)) {
				//create a Tutor with the right name
				Tutor t = new Tutor();
				t.setUsername(TUTOR_NAME);

				return t;
			} else {
				return null;
			}
		});
	}
	
	// mock output for room regular
	private void setMockOutputRoom() {
		when(roomRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			// create a room list
			List<Room> rooms = new ArrayList<>();
			Room r = new Room();
			r.setRoomNumber(ROOM_NUM);
			
			// add a session to the room
			Session s = new Session();
			s.setRoom(r);
			s.setDate(SESSION_DATE);
			s.setTime(SESSION_TIME);
			r.addSession(s);
			
			rooms.add(r);
			return rooms;
		});
	}
	
	// mock output for creating multiple rooms with sessions at the same time
	private void setMockOutputMultipleRoomsSameTime() {
		when(roomRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			// create a room list
			List<Room> rooms = new ArrayList<>();
			Room r = new Room();
			r.setRoomNumber(ROOM_NUM);
			Room r2 = new Room();
			r2.setRoomNumber(ROOM_NUM+1);
			
			// add a session to the rooms
			Session s = new Session();
			s.setRoom(r);
			s.setDate(SESSION_DATE);
			s.setTime(SESSION_TIME);
			r.addSession(s);
			
			Session s2 = new Session();
			s2.setRoom(r2);
			s2.setDate(SESSION_DATE);
			s2.setTime(SESSION_TIME);
			r2.addSession(s2);
			
			rooms.add(r);
			rooms.add(r2);
			return rooms;
		});
	}
	
	// mock output for creating multiple rooms with sessions at different times
	private void setMockOutputMultipleRoomsDiffTime() {
		when(roomRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			// create a room list
			List<Room> rooms = new ArrayList<>();
			Room r = new Room();
			r.setRoomNumber(ROOM_NUM);
			Room r2 = new Room();
			r2.setRoomNumber(ROOM_NUM+1);
			
			// add a session to the rooms
			Session s = new Session();
			s.setRoom(r);
			s.setDate(SESSION_DATE);
			s.setTime(SESSION_TIME);
			r.addSession(s);
			
			Session s2 = new Session();
			s2.setRoom(r2);
			s2.setDate(SESSION_DATE_DIFF);
			s2.setTime(SESSION_TIME_DIFF);
			r2.addSession(s2);
			
			rooms.add(r);
			rooms.add(r2);
			return rooms;
		});
	}
	
	// mock output for room no sessions
	private void setMockOutputRoomNoSessions() {
		when(roomRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			// create a room list
			List<Room> rooms = new ArrayList<>();
			Room r = new Room();
			r.setRoomNumber(ROOM_NUM);
			
			rooms.add(r);
			return rooms;
		});
	}
	
	// mock output for room no rooms
	private void setMockOutputRoomNoRooms() {
		when(roomRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			// create a room list
			List<Room> rooms = new ArrayList<>();
			return rooms;
		});
	}
	
	//************************************************* TESTS *************************************************//

	// check that the service can retrieve all universities properly
	@Test
	public void getAllUniversities() {
		List<University> uniList = new ArrayList<>();
		
		// get all universities
		try {
			uniList = service.getAllUniversities();
		} catch(IllegalArgumentException e) { fail();}
		
		// check that only 1 and its the right one
		assertEquals(1, uniList.size());
		University u = uniList.get(0);
		assertEquals(UNI_NAME, u.getName());
		assertEquals(UNI_ADDR, u.getAddress());
	}
	
	// check for no universities created
	// test coverage: 100%
	@Test
	public void getAllUniversitiesEmpty() {
		List<University> uniList = new ArrayList<>();
		
		// run the correct mock output
		setMockOutputUniversityEmpty();
		
		// get all universities
		try {
			uniList = service.getAllUniversities();
		} catch(IllegalArgumentException e) { fail();}
		
		// check that only 1 and its the right one
		assertEquals(0, uniList.size());
	}
	
	// check that we can get all the courses
	@Test
	public void getAllCourses() {
		List<Course> courseList = new ArrayList<>();
		
		// get all universities
		try {
			courseList = service.getAllCourses();
		} catch(IllegalArgumentException e) { fail();}
		
		// check that only 1 and its the right one
		assertEquals(1, courseList.size());
		Course c = courseList.get(0);
		assertEquals(COURSE_NAME, c.getCourseName());
	}
	
	// check that we can get all the courses
	@Test
	public void getAllCoursesEmpty() {
		List<Course> courseList = new ArrayList<>();
		
		// run the correct mock ouptut
		setMockOutputCourseEmpty();
		
		// get all universities
		try {
			courseList = service.getAllCourses();
		} catch(IllegalArgumentException e) { fail();}
		
		// check that only 1 and its the right one
		assertEquals(0, courseList.size());
	}
	
	// check that we can view all the courses at a school
	@Test
	public void getCoursesByUniversityPositive() {
		List<Course> courses = new ArrayList<>();
		// get all the courses associated with the given university
		try {
			courses = service.getAllCoursesByUniversity(UNI_NAME);
		} catch(IllegalArgumentException e) { fail();}
		
		// check that we have one course and that it is the right one
		assertEquals(1, courses.size());
		assertEquals(COURSE_NAME, courses.get(0).getCourseName());	
	}
	
	// test for getting courses if no university linked to course
	@Test
	public void getCoursesByUniversityNull() {
		String error = null;
		
		//run the correct mock output
		setMockOutputCourseNull();
		
		// get all the courses associated with the given university
		try {
			service.getAllCoursesByUniversity(UNI_NAME);
		} catch(IllegalArgumentException e) { error = e.getMessage();}
		
		// check the error message
		assertEquals(ErrorStrings.Invalid_University_FindCourse, error);
	}
	
	// test for getting courses if no university linked to course
	@Test
	public void getCoursesByUniversityNoLinks() {
		String error = null;
		
		//run the correct mock output
		setMockOutputCourseNoUni();
		
		// get all the courses associated with the given university
		try {
			service.getAllCoursesByUniversity(UNI_NAME);
		} catch(IllegalArgumentException e) { error = e.getMessage();}
		
		// check the error message
		assertEquals(ErrorStrings.Invalid_University_FindCourse , error);
	}
	
	// test for getting courses if there are no courses
	@Test
	public void getCoursesByUniversityEmpty() {
		String error = null;
		
		//run the correct mock output
		setMockOutputCourseEmpty();
		
		// get all the courses associated with the given university
		try {
			service.getAllCoursesByUniversity(UNI_NAME);
		} catch(IllegalArgumentException e) { error = e.getMessage();}
		
		// check that we have one course and that it is the right one
		assertEquals(ErrorStrings.Invalid_University_FindCourse , error);
	}
	
	// Test for getting the courses for a university that doesn't exist
	@Test
	public void getCoursesByUniversityBadUni() {
		List<Course> courses = new ArrayList<>();
		String error = null;
		
		// get all the courses associated with a bad university name
		try {
			courses = service.getAllCoursesByUniversity(UNI_NAME_BAD);
		} catch(IllegalArgumentException e) { error = e.getMessage();}
		
		// check that we have zero courses and that it is the right error message
		assertEquals(0, courses.size());
		assertEquals(ErrorStrings.Invalid_Service_CourseOfferedUni, error);		
	}
	
	// check that we can get all the course offerings
	@Test
	public void getAllCoursesOfferingsPositive() {
		List<CourseOffering> courseOs = new ArrayList<>();
		
		// get all course offerings
		try {
			courseOs = service.getAllCourseOfferings();
		} catch(IllegalArgumentException e) { fail();}
		
		// check that only 1 and its the right one
		assertEquals(1, courseOs.size());
		CourseOffering c = courseOs.get(0);
		assertEquals(CO_TERM, c.getTerm());
		assertEquals(CO_YEAR, c.getYear());
	}
	
	// check that we can get all the course offerings (none)
	@Test
	public void getAllCoursesOfferingsEmpty() {
		List<CourseOffering> courseOs = new ArrayList<>();
		
		// set the correct mock output
		setMockOutputCourseOfferingEmpty();
		
		// get all course offerings
		try {
			courseOs = service.getAllCourseOfferings();
		} catch(IllegalArgumentException e) { fail();}
		
		// check that none received
		assertEquals(0, courseOs.size());
	}
	
	// check that we can get all the course offerings (null)
	@Test
	public void getAllCoursesOfferingsNull() {
		List<CourseOffering> courseOs = new ArrayList<>();
		
		// set the correct mock output
		setMockOutputCourseOfferingNull();
		
		// get all course offerings
		try {
			courseOs = service.getAllCourseOfferings();
		} catch(IllegalArgumentException e) { fail();}
		
		// check that none received
		assertEquals(null, courseOs);
	}
	
	// check that we can view all the courses offerings for a course
	@Test
	public void getCoursesOfferingsPositive() {
		
		List<CourseOffering> courseOs = new ArrayList<>();
		// get all the courses offerings associated with the given course
		try {
			courseOs = service.getAllCourseOfferingsByCourse(COURSE_NAME, UNI_NAME);
		} catch(IllegalArgumentException e) { fail();}
		
		// check that we have one course offering and that it is the right one
		assertEquals(1, courseOs.size());
		assertEquals(CO_TERM, courseOs.get(0).getTerm());
		assertEquals(CO_YEAR, courseOs.get(0).getYear());
	}
	
	// test for university that doesn't exist
	@Test
	public void getCoursesOfferingsBadUni() {
		
		List<CourseOffering> courseOs = new ArrayList<>();
		String error = null;
		
		// get all the courses offerings associated with the given course
		try {
			courseOs = service.getAllCourseOfferingsByCourse(COURSE_NAME, UNI_NAME_BAD);
		} catch(IllegalArgumentException e) { error = e.getMessage();}
		
		// check that we have one course offering and that it is the right one
		assertEquals(0, courseOs.size());
		assertEquals(ErrorStrings.Invalid_Service_TutorForCO, error);
	}
	
	// test for null course offerings
	@Test
	public void getCoursesOfferingsNull() {
		
		List<CourseOffering> courseOs = new ArrayList<>();
		String error = null;
		
		// set the correct mock output
		setMockOutputCourseOfferingNull();
		
		// get all the courses offerings associated with the given course
		try {
			courseOs = service.getAllCourseOfferingsByCourse(COURSE_NAME, UNI_NAME);
		} catch(IllegalArgumentException e) { error = e.getMessage();}
		
		// check that we have one course offering and that it is the right one
		assertEquals(0, courseOs.size());
		assertEquals(ErrorStrings.Invalid_Service_CONone, error);
	}
	
	// test for course that doesn't exist
	@Test
	public void getCoursesOfferingsBadCourse() {
		
		List<CourseOffering> courseOs = new ArrayList<>();
		String error = null;
		
		// get all the courses offerings associated with the given course
		try {
			courseOs = service.getAllCourseOfferingsByCourse(COURSE_NAME_BAD, UNI_NAME);
		} catch(IllegalArgumentException e) { error = e.getMessage();}
		
		// check that we have one course offering and that it is the right one
		assertEquals(0, courseOs.size());
		assertEquals(ErrorStrings.Invalid_Service_TutorForCO, error);
	}
	
	// check that we can view all the tutors for a course offering
	@Test
	public void getTutorsPositive() {
		
		List<Tutor> tutors = new ArrayList<>();
		// get all the tutors associated with the given course offering
		try {
			tutors = service.getAllTutorsByCourseOffering(CO_ID);
		} catch(IllegalArgumentException e) { fail();}
		
		// check that we have one tutor and that it is the right one
		assertEquals(1, tutors.size());
		assertEquals(TUTOR_NAME, tutors.get(0).getUsername());
	}
	
	// test if course offering exists
	@Test
	public void getTutorsBadCO() {
		
		List<Tutor> tutors = new ArrayList<>();
		String error = null;
		// get all the tutors associated with the given course offering
		try {
			tutors = service.getAllTutorsByCourseOffering(CO_ID_BAD);
		} catch(IllegalArgumentException e) { error = e.getMessage();}
		
		// check that we have no tutors and correct error message
		assertEquals(0, tutors.size());
		assertEquals(ErrorStrings.Invalid_Service_COBad, error);
	}
	
	// check for a tutor for a course offering that doesn't exist
	@Test
	public void getTutorsNoCO() {
		
		List<Tutor> tutors = new ArrayList<>();
		String error = null;
		// get all the tutors associated with the given course offering
		try {
			tutors = service.getAllTutorsByCourseOffering(CO_ID_NONE);
		} catch(IllegalArgumentException e) { error = e.getMessage();}
		
		// check that we have no tutors and correct error message
		assertEquals(0, tutors.size());
		assertEquals(ErrorStrings.Invalid_Service_COBad, error);
	}
	
	// test if there are no tutors for the course offering
	@Test
	public void getTutorsEmpty() {
		
		List<Tutor> tutors = new ArrayList<>();
		String error = null;
		// get all the tutors associated with the given course offering
		try {
			tutors = service.getAllTutorsByCourseOffering(CO_ID_EMPTY);
		} catch(IllegalArgumentException e) { error = e.getMessage();}
		
		// check that we have no tutors and correct error message
		assertEquals(0, tutors.size());
		assertEquals(ErrorStrings.Invalid_Service_TutorForCO, error);
	}
	
	// check that we can display the tutor information
	@Test
	public void getTutorInfoPositive() {
		
		Tutor t = new Tutor();
		// get the tutor by username
		try {
			t = service.findTutorByUsername(TUTOR_NAME);
		} catch(IllegalArgumentException e) { fail();}
		
		// check that it is the right tutor received
		assertEquals(TUTOR_NAME, t.getUsername());
	}
	
	// check for a tutor username that doesn't exist
	@Test
	public void getTutorInfoBadUsername() {
		
		Tutor t = new Tutor();
		String error = null;
		
		// get the tutor by username
		try {
			t = service.findTutorByUsername(TUTOR_NAME_BAD);
		} catch(IllegalArgumentException e) { error = e.getMessage();}
		
		// check that no tutor was returned and the correct error message
		assertEquals(true, t.getUsername() == null);
		assertEquals(ErrorStrings.Invalid_Service_Tutor, error);
	}
	
	// check that the service can retrieve all rooms properly
	@Test
	public void getAllRoomsPositive() {
		List<Room> roomList = new ArrayList<>();
		
		// get all rooms
		try {
			roomList = service.getAllRooms();
		} catch(IllegalArgumentException e) { fail();}
		
		// check that only 1 and its the right one
		assertEquals(1, roomList.size());
		Room r = roomList.get(0);
		assertEquals(ROOM_NUM, r.getRoomNumber());
	}
	
	// check that there is a conflict of time and date
	@Test
	public void checkConflictFalse() {
		boolean isAvail = false;
		
		// check for conflict
		try {
			isAvail = service.isRoomAvailable(SESSION_DATE, SESSION_TIME);
		} catch(IllegalArgumentException e) { fail();}
		
		// check that it is not available
		assert(!isAvail);
	}
	
	// check that there is no conflict by changing the date
	@Test
	public void checkConflictTrueDate() {
		boolean isAvail = false;
		
		// check for conflict
		try {
			isAvail = service.isRoomAvailable(SESSION_DATE_DIFF, SESSION_TIME);
		} catch(IllegalArgumentException e) { fail();}
		
		// check that it is available
		assert(isAvail);
	}
	
	// check that there is no conflict by changing the time
	@Test
	public void checkConflictTrueTime() {
		boolean isAvail = false;
		
		// check for conflict
		try {
			isAvail = service.isRoomAvailable(SESSION_DATE, SESSION_TIME_DIFF);
		} catch(IllegalArgumentException e) { fail();}
		
		// check that it is available
		assert(isAvail);
	}
	
	// check that there is no conflict when only 1 of 2 rooms has a conflict
	@Test
	public void checkConflictTrueMultipleRooms() {
		boolean isAvail = false;
		
		// run the correct mock output
		setMockOutputMultipleRoomsDiffTime();
		
		// check for conflict
		try {
			isAvail = service.isRoomAvailable(SESSION_DATE, SESSION_TIME);
		} catch(IllegalArgumentException e) { fail();}
		
		// check that it is available
		assert(isAvail);
	}
	
	// check that there is a conflict when all rooms have a conflict
	@Test
	public void checkConflictFalseMultipleRooms() {
		boolean isAvail = false;
		
		// run the correct mock output
		setMockOutputMultipleRoomsSameTime();
		
		// check for conflict
		try {
			isAvail = service.isRoomAvailable(SESSION_DATE, SESSION_TIME);
		} catch(IllegalArgumentException e) { fail();}
		
		// check that it is not available
		assert(!isAvail);
	}
	
	// check when there are no sessions created
	@Test
	public void checkConflictTrueNoSessions() {
		boolean isAvail = false;
		
		// run the correct mock output
		setMockOutputRoomNoSessions();
		
		// check for conflict
		try {
			isAvail = service.isRoomAvailable(SESSION_DATE, SESSION_TIME);
		} catch(IllegalArgumentException e) { fail();}
		
		// check it is available
		assert(isAvail);
	}
	
	// check when there are no rooms created
	@Test
	public void checkConflictNoRooms() {
		boolean isAvail = false;
		
		// run the correct mock output
		setMockOutputRoomNoRooms();
		
		// check for conflict
		try {
			isAvail = service.isRoomAvailable(SESSION_DATE, SESSION_TIME);
		} catch(IllegalArgumentException e) { fail();}
		
		// check correct error message
		assert(!isAvail);
	}
	
}
