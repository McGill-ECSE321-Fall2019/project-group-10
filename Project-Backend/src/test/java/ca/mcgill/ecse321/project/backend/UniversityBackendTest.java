package ca.mcgill.ecse321.project.backend;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.project.dao.*;
import ca.mcgill.ecse321.project.model.*;
import ca.mcgill.ecse321.project.service.TutoringAppService;

@RunWith(MockitoJUnitRunner.class)
public class UniversityBackendTest {

	@Mock 
	private AvailabilityRepository availabilityRepository;
	@Mock
	private CourseOfferingRepository courseOfferingRepository;  
	@Mock
	private CourseRepository courseRepository;
	@Mock
	private ReviewRepository reviewRepository;  
	@Mock
	private RoleRepository roleRepository; 
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
	
	private static final String UNI_NAME = "McGill";
	private static final String UNI_ADDR = "304 Sherbrooke";
//	private static final int UNI_ID = 1;
	private static final String UNI_NAME_BAD = "Concordia";
	
	private static final String COURSE_NAME = "ECSE321";
	private static final String COURSE_INFO = "Intro to soft eng";	
	private static final String COURSE_NAME_BAD = "ECSE443";
	
	private static final Term CO_TERM = Term.Fall;
	private static final int CO_YEAR = 2019;
	private static final int CO_ID = 1;
	private static final int CO_ID_BAD = 2;
	private static final int CO_ID_EMPTY = 3;
	
	private static final String TUTOR_NAME = "username";
	
	@Before
	public void setMockOutput() {
		when(universityRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			List<University> unis = new ArrayList<>();
			University uni = new University();
			uni.setName(UNI_NAME);
			uni.setAddress(UNI_ADDR);
			unis.add(uni);
			return unis;
		});
		
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
//			// create a university
//			University uni = new University();
//			uni.setName(UNI_NAME);
//			
//			// create a course
//			Course c = new Course();
//			c.setCourseName(COURSE_NAME);
//			c.setUniversity(uni);
			
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
		
		// Whenever anything is saved, just return the parameter object
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		//when(universityRepository.save(any(University.class))).thenAnswer(returnParameterAsAnswer);
		//when(courseRepository.save(any(Course.class))).thenAnswer(returnParameterAsAnswer);
	}

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
		assertEquals(error, "No courses offered for this university");		
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
		assertEquals(error, "No courses offerings offered for this course");
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
		assertEquals(error, "No courses offerings offered for this course");
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
		assertEquals(tutors.get(0).getUsername(), TUTOR_NAME);
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
		
		// check that we have one tutor and that it is the right one
		assertEquals(0, tutors.size());
		assertEquals(error, "This course offering does not exist");
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
		
		// check that we have one tutor and that it is the right one
		assertEquals(0, tutors.size());
		assertEquals(error, "No tutors for this course offering");
	}
	
}
