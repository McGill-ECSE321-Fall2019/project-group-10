package ca.mcgill.ecse321.project.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;

import ca.mcgill.ecse321.project.dao.*;

@RunWith(MockitoJUnitRunner.class)
public class BusinessMethodTest {

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
	
	@Before
	public void setMockOutput() {
		
		//TODO: Set up the mock database like they do in the tutorial
//		when(personDao.findById((anyString()))).thenAnswer((InvocationOnMock invocation) -> {
//			if (invocation.getArgument(0).equals(PERSON_KEY)) {
//				Person person = new Person();
//				person.setName(PERSON_KEY);
//				return person;
//			} else {
//				return null;
//			}
//		});
//		// Whenever anything is saved, just return the parameter object
//		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
//			return invocation.getArgument(0);
//		};
//		when(personDao.save(any(Person.class))).thenAnswer(returnParameterAsAnswer);
//		when(eventDao.save(any(Event.class))).thenAnswer(returnParameterAsAnswer);
//		when(registrationDao.save(any(Registration.class))).thenAnswer(returnParameterAsAnswer);
	}
	
	@Test
	public void testTrue() {
		
		assertEquals(1,1);
		
	}
}
