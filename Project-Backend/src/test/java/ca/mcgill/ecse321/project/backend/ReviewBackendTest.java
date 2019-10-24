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

import ca.mcgill.ecse321.project.ErrorStrings;
import ca.mcgill.ecse321.project.dao.*;
import ca.mcgill.ecse321.project.model.*;
import ca.mcgill.ecse321.project.service.TutoringAppService;

@RunWith(MockitoJUnitRunner.class)
public class ReviewBackendTest {

	@Mock
	private CourseOfferingRepository courseOfferingRepository;  
	@Mock
	private ReviewRepository reviewRepository;  
	@Mock
	private TutorRepository tutorRepository; 
	@Mock
	private UserRepository userRepository;
	@Mock
	private TextRepository textRepository;
	@Mock
	private RatingRepository ratingRepository;
	
	@InjectMocks
	private TutoringAppService service;
	
	// constants for text
	private static final String TEXT_GOOD_DESCRIPTION = "HELLO MY NAME IS ALEX";
	private static final String TEXT_BAD_EMPTY = "";
	private static final String TEXT_BAD_TOO_LONG = "In addition to a stellar faculty, McGill is known for attracting the brightest students from across Canada, the United States, and around the world. McGill students have the highest average entering grades in Canada, and our commitment to fostering the very best has helped our students win more national and international awards on average than their peers at any other Canadian university. The prestigious Rhodes Scholarship has gone to a nation-leading 145 McGill students.";
	private static final int TEXT_GOOD_REVIEWID = 3;
	private static final int TEXT_GOOD_REVIEWID2 = 4;
	private static final int TEXT_BAD_REVIEWID = -3;
	private static final int TEXT_BAD_REVIEWID2 = -4;
	
	// constants for rating
	private static final int RATING_GOOD_REVIEWID = 3;
	private static final int RATING_BAD_REVIEWID = -3;
	private static final int RATING_GOOD_RATING = 3;
	private static final int RATING_BAD_TOOLARGE = 6;
	
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
		//setMockOutputUser();
		setMockOutputTextGood();
		//setMockOutputRoleBad();
		//setMockOutputCourseOffering();
		//setMockOutputRole();
		setMockOutputTextBadTooManyWords();
		//setMockOutputRatingGood();
		//setMockOutputRatingBad();
	}
	
	//********************************************* MOCK OUTPUTS *********************************************//
	/*
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
	}*/
	
	// mock output for review
	private void setMockOutputTextGood() {
		when(textRepository.findTextByReviewID((anyInt()))).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(TEXT_GOOD_REVIEWID)) {
				Review review = new Text();
				review.setReviewID(TEXT_GOOD_REVIEWID);
				
				Text text = new Text();
				text.setReviewID(TEXT_GOOD_REVIEWID);
				text.setDescription(TEXT_GOOD_DESCRIPTION);
				text.setIsAllowed(true);
				return text;
				
			} else if(invocation.getArgument(0).equals(TEXT_BAD_REVIEWID)) {
				Text text = new Text();
				text.setReviewID(TEXT_BAD_REVIEWID);
				text.setDescription(TEXT_BAD_EMPTY);
				text.setIsAllowed(true);
				return text;
				
			} else return null;
		});
	}
	
	private void setMockOutputTextBadTooManyWords() {
		when(textRepository.findTextByReviewID((anyInt()))).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(TEXT_GOOD_REVIEWID2)) {
				Text text = new Text();
				text.setDescription(TEXT_BAD_TOO_LONG);
				text.setIsAllowed(false);
				text.setReviewID(TEXT_GOOD_REVIEWID2);
				return text;
			} else if(invocation.getArgument(0).equals(TEXT_BAD_REVIEWID2)) {
				Text text = new Text();
				text.setDescription(TEXT_BAD_TOO_LONG);
				text.setIsAllowed(true);
				text.setReviewID(TEXT_BAD_REVIEWID2);
				return text;
			} else return null;
		});
	}
	/*
	private void setMockOutputRatingGood() {
		when(ratingRepository.findRatingByReviewID((anyInt()))).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(TEXT_GOOD_REVIEWID)) {
				Rating review = new Rating();
				review.setReviewID(RATING_GOOD_REVIEWID);
				review.setRatingValue(RATING_GOOD_RATING);
				return review;
			} else if(invocation.getArgument(0).equals(RATING_BAD_REVIEWID)){
				Rating review = new Rating();
				review.setReviewID(RATING_BAD_REVIEWID);
				review.setRatingValue(RATING_GOOD_RATING);
				return review;
			} else return null;
		});
	}*/
	/*
	private void setMockOutputRatingBad() {
		when(ratingRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			Rating review = new Rating();
			review.setReviewID(RATING_BAD_REVIEWID);
			review.setRatingValue(RATING_BAD_TOOLARGE);
			return review;
		});
	}*/
	
	/*
	private void setMockOutputCourseOffering() {
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
	}*/
	/*
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
	}*/
	/*
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
	}*/

//************************************************* TESTS *************************************************//

		// Check to make sure all the tests are present.
		@Test
		public void getAllTextsWithID() {
			Text text = new Text();
			String error = null;
			
			try {
				text = service.getText(TEXT_GOOD_REVIEWID);
			} catch(IllegalArgumentException e) { error = e.getMessage();}
			
			assertEquals(error, ErrorStrings.Invalid_NULL);
			assertEquals(TEXT_GOOD_REVIEWID, 3);
		}
		
		@Test
		public void getCheck() {
			assertEquals(2,2);
			
		}
		
}
	