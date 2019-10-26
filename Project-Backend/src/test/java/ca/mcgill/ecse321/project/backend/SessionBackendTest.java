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
public class SessionBackendTest {

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
	// constants for course offering
	private static final Term CO_TERM = Term.Fall;
	private static final int CO_YEAR = 2019;
	private static final int CO_ID = 1;
	
	private static final int CO_ID_EMPTY = 3;
	
	// constants for tutor
	private static final String TUTOR_NAME = "username";
	private static final String TUTOR_NAME_BAD = "bad username";
	private static final double SESSION_AMOUNT_PAID = 15.00;
	private static final String TUTOR_NAME_UNAVAILABLE = "unavUsername";
	
	// constant for room
	private static final int ROOM_NUM = 203;
	
	// constants for session
	private static final Date SESSION_DATE = Date.valueOf("2019-10-31");
	private static final Time SESSION_TIME = Time.valueOf("11:11:11");
	private static final Date SESSION_DATE_DIFF = Date.valueOf("2020-03-02");
	private static final Time SESSION_TIME_DIFF = Time.valueOf("03:02:32");
	
	//Constants for Student
	private static final String STUDENT_NAME = "studentUser";
	private static final String STUDENT_NAME_BAD = "badStudentUsername"; 
	private static final String STUDENT_NAME_DIFF = "DiffUsername";
	
	// constants for availability
	private static final Date AVAILABILITY_DATE = Date.valueOf("2019-10-31");
	private static final Time AVAILABILITY_TIME = Time.valueOf("11:11:11");
	private static final Date AVAILABILITY_DATE_2 = Date.valueOf("2021-02-01");
	private static final Time AVAILABILITY_TIME_2 = Time.valueOf("10:10:00");
	
	
	
	@Before
	public void setMockOutput() {
		
		// run all setups for mock outputs
		setMockOutputCourseOffering();
		setMockOutputTutor();
		setMockOutputRoom();
		setMockOutputStudent();
	}
	
	//********************************************* MOCK OUTPUTS *********************************************//
	
	
	// mock output for course offering
	private void setMockOutputCourseOffering() {
		
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
				Set<Session> sessions = new HashSet<Session>();
				sessions.add(sessionRepository.findSessionBySessionID(0));
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
	
	//mock the output for a student
	private void setMockOutputStudent() {
		when(studentRepository.findStudentByUsername((anyString()))).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(STUDENT_NAME)) {
				Student s = new Student();
				s.setUsername(STUDENT_NAME);
				Set<Session> sessions = new HashSet<Session> ();
				s.setSession(sessions);
				return s;
			} else if (invocation.getArgument(0).equals(STUDENT_NAME_DIFF)) {
				
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
		
		when(sessionRepository.findSessionBySessionID((anyInt()))).thenAnswer((InvocationOnMock invocation) -> {
			
			if(invocation.getArgument(0).equals(0)) {
				
				Session s = new Session();
				s.setAmountPaid(SESSION_AMOUNT_PAID);
				s.setDate(SESSION_DATE);
				s.setTime(SESSION_TIME);
				
				Tutor t = new Tutor();
				t.setUsername(TUTOR_NAME_UNAVAILABLE);
				Set<Availability> avSet = new HashSet<Availability>();
				t.setAvailability(avSet);
				Availability a = new Availability();
				a.setDate(SESSION_DATE_DIFF);
				a.setTime(SESSION_TIME_DIFF);
				a.setTutor(t);
				t.getAvailability().add(a);
				
				Student student = new Student();
				student.setUsername(STUDENT_NAME);
				
				Set<Session> sessions = new HashSet<Session>();
				sessions.add(s);
				student.setSession(sessions);
				
				List<Student> students = (new ArrayList<Student>());
				students.add(student);
				s.setStudent(students);
				s.setAmountPaid(12.00);
				s.setTutor(t);
				CourseOffering co = new CourseOffering();
				
				t.setSession(sessions);
				
				List<Session> sessions2 = new ArrayList<Session>();
				
				sessions2.add(s);
				
				co.setSession(sessions2);
				
				
				s.setCourseOffering(co);
				
				return s;
				
			} else if (invocation.getArgument(0).equals(1)) {
				
				Session s = new Session();
				s.setAmountPaid(SESSION_AMOUNT_PAID);
				s.setDate(Date.valueOf("2019-10-27"));
				s.setTime(Time.valueOf("21:00:00"));
				
				Tutor t = new Tutor();
				t.setUsername(TUTOR_NAME_UNAVAILABLE);
				Set<Availability> avSet = new HashSet<Availability>();
				t.setAvailability(avSet);
				Availability a = new Availability();
				a.setDate(SESSION_DATE_DIFF);
				a.setTime(SESSION_TIME_DIFF);
				a.setTutor(t);
				t.getAvailability().add(a);
				
				Student student = new Student();
				student.setUsername(STUDENT_NAME);
				
				Set<Session> sessions = new HashSet<Session>();
				sessions.add(s);
				student.setSession(sessions);
				
				List<Student> students = (new ArrayList<Student>());
				students.add(student);
				s.setStudent(students);
				s.setAmountPaid(12.00);
				s.setTutor(t);
				CourseOffering co = new CourseOffering();
				
				t.setSession(sessions);
				
				List<Session> sessions2 = new ArrayList<Session>();
				
				sessions2.add(s);
				
				co.setSession(sessions2);
				
				
				s.setCourseOffering(co);
				
				return s;
				
				
			} else if (invocation.getArgument(0).equals(2)) {
				
				
				Session s = new Session();
				s.setAmountPaid(SESSION_AMOUNT_PAID);
				s.setDate(Date.valueOf("2019-10-27"));
				s.setTime(Time.valueOf("12:00:00"));
				
				Tutor t = new Tutor();
				t.setUsername(TUTOR_NAME_UNAVAILABLE);
				Set<Availability> avSet = new HashSet<Availability>();
				t.setAvailability(avSet);
				Availability a = new Availability();
				a.setDate(SESSION_DATE_DIFF);
				a.setTime(SESSION_TIME_DIFF);
				a.setTutor(t);
				t.getAvailability().add(a);
				
				Student student = new Student();
				student.setUsername(STUDENT_NAME);
				
				Set<Session> sessions = new HashSet<Session>();
				sessions.add(s);
				student.setSession(sessions);
				
				List<Student> students = (new ArrayList<Student>());
				students.add(student);
				s.setStudent(students);
				s.setAmountPaid(12.00);
				s.setTutor(t);
				CourseOffering co = new CourseOffering();
				
				t.setSession(sessions);
				
				List<Session> sessions2 = new ArrayList<Session>();
				
				sessions2.add(s);
				
				co.setSession(sessions2);
				
				
				s.setCourseOffering(co);
				
				return s;	
				
			} else if (invocation.getArgument(0).equals(3)) {
				
				Session s = new Session();
				s.setAmountPaid(SESSION_AMOUNT_PAID);
				s.setDate(Date.valueOf("2019-10-26"));
				s.setTime(Time.valueOf("07:00:00"));
				
				Tutor t = new Tutor();
				t.setUsername(TUTOR_NAME_UNAVAILABLE);
				Set<Availability> avSet = new HashSet<Availability>();
				t.setAvailability(avSet);
				Availability a = new Availability();
				a.setDate(SESSION_DATE_DIFF);
				a.setTime(SESSION_TIME_DIFF);
				a.setTutor(t);
				t.getAvailability().add(a);
				
				Student student = new Student();
				student.setUsername(STUDENT_NAME);
				
				Set<Session> sessions = new HashSet<Session>();
				sessions.add(s);
				student.setSession(sessions);
				
				List<Student> students = (new ArrayList<Student>());
				students.add(student);
				s.setStudent(students);
				s.setAmountPaid(12.00);
				s.setTutor(t);
				CourseOffering co = new CourseOffering();
				
				t.setSession(sessions);
				
				List<Session> sessions2 = new ArrayList<Session>();
				
				sessions2.add(s);
				
				co.setSession(sessions2);
				
				
				s.setCourseOffering(co);
				
				return s;
				
			} else if (invocation.getArgument(0).equals(4)) {
				
				return null;
				
			} else if (invocation.getArgument(0).equals(5)) {
				
				Session s = new Session();
				s.setAmountPaid(SESSION_AMOUNT_PAID);
				s.setDate(SESSION_DATE);
				s.setTime(SESSION_TIME);
				
				Tutor t = new Tutor();
				t.setUsername(TUTOR_NAME_UNAVAILABLE);
				Set<Availability> avSet = new HashSet<Availability>();
				t.setAvailability(avSet);
				Availability a = new Availability();
				a.setDate(SESSION_DATE_DIFF);
				a.setTime(SESSION_TIME_DIFF);
				a.setTutor(t);
				t.getAvailability().add(a);
				
				Student student = new Student();
				student.setUsername(STUDENT_NAME);
				
				Set<Session> sessions = new HashSet<Session>();
				
				student.setSession(sessions);
				
				List<Student> students = (new ArrayList<Student>());
				
				s.setStudent(students);
				s.setAmountPaid(12.00);
				s.setTutor(t);
				CourseOffering co = new CourseOffering();
				
				t.setSession(sessions);
				
				List<Session> sessions2 = new ArrayList<Session>();
				
				sessions2.add(s);
				
				co.setSession(sessions2);
				
				
				s.setCourseOffering(co);
				
				return s;
				
			}
			
			
			
			return null;
			
		});
		
	}
	
	private void createMockAvailability() {
		
		/*when(service.createAvailability((testDate),(testTime), (anyString()))).thenAnswer((InvocationOnMock invocation) -> {
			
			
			Availability availability = new Availability();
			availability.setTime(testTime);
			availability.setDate(testDate);
			Tutor t = tutorRepository.findTutorByUsername(invocation.getArgument(0));
			availability.setTutor(t);
			t.getAvailability().add(availability);
			
			return availability;
			
		});*/
		
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
		
		setMockOutputSession();
		
		List<Session> sessions = null;
		
		try {
			sessions = service.getAllSessions();
		} catch (Exception e){
			
			fail();
			
		}
		
		if(sessions == null) {
			
			fail();
			
		}
		
		int id = sessions.get(0).getSessionID();
		
		Session s = null;
		
		try {
			s = service.getSession(id);
		} catch (Exception e) {
			fail();
		}
		
		assertEquals(s.getSessionID(), sessions.get(0).getSessionID());
		
	}
	
	@Test
	public void testValidDeleteSession() {
		
		setMockOutputSession();
		createMockAvailability();
		boolean result = false;
		//s = sessionRepository.findSessionBySessionID(0);
		
		
		try {
			result = service.deleteSession(0);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		
		assertEquals(result, true);
		
	}
	
	@Test
	public void testDeleteSessionValidWithinOneDay() {
		
		setMockOutputSession();
		createMockAvailability();
		
		boolean result = false;
		
		try {
			result = service.deleteSession(1);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		
		assertEquals(result, true);
		
	}
	@Test
	public void testDeleteToLateOnAnotherDateSession() {
		
		setMockOutputSession();
		createMockAvailability();
		
		String error = null;
		
		try {
			service.deleteSession(2);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		
		assertEquals( error, "It is too late to cancel a session!" );
		
	}
	
	@Test
	public void testDeleteOnSameDateSession() {
		
		setMockOutputSession();
		createMockAvailability();
		
		String error = null;
		
		try {
			service.deleteSession(3);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		
		assertEquals( error, "It is too late to cancel a session! Please do it at least the day before!");
		
		
	}
	
	@Test
	public void testDeleteSessionNullError() {
		
		setMockOutputSession();
		createMockAvailability();
		
		String error = null;
		
		try {
			service.deleteSession(4);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		
		assertEquals( error, "Invalid Session ID");
		
		
	}
	
	@Test
	public void testAddAStudentToASessionNullStudentName() {
		String error = null;
		
		try {
			service.getSessionByStudent(null);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		
		assertEquals( error, ErrorStrings.Invalid_Session_StudentName);
		
	}
	
	@Test
	public void testAddStudentToASessionNullStudent() {
		
		String error = null;
		
		try {
			service.getSessionByStudent(STUDENT_NAME_BAD);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		
		assertEquals( error, ErrorStrings.Invalid_Session_FindStudentByUsername);
		
	}
	
	@Test
	public void testGetSessionByStudent() {
		
		List<Session> sessions = null;
		
		try {
			sessions = service.getSessionByStudent(STUDENT_NAME);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		
		assertEquals( 0, sessions.size());
		
	}
	
	@Test
	public void createSessionInFutureFar() {
		
		String error = null;
		try {
			service.createSession(CO_ID, Date.valueOf("2019-11-10"), SESSION_TIME,SESSION_AMOUNT_PAID, STUDENT_NAME, TUTOR_NAME);
		} catch (IllegalArgumentException e) {
			
			error = e.getMessage();
		}
		//check it was the correct error
		assertEquals(error, "Can not book a session more than 14 days in advance");
		
	}
	
	@Test
	public void testAddStudentAlreadyAdded() {
		
		setMockOutputSession();
		
		String error = null;
		
		try {
			service.addStudentToSession(0, STUDENT_NAME);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		
		assertEquals(error, "Student is already added to this session.");
		
	}
	
	@Test
	public void testAddStudentNullStudent() {
		
		setMockOutputSession();
		
		String error = null;
		
		try {
			service.addStudentToSession(0, STUDENT_NAME_BAD);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		
		assertEquals(error, "Student is null!");
		
	}
	
	@Test
	public void testAddStudentNullSession() {
		
		setMockOutputSession();
		
		String error = null;
		
		try {
			service.addStudentToSession(100, STUDENT_NAME);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		
		assertEquals(error, "Session is null!");
		
	}
	
	@Test
	public void testValidAddStudent() {
		
		setMockOutputSession();
		
		Student s = null;
		
		try {
			s = service.addStudentToSession(5, STUDENT_NAME);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		
		assertEquals(s.getSession().size(), 1);
		
	}
}
