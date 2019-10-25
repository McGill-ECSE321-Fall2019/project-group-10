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
	private static final String UNI_NAME_BAD = "Concordia";
	
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
	
	// constant for room
	private static final int ROOM_NUM = 203;
	
	// constants for session
	private static final Date SESSION_DATE = Date.valueOf("2018-02-01");
	private static final Time SESSION_TIME = Time.valueOf("11:11:11");
	private static final Date SESSION_DATE_DIFF = Date.valueOf("2020-03-02");
	private static final Time SESSION_TIME_DIFF = Time.valueOf("03:02:32");
	
	@Before
	public void setMockOutput() {
		
		// run all setups for mock outputs

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
			
			Session s2 = new Session();
			s2.setRoom(r2);
			s2.setDate(SESSION_DATE);
			s2.setTime(SESSION_TIME);
			
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
