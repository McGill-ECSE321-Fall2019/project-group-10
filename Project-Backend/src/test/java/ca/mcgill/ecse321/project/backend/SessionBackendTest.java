package ca.mcgill.ecse321.project.backend;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
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
	
	// constants for course offering
	private static final int CO_ID = 1;
	
	private static final int CO_ID_EMPTY = 3;
	
	// constants for tutor
	private static final String TUTOR_NAME = "username";
	private static final String TUTOR_NAME_BAD = "bad username";
	private static final double SESSION_AMOUNT_PAID = 15.00;
	private static final String TUTOR_NAME_UNAVAILABLE = "unavUsername";
	private static final String TUTOR_NAME_NO_AVAILABILITIES = "noAvUsername";
	
	// constant for room
	private static final int ROOM_NUM = 203;
	
	// constants for session
	private static final Date SESSION_DATE = Date.valueOf(LocalDate.now().plusDays(3)) ;//Date.valueOf("2019-10-31");
	private static final Time SESSION_TIME = Time.valueOf("11:11:11");
	private static final Date SESSION_DATE_DIFF = Date.valueOf("2020-03-02");
	private static final Time SESSION_TIME_DIFF = Time.valueOf("03:02:32");
	
	//Constants for Student
	private static final String STUDENT_NAME = "studentUser";
	private static final String STUDENT_NAME_BAD = "badStudentUsername"; 
	private static final String STUDENT_NAME_DIFF = "DiffUsername";
	
	// constants for availability
	private static final Date AVAILABILITY_DATE = Date.valueOf(LocalDate.now().plusDays(3));
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
		setMockOutputAvailabilities();
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
	
	private void setMockOutputAvailabilities() {
		when(availabilityRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			Tutor t = new Tutor();
			t.setUsername(TUTOR_NAME);
			List<Availability> avSet = new ArrayList<Availability>();
			Availability a = new Availability();
			a.setDate(AVAILABILITY_DATE);
			a.setTime(AVAILABILITY_TIME);
			a.setTutor(t);
			avSet.add(a);
			return avSet;
		});	
	}
	
	private void setMockOutputAvailabilitiesAlreadyExist() {
		when(availabilityRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
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
			return avSet;
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
//				Availability a = new Availability();
//				a.setDate(AVAILABILITY_DATE);
//				a.setTime(AVAILABILITY_TIME);
//				a.setTutor(t);
//				t.getAvailability().add(a);
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
			} else if (invocation.getArgument(0).equals(TUTOR_NAME_NO_AVAILABILITIES)){
				Tutor t = new Tutor();
				t.setUsername(TUTOR_NAME_NO_AVAILABILITIES);
				Set<Availability> avSet = null;
				t.setAvailability(avSet);
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
	// mock output for students
	@SuppressWarnings("static-access")
	private void setMockOutputSession() {
		//mock Find all
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
		
		// mock getting a specific section
		when(sessionRepository.findSessionBySessionID((anyInt()))).thenAnswer((InvocationOnMock invocation) -> {
			
			//completely valid session, used in later calls
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
			
			//Time very close, but still a valid session to book on October 26, 2019
			} else if (invocation.getArgument(0).equals(1)) {
				
				Session s = new Session();
				s.setAmountPaid(SESSION_AMOUNT_PAID);
				s.setDate(Date.valueOf(LocalDate.now().plusDays(1)));
				s.setTime(Time.valueOf(LocalTime.now().plusMinutes(5)));
				
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
				
			//Unavailable tutor	
			} else if (invocation.getArgument(0).equals(2)) {
				
				
				Session s = new Session();
				s.setAmountPaid(SESSION_AMOUNT_PAID);
				s.setDate(Date.valueOf(LocalDate.now().plusDays(1)));
				s.setTime(Time.valueOf(LocalTime.now().minusMinutes(5)));
				
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
			
			//Invalid session
			} else if (invocation.getArgument(0).equals(3)) {
				
				Session s = new Session();
				s.setAmountPaid(SESSION_AMOUNT_PAID);
				s.setDate(Date.valueOf(LocalDate.now()));
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
			
			//null session
			} else if (invocation.getArgument(0).equals(4)) {
				
				return null;
			
			//session to test add student 
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
				
			} else if (invocation.getArgument(0).equals(6)) {
				
				Session s = new Session();
				s.setAmountPaid(SESSION_AMOUNT_PAID);
				s.setDate(SESSION_DATE);
				s.setTime(SESSION_TIME);
				
				Room r = new Room();
				r.setRoomNumber(5);
				s.setRoom(r);
				
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
				
				r.setSession(sessions);
				
				t.setSession(sessions);
				
				List<Session> sessions2 = new ArrayList<Session>();
				
				sessions2.add(s);
				
				co.setSession(sessions2);
				
				
				s.setCourseOffering(co);
				
				return s;
				
			} else if (invocation.getArgument(0).equals(7)) {
				
				Session s = new Session();
				s.setAmountPaid(SESSION_AMOUNT_PAID);
				s.setDate(SESSION_DATE);
				s.setTime(SESSION_TIME);
				
				Tutor t = new Tutor();
				t.setUsername(TUTOR_NAME);
				Set<Availability> avSet = new HashSet<Availability>();
				t.setAvailability(avSet);
				Availability a = new Availability();
				a.setDate(SESSION_DATE);
				a.setTime(SESSION_TIME);
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
				
			}
			
			return null;
			
		});
		
	}
	
	
	//************************************************* TESTS *************************************************//
	
	//Test the creation of a valid session!
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
	
	//Test create session null student Name
	@Test
	public void testCreateSessionNullStudentName() {
		
		String error = null;
		try {
			service.createSession(CO_ID, SESSION_DATE, SESSION_TIME,SESSION_AMOUNT_PAID, null, TUTOR_NAME);
		} catch (IllegalArgumentException e) {
			//get the error
			error = e.getMessage();
		}
		//check it was the correct error
		assertEquals(error, ErrorStrings.Invalid_Session_StudentName);
		
	}
	
	//test a null Date
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
	
	//Test if there is a negative amount paid
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
	
	//try to create a session if there is no available room
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
		assertEquals(error, ErrorStrings.Invalid_Session_No_Room);
		
		
	}
	
	//try to add first available room to the session.
	@Test
	public void testUpdateSessionWithAvailableRoomBooking() {
		setMockOutputRoom();
		
		String error = null;
		
		Session session = new Session();
		
		try {
			session = service.createSession(CO_ID, SESSION_DATE, SESSION_TIME, SESSION_AMOUNT_PAID, STUDENT_NAME, TUTOR_NAME);
		} catch (IllegalArgumentException e) {
			fail();
		}
		
		try {
			//Now we must update the session with adding a room.
			session.setRoom(service.getFirstAvailableRoom(SESSION_DATE, SESSION_TIME));
		} catch (IllegalArgumentException e) {
			fail();
		}
		
		int roomId = session.getRoom().getRoomNumber();

		//check it was the correct error
		assertEquals(error, null);
		assertEquals(session.getRoom().getRoomNumber(), roomId);
		
	}
	
	//test creating the session at an early time
	@Test
	public void testCreateSessionTooEarlyTime() {
		
		String error = null;
		
		try {
			service.createSession(CO_ID, SESSION_DATE, Time.valueOf("08:00:00"), SESSION_AMOUNT_PAID, STUDENT_NAME, TUTOR_NAME);
		} catch (IllegalArgumentException e) {
			
			error = e.getMessage();
		}
		//check it was the correct error
		assertEquals(error, ErrorStrings.Invalid_Session_Time);
		
	}
	
	//check creating the session too late
	@Test
	public void testCreateSessionTooLateTime() {
		
		String error = null;
		
		try {
			service.createSession(CO_ID, SESSION_DATE, Time.valueOf("20:10:00"), SESSION_AMOUNT_PAID, STUDENT_NAME, TUTOR_NAME);
		} catch (IllegalArgumentException e) {
			
			error = e.getMessage();
		}
		//check it was the correct error
		assertEquals(error, ErrorStrings.Invalid_Session_Time);
		
	}
	
	//Create a session on the same or in the past
	@Test
	public void testCreateSessionSameDayOrInThePast(){
		
		String error = null;
		
		try {
			service.createSession(CO_ID, Date.valueOf("2019-10-24"), SESSION_TIME, SESSION_AMOUNT_PAID, STUDENT_NAME, TUTOR_NAME);
		} catch (IllegalArgumentException e) {
			
			error = e.getMessage();
		}
		//check it was the correct error
		assertEquals(error, ErrorStrings.Invalid_Session_Date_Same_Day);
		
	}
	
	//create a session null tutor
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
	
	//create a session with an unavailable tutor
	@Test
	public void testCreateSessionUnavailableTutor() {
		
		setMockOutputAvailabilitiesAlreadyExist();
		
		String error = null;
		
		try {
			service.createSession(CO_ID, SESSION_DATE, SESSION_TIME, SESSION_AMOUNT_PAID, STUDENT_NAME, TUTOR_NAME_UNAVAILABLE);
		} catch (IllegalArgumentException e) {
			
			error = e.getMessage();
		}
		//check it was the correct error
		assertEquals(error, ErrorStrings.Invalid_Session_Tutor_Busy);
		
	}
	
	//create a session with a null course offering
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
	
	//test that there is a null student
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
	
	//test getting all sessions
	@Test
	public void testFindAllSessions() {
		
		setMockOutputSession();
		
		List<Session> sessions = null;
		
		try {
			//get the sessions createed
			sessions = service.getAllSessions();
			
		} catch (Exception e) {
			
			fail();
			
		}
		
		//check that the sessions returned are correct
		assertEquals(sessions.size(), 2);
		assertEquals(sessions.get(0).getDate(), SESSION_DATE_DIFF );
		assertEquals(sessions.get(1).getDate(), SESSION_DATE);
		
	}
	
	//test getting a session by ID
	@Test
	public void testGetSessionById() {
		
		setMockOutputSession();
		
		List<Session> sessions = null;
		
		try {
			sessions = service.getAllSessions();
		} catch (Exception e){
			//if this fails, there was a mistake
			fail();
			
		}
		
		if(sessions == null) {
			
			//if sessions is null, then fail
			fail();
			
		}
		//get the id of the first session
		int id = sessions.get(0).getSessionID();
		
		Session s = null;
		
		try {
			//get the session with id
			s = service.getSession(id);
		} catch (Exception e) {
			
			//should pass, if it throws an exception, it is invalid
			fail();
		}
		
		//assert equals the session number returned by getSession and the id
		assertEquals(s.getSessionID(), sessions.get(0).getSessionID());
		
	}
	
	//Test a valid delete 
	@Test
	public void testValidDeleteSession() {
		
		setMockOutputSession();
		
		boolean result = false;
		
		try {
			//check that the session was successfully deleted
			result = service.deleteSession(0);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		//check that the method returned true
		assertEquals(result, true);
		
	}
	
	//Test deleting a session the day before the session is to take palce
	@Test
	public void testDeleteSessionValidWithinOneDay() {
		
		setMockOutputSession();
		
		boolean result = false;
		
		try {
			result = service.deleteSession(1);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		
		//check for successful deletion
		assertEquals(result, true);
		
	}
	
	//test that it is too late on a different day then the session is suppose to be on 
	//to cancel.
	@Test
	public void testDeleteTooLateOnAnotherDateSession() {
		
		setMockOutputSession();
		
		String error = null;
		
		try {
			service.deleteSession(2);
		} catch (IllegalArgumentException e) {
			// get the error message
			error = e.getMessage();
		}
		
		//Check that the error occurred
		assertEquals( error, ErrorStrings.Invalid_Session_Too_Late_To_Cancel);
		
	}
	
	// test delete on the same date
	@Test
	public void testDeleteOnSameDateSession() {
		
		setMockOutputSession();
		
		String error = null;
		
		try {
			service.deleteSession(3);
		} catch (IllegalArgumentException e) {
			// get the error message
			error = e.getMessage();
		}
		
		//Check that the error occurred, and that it is correct
		assertEquals( error, ErrorStrings.Invalid_Session_Cancel_Same_Date);
		
	}
	
	//Delete a Session with a null session
	@Test
	public void testDeleteSessionNullError() {
		
		setMockOutputSession();
		
		String error = null;
		
		try {
			service.deleteSession(4);
		} catch (IllegalArgumentException e) {
			// get the error message
			error = e.getMessage();
		}
		
		//check that it was the correct error
		assertEquals( error, ErrorStrings.Invalid_Session_FindSessionByID);
		
		
	}
	
	//Testing adding a Null student name to a session
	@Test
	public void testAddAStudentToASessionNullStudentName() {
		String error = null;
		
		try {
			service.getSessionByStudent(null);
		} catch (IllegalArgumentException e) {
			// get the error message
			error = e.getMessage();
		}
		
		//check that this is the correct error
		assertEquals( error, ErrorStrings.Invalid_Session_StudentName);
		
	}
	
	//add a student to a session with a null student, but not a null student name
	@Test
	public void testAddStudentToASessionNullStudent() {
		
		String error = null;
		
		try {
			service.getSessionByStudent(STUDENT_NAME_BAD);
		} catch (IllegalArgumentException e) {
			// get the error message
			error = e.getMessage();
		}
		
		//check correct error
		assertEquals( error, ErrorStrings.Invalid_Session_FindStudentByUsername);
		
	}
	
	//test getting sessions by student
	@Test
	public void testGetSessionByStudent() {
		
		List<Session> sessions = null;
		
		try {
			sessions = service.getSessionByStudent(STUDENT_NAME);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		
		//check that the right set of sessions is returned
		assertEquals( 0, sessions.size());
		
	}
	
	//Test trying to create a session more than two weeks from now
	@Test
	public void testCreateSessionInMoreThanTwoWeeks() {
		
		String error = null;
		try {
			service.createSession(CO_ID, Date.valueOf(LocalDate.now().plusDays(15)), SESSION_TIME,SESSION_AMOUNT_PAID, STUDENT_NAME, TUTOR_NAME);
		} catch (IllegalArgumentException e) {
			
			error = e.getMessage();
		}
		//check it was the correct error
		assertEquals(error, ErrorStrings.Invalid_Session_Date_Too_Far);
		
	}
	
	//Try to add a student to a session that already has that student
	@Test
	public void testAddStudentAlreadyAdded() {
		
		setMockOutputSession();
		
		String error = null;
		
		try {
			service.addStudentToSession(0, STUDENT_NAME);
		} catch (IllegalArgumentException e) {
			// get the error message
			error = e.getMessage();
		}
		
		//check for correct error
		assertEquals(error, ErrorStrings.Invalid_Session_Has_Student_Already);
		
	}
	
	//test adding a student to a null session
	@Test
	public void testAddStudentNullSession() {
		
		setMockOutputSession();
		
		String error = null;
		
		try {
			service.addStudentToSession(100, STUDENT_NAME);
		} catch (IllegalArgumentException e) {
			// get error message
			error = e.getMessage();
		}
		
		//check that it is the correct error
		assertEquals(error, ErrorStrings.Invalid_Session_FindSessionByID);
		
	}
	
	//test adding a student to a session
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
	
	//test get the session of a null student name
	@Test
	public void testGetSesstionByStudentNullSName() {
		
		String error = null;
		
		try {
			service.getSessionByStudent(null);
		} catch (IllegalArgumentException e) {
			// get error message
			error = e.getMessage();
		}
		
		//check that it is the correct error
		assertEquals(error, ErrorStrings.Invalid_Session_StudentName);
		
	}
	
	//test get the session of a null student
	@Test
	public void testGetSesstionByStudentNullStudent() {
			
		String error = null;
		
		try {
			service.getSessionByStudent(STUDENT_NAME_BAD);
		} catch (IllegalArgumentException e) {
			// get error message
			error = e.getMessage();
		}
		
		//check that it is the correct error
		assertEquals(error, ErrorStrings.Invalid_Student_FindStudentByUsername);
			
	}
	
	@Test
	public void testCreateSessionNullTutorAvailabilityList() {
		
		String error = null;
		
		try {
			service.createSession(CO_ID, SESSION_DATE, SESSION_TIME, SESSION_AMOUNT_PAID, STUDENT_NAME, TUTOR_NAME_NO_AVAILABILITIES);
		} catch (IllegalArgumentException e) {
			// get error message
			error = e.getMessage();
		}
		
		//check that it is the correct error
		assertEquals(error, ErrorStrings.Invalid_Session_Tutor_Busy);
		
	}
	
	@Test
	public void testRemoveRoomDeleteSession() {
		
		setMockOutputSession();
		
		boolean result = false;
		
		try {
			//check that the session was successfully deleted
			result = service.deleteSession(6);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		//check that the method returned true
		assertEquals(result, true);
		
	}
	
	//@Test
	public void testDeleteWhenTutorAlreadyHasAvailability() {
		
		setMockOutputSession();
		
		String error = null;
		
		try {
			//check that the session was successfully deleted
			service.deleteSession(7);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		//check that the method returned true
		assertEquals(error, ErrorStrings.Invalid_Availability_Already_Exists);
		
	}
	
}
