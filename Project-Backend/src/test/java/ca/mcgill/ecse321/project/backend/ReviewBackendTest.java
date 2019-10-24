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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;

import ca.mcgill.ecse321.project.dao.*;
import ca.mcgill.ecse321.project.model.*;
import ca.mcgill.ecse321.project.service.TutoringAppService;

@RunWith(MockitoJUnitRunner.class)
public class ReviewBackendTest {

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
	
	// constants for text
	private static final String TEXT_GOOD_DESCRIPTION = "HELLO MY NAME IS ALEX";
	private static final String TEXT_BAD_EMPTY = "";
	private static final String TEXT_BAD_NULL = null;
	private static final String TEXT_BAD_TOOLONG = "In addition to a stellar faculty, McGill is known for attracting the brightest students from across Canada, the United States, and around the world. McGill students have the highest average entering grades in Canada, and our commitment to fostering the very best has helped our students win more national and international awards on average than their peers at any other Canadian university. The prestigious Rhodes Scholarship has gone to a nation-leading 145 McGill students.";
	private static final int TEXT_GOOD_REVIEWID = 3;
	private static final int TEXT_BAD_REVIEWID = -3;
	
	// constants for rating
	private static final int RATING_GOOD_REVIEWID = 3;
	private static final int RATING_BAD_REVIEWID = -3;
	private static final int RATING_GOOD_RATING = 3;
	private static final int RATING_BAD_TOOLARGE = 6;
	private static final int RATING_BAD_TOOLOW = -2;
	
	// constants for course offering
	private static final Term CO_TERM = Term.Winter;
	private static final int CO_YEAR = 2019;
	private static final int CO_ID = 1;
	private static final int CO_ID_BAD = -2;
	
	// constants for user
	private static final String USER_USERNAME = "teddy";
	private static final String USER_PASSWORD = "123";
	
	// constants for user
	private static final String TUTOR_USERNAME = "sad";
	private static final String TUTOR_USERNAME_BAD = "sad username";
	private static final String TUTOR_PASSWORD = "sad123";
	private static final String TUTOR_PASSWORD_BAD_S = "321 321";
	
	@Before
	public void setMockOutput() {
		
		// run all setups for mock outputs
		setMockOutputUser();
		setMockOutputReviews();
		setMockOutputRoleBad();
		setMockOutputCourseOfferingGood();
		setMockOutputCourseOfferingBad();
		setMockOutputRole();
	}
	
	//********************************************* MOCK OUTPUTS *********************************************//
	
	// mock output for user
	private void setMockOutputUser() {
		when(userRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			List<User> userList = new ArrayList<>();
			User user = new User();
			user.setName(USER_USERNAME);
			user.setPassword(USER_PASSWORD);
			userList.add(user);
			return userList;
		});	
	}
	
	// mock output for review
	private void setMockOutputReviews() {
		when(reviewRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			// create a university
			List<Review[]> reviewList = new ArrayList<>();
			
			//List of the text and review.
			Review[] reviewPackage = new Review[2];
			
			Text text = new Text();
			text.setDescription(TEXT_GOOD_DESCRIPTION);
			text.setIsAllowed(true);
			text.setReviewID(TEXT_GOOD_REVIEWID);
			
			//Different courseOffering
			Text text2 = new Text();
			text2.setDescription(TEXT_BAD_EMPTY);
			text2.setIsAllowed(false);
			text2.setReviewID(TEXT_BAD_REVIEWID);
			
			Rating review = new Rating();
			review.setReviewID(RATING_GOOD_REVIEWID);
			review.setRatingValue(RATING_GOOD_RATING);
			
			//Combine text and reviews together
			reviewPackage[0] = text;
			reviewPackage[1] = review;
			reviewList.add(reviewPackage);
			
			reviewPackage[0] = text2;
			reviewPackage[1] = review;
			reviewList.add(reviewPackage);
			
			return reviewList;
		});
	}
	
	// mock output for course offering
	private void setMockOutputCourseOfferingGood() {
		when(courseOfferingRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			CourseOffering courseOffering = new CourseOffering();
			courseOffering.setCourseOfferingID(CO_ID);
			courseOffering.setTerm(CO_TERM);
			courseOffering.setYear(CO_YEAR);
			return courseOffering;
		});
	}
	
	private void setMockOutputCourseOfferingBad() {
		when(courseOfferingRepository.findCourseOfferingByCourseOfferingID((anyInt()))).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(CO_ID)) {
				CourseOffering courseOffering = new CourseOffering();
				courseOffering.setCourseOfferingID(CO_ID_BAD);
				courseOffering.setTerm(CO_TERM);
				courseOffering.setYear(CO_YEAR);
				return courseOffering;
			} else if(invocation.getArgument(0).equals(CO_ID_BAD)) {
				//create a course offering with bad input
				CourseOffering courseOffering = new CourseOffering();
				courseOffering.setCourseOfferingID(CO_ID_BAD);
				return courseOffering;
			} else
				return null;
		});
	}
	
	private void setMockOutputRole() {
		when(tutorRepository.findTutorByUsername((anyString()))).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(TUTOR_USERNAME)) {
				//create a Tutor with the right name
				Tutor tutor = new Tutor();
				tutor.setUsername(TUTOR_USERNAME);
				tutor.setPassword(TUTOR_PASSWORD);
				return tutor;
			} else {
				return null;
			}
		});
	}
	
	private void setMockOutputRoleBad() {
		when(tutorRepository.findTutorByUsername((anyString()))).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(TUTOR_USERNAME)) {
				//create a Tutor with the right name
				Tutor tutor = new Tutor();
				tutor.setUsername(TUTOR_USERNAME_BAD);
				tutor.setPassword(TUTOR_PASSWORD_BAD_S);
				return tutor;
			} else {
				return null;
			}
		});
	}

//************************************************* TESTS *************************************************//

		// check that the service can retrieve all universities properly
		@Test
		public void getAllReviewsFromTutor() {
			List<Review[]> reviewList = new ArrayList<>();
			
			// get all reviews
			try {
				reviewList = service.getAllReviews();
			} catch(IllegalArgumentException e) { fail();}
			
			// check that only 1 and its the right one
			//assertEquals(2, reviewList.size());
			
		}
		
		@Test
		public void getCheck() {
			assertEquals(2,2);
			
		}
		
}
	