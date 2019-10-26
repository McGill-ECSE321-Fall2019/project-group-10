package ca.mcgill.ecse321.project.backend;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.*;
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
public class CreateSessionTest {

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
	
	// constants for course
	private static final String COURSE_NAME = "ECSE321";
	private static final String COURSE_INFO = "Intro to soft eng";	
	private static final String COURSE_NAME_BAD = "ECSE443";
	
	// constants for course offering
	private static final Term CO_TERM = Term.Fall;
	private static final int CO_YEAR = 2019;
	private static final int CO_ID = 1;
	private static final int CO_ID_BAD = 2;
	private static final int CO_ID_EMPTY = 3;
	
	// constants for tutor
	private static final String TUTOR_NAME = "username";
	private static final String TUTOR_NAME_BAD = "bad username";
	private static final double SESSION_AMOUNT_PAID = 15.00;
	private static final String TUTOR_NAME_UNAVAILABLE = "unavUsername";
	
	// constant for room
	private static final int ROOM_NUM = 203;
	
	// constants for session
	private static final Date SESSION_DATE = Date.valueOf("2020-02-01");
	private static final Time SESSION_TIME = Time.valueOf("11:11:11");
	private static final Date SESSION_DATE_DIFF = Date.valueOf("2020-03-02");
	private static final Time SESSION_TIME_DIFF = Time.valueOf("03:02:32");
	
	//Constants for Student
	private static final String STUDENT_NAME = "studentUser";
	private static final String STUDENT_NAME_BAD = "badStudentUsername"; 
	private static final String STUDENT_EMAIL = "student.tester@mcgill.ca";
	
	// constants for availability
	private static final Date AVAILABILITY_DATE = Date.valueOf("2020-02-01");
	private static final Time AVAILABILITY_TIME = Time.valueOf("11:11:11");
	private static final Date AVAILABILITY_DATE_2 = Date.valueOf("2021-02-01");
	private static final Time AVAILABILITY_TIME_2 = Time.valueOf("10:10:00");
	
	@Before
	public void setMockOutput() {
		
		// run all setups for mock outputs

		setMockOutputUniversity();
		setMockOutputCourse();
		setMockOutputCourseOffering();
		setMockOutputTutor();
		setMockOutputRoom();
		setMockOutputStudent();
		setMockOutputAvailability();
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
	
	// mock output for course offering
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
			co.setSession(new ArrayList<Session>());
			
			cos.add(co);
			return cos;
		});
		
		when(courseOfferingRepository.findCourseOfferingByCourseOfferingID((anyInt()))).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(CO_ID)) {
				//create a course offering
				CourseOffering co = new CourseOffering();
				co.setCourseOfferingID(CO_ID);
				//co.setCourse(c);
				
				//create a Tutor
				Tutor t = new Tutor();
				t.setUsername(TUTOR_NAME);
				List<Tutor> tutors = new ArrayList<>();
				tutors.add(t);
				co.setSession(new ArrayList<Session>());
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
				Set<Availability> avSet = new HashSet<Availability>();
				t.setAvailability(avSet);
				Availability a = new Availability();
				a.setDate(AVAILABILITY_DATE);
				a.setTime(AVAILABILITY_TIME);
				a.setTutor(t);
				t.getAvailability().add(a);
				return t;
			} else if(invocation.getArgument(0).equals(TUTOR_NAME_BAD)){
				return null;
			} else if(invocation.getArgument(0).equals(TUTOR_NAME_UNAVAILABLE)) {
				Tutor t = new Tutor();
				t.setUsername(TUTOR_NAME_UNAVAILABLE);
				Set<Availability> avSet = new HashSet<Availability>();
				t.setAvailability(avSet);
				Availability a = new Availability();
				a.setDate(AVAILABILITY_DATE_2);
				a.setTime(AVAILABILITY_TIME_2);
				a.setTutor(t);
				t.getAvailability().add(a);
				return t;
			} else {
				return null;
			}
		});
	}
	
	// mock output for availability
	private void setMockOutputAvailability() {
		
		/*when(service.getAvailabilityByTutor((anyString()))).thenAnswer((InvocationOnMock invocation) -> {
			
			if(invocation.getArgument(0).equals(TUTOR_NAME)) {
				
				Tutor t = new Tutor();
				t.setUsername(TUTOR_NAME);
				
				Availability a = new Availability();
				a.setDate(AVAILABILITY_DATE);
				a.setTime(AVAILABILITY_TIME);
				a.setTutor(t);
				
				List<Availability> avList = new ArrayList<Availability>();
				avList.add(a);
				
				return avList;
				
			} else {
				
				return null;
			}
			
		});*/
		
	}
	
	// mock output for room regular
	private void setMockOutputRoom() {
		when(roomRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			// create a room list
			List<Room> rooms = new ArrayList<>();
			Room r = new Room();
			r.setRoomNumber(ROOM_NUM);
			
			
			rooms.add(r);
			return rooms;
		});
	}
	
	// mock output for creating multiple rooms with sessions at the same time
	private void setMockOutputNoAvailableRooms() {
		when(roomRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			// create a room list
			List<Room> rooms = new ArrayList<>();
			Room r = new Room();
			r.setRoomNumber(ROOM_NUM);
			
			// add a session to the rooms
			Session s = new Session();
			s.setRoom(r);
			s.setDate(SESSION_DATE);
			s.setTime(SESSION_TIME);
			
			rooms.add(r);
			
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
			
			Session s2 = new Session();
			s2.setRoom(r2);
			s2.setDate(SESSION_DATE_DIFF);
			s2.setTime(SESSION_TIME_DIFF);
			
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
	
	// mock output for room null
	private void setMockOutputRoomNull() {
		when(roomRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			return null;
		});
	}
	
	//mock the output for a student
	private void setMockOutputStudent() {
		when(studentRepository.findStudentByUsername((anyString()))).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(STUDENT_NAME)) {
				Student s = new Student();
				s.setUsername(STUDENT_NAME);
				Set<Session> sessions = new HashSet<Session> ();
				s.setSession(sessions);
				return s;
			}
			else {
				
				return null;
				
			}
		
		});
		
	}
	
	private void setMockOutputSession() {
		when(sessionRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			Session s = new Session();
			Session s1 = new Session();
			s.setAmountPaid(SESSION_AMOUNT_PAID);
			s.setDate(SESSION_DATE);
			s.setTime(SESSION_TIME);
			s.setStudent(new ArrayList<Student>());
			s1.setAmountPaid(SESSION_AMOUNT_PAID);
			s1.setDate(SESSION_DATE_DIFF);
			s1.setTime(SESSION_TIME_DIFF);
			s1.setStudent(new ArrayList<Student>());
			
			List<Session> sessions = new ArrayList<Session> ();
			
			sessions.add(s1);
			sessions.add(s);
			
			return sessions;
			
		});
		
	}
	//************************************************* TESTS *************************************************//
	
	@Test
	public void testCreateValidSession() {
		
		Session s = null;
		
		try {
			s = service.createSession(CO_ID, SESSION_DATE, SESSION_TIME,SESSION_AMOUNT_PAID, STUDENT_NAME, TUTOR_NAME);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		
		
		
		
		assertEquals(SESSION_TIME.toString(), s.getTime().toString());
		assertEquals((int)SESSION_AMOUNT_PAID, (int)s.getAmountPaid());
		assertEquals(SESSION_DATE.toString(), s.getDate().toString());
		assertEquals(s.getCourseOffering().getCourseOfferingID(), CO_ID);
		assertEquals(STUDENT_NAME, s.getStudent().get(0).getUsername());
		assertEquals(TUTOR_NAME, s.getTutor().getUsername());
		
	}
	
	//Test Null User Name
	@Test
	public void testCreateSessionNullTutorName (){
		
		String error = null;
		try {
			service.createSession(CO_ID, SESSION_DATE, SESSION_TIME,SESSION_AMOUNT_PAID, STUDENT_NAME, null);
		} catch (IllegalArgumentException e) {
			
			error = e.getMessage();
		}
		//check it was the correct error
		assertEquals(error, ErrorStrings.Invalid_Session_TutorName);
		
	}
	
	@Test
	public void testCreateSessionNullStudentName() {
		
		String error = null;
		try {
			service.createSession(CO_ID, SESSION_DATE, SESSION_TIME,SESSION_AMOUNT_PAID, null, TUTOR_NAME);
		} catch (IllegalArgumentException e) {
			
			error = e.getMessage();
		}
		//check it was the correct error
		assertEquals(error, ErrorStrings.Invalid_Session_StudentName);
		
	}
	
	@Test
	public void testCreateSessionNullDate() {
		
		String error = null;
		try {
			service.createSession(CO_ID, null, SESSION_TIME,SESSION_AMOUNT_PAID, STUDENT_NAME, TUTOR_NAME);
		} catch (IllegalArgumentException e) {
			
			error = e.getMessage();
		}
		//check it was the correct error
		assertEquals(error, ErrorStrings.Invalid_Session_DateTime);
		
	}
	
	@Test
	public void testCreateSessionNegAmountPaid() {
		
		String error = null;
		try {
			service.createSession(CO_ID, SESSION_DATE, SESSION_TIME, -0.05, STUDENT_NAME, TUTOR_NAME);
		} catch (IllegalArgumentException e) {
			
			error = e.getMessage();
		}
		//check it was the correct error
		assertEquals(error, ErrorStrings.Invalid_Session_NegativeAmountPaid);
		
	}
	
	@Test
	public void testCreateSessionUnavialableRoom() {
		setMockOutputRoomNoRooms();
		
		String error = null;
		
		try {
			service.createSession(CO_ID, SESSION_DATE, SESSION_TIME, SESSION_AMOUNT_PAID, STUDENT_NAME, TUTOR_NAME);
		} catch (IllegalArgumentException e) {
			
			error = e.getMessage();
		}
		//check it was the correct error
		assertEquals(error, "There is no room available at this time");
		
		
	}
	
	@Test
	public void testCreateSessionTooEarlyTime() {
		
		String error = null;
		
		try {
			service.createSession(CO_ID, SESSION_DATE, Time.valueOf("08:00:00"), SESSION_AMOUNT_PAID, STUDENT_NAME, TUTOR_NAME);
		} catch (IllegalArgumentException e) {
			
			error = e.getMessage();
		}
		//check it was the correct error
		assertEquals(error, "This is not a valid time");
		
	}
	
	@Test
	public void testCreateSessionTooLateTime() {
		
		String error = null;
		
		try {
			service.createSession(CO_ID, SESSION_DATE, Time.valueOf("20:10:00"), SESSION_AMOUNT_PAID, STUDENT_NAME, TUTOR_NAME);
		} catch (IllegalArgumentException e) {
			
			error = e.getMessage();
		}
		//check it was the correct error
		assertEquals(error, "This is not a valid time");
		
	}
	
	@Test
	public void testCreateSessionSameDayOrInThePast(){
		
		String error = null;
		
		try {
			service.createSession(CO_ID, Date.valueOf("2019-10-24"), SESSION_TIME, SESSION_AMOUNT_PAID, STUDENT_NAME, TUTOR_NAME);
		} catch (IllegalArgumentException e) {
			
			error = e.getMessage();
		}
		//check it was the correct error
		assertEquals(error, "Can not book a session on the same day, or in the past!");
		
	}
	
	@Test
	public void testCreateSessionNullTutor() {
		
		String error = null;
		
		try {
			service.createSession(CO_ID, SESSION_DATE, SESSION_TIME, SESSION_AMOUNT_PAID, STUDENT_NAME, TUTOR_NAME_BAD);
		} catch (IllegalArgumentException e) {
			
			error = e.getMessage();
		}
		//check it was the correct error
		assertEquals(error, ErrorStrings.Invalid_Session_FindTutorByUsername);
		
	}
	
	@Test
	public void testCreateSessionUnavailableTutor() {
		
		String error = null;
		
		try {
			service.createSession(CO_ID, SESSION_DATE, SESSION_TIME, SESSION_AMOUNT_PAID, STUDENT_NAME, TUTOR_NAME_UNAVAILABLE);
		} catch (IllegalArgumentException e) {
			
			error = e.getMessage();
		}
		//check it was the correct error
		assertEquals(error, "The Tutor is busy during this time.");
		
	}
	
	@Test
	public void testCreateSessionNullCourseOffering() {
		
		String error = null;
		
		try {
			service.createSession(90, SESSION_DATE, SESSION_TIME, SESSION_AMOUNT_PAID, STUDENT_NAME, TUTOR_NAME);
		} catch (IllegalArgumentException e) {
			
			error = e.getMessage();
		}
		//check it was the correct error
		assertEquals(error, ErrorStrings.Invalid_Session_FindCourseOfferingByID);
		
	}
	
	@Test
	public void testCreateSessionNullStudent() {
		
		String error = null;
		
		try {
			service.createSession(CO_ID, SESSION_DATE, SESSION_TIME, SESSION_AMOUNT_PAID, STUDENT_NAME_BAD, TUTOR_NAME);
		} catch (IllegalArgumentException e) {
			
			error = e.getMessage();
		}
		//check it was the correct error
		assertEquals(error,ErrorStrings.Invalid_Session_FindStudentByUsername);
		
	}
	
	@Test
	public void testFindAllSessions() {
		
		setMockOutputSession();
		
		List<Session> sessions = null;
		
		try {
			
			sessions = service.getAllSessions();
			
		} catch (Exception e) {
			
			fail();
			
		}
		
		assertEquals(sessions.size(), 2);
		assertEquals(sessions.get(0).getDate(), SESSION_DATE_DIFF );
		assertEquals(sessions.get(1).getDate(), SESSION_DATE);
		
	}
	
	@Test
	public void testGetSessionById() {
		
	}
}
