package ca.mcgill.ecse321.project.backend;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

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
	private static final String TEXT_GOOD_DESCRIPTION = "HELLO MY NAME IS JOHN";
	private static final String TEXT_BAD_EMPTY = "";
	private static final String TEXT_BAD_TOO_LONG = "In addition to a stellar faculty, McGill is known for attracting the brightest students from across Canada, the United States, and around the world. McGill students have the highest average entering grades in Canada, and our commitment to fostering the very best has helped our students win more national and international awards on average than their peers at any other Canadian university. The prestigious Rhodes Scholarship has gone to a nation-leading 145 McGill students.";
	private static final int TEXT_GOOD_REVIEWID = 3;
	private static final int TEXT_BAD_REVIEWID = -3;
	private static final int TEXT_BAD_REVIEWID2 = -4;
	
	// constants for rating
	private static final int RATING_GOOD_REVIEWID = 3;
	private static final int RATING_GOOD_REVIEWID2 = 4;
	private static final int RATING_GOOD_REVIEWID3 = 5;

	private static final int REVIEW_ID1 = 10;
	private static final int REVIEW_ID2 = 11;
	
	private static final int RATING_BAD_NULL = -3;
	private static final int RATING_BAD_TOOLARGE = 6;
	
	// constants for course offering
	private static final Term CO_TERM = Term.Winter;
	private static final int CO_YEAR = 2019;
	private static final int CO_ID = 1;
	private static final int CO_ID2 = 2;
	
	// constants for user
	private static final String USER_USERNAME = "teddy";
	private static final String USER_PASSWORD = "123";
	private static final String USER_EMAIL = "testman@man,com";
	
	// constants for tutor
	private static final double HOURLY_RATE = 10.00;

	// constants for user
	private static final String TUTOR_USERNAME = "teddy";
	private static final String TUTOR_USERNAME2 = "teddy2";
	private static final int TUTOR_ID = 10;
	private static final String TUTOR_PASSWORD = "123";
	private static final String TUTOR_PASSWORD2 = "1234";
	
	@Before
	public void setMockOutput() {
		
		setMockOutputUser();
		setMockOutputTutor();
		setMockOutputCourseOffering();
		setMockOutputReview();
		setMockOutputText();
		setMockOutputRating();
	}
	
	//********************************************* MOCK OUTPUTS *********************************************//
	
	private void setMockOutputUser() {
		when(userRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			List<TSUser> userList = new ArrayList<>();
			TSUser user = new TSUser();
			user.setName(USER_USERNAME);
			user.setEmail(USER_PASSWORD);
			userList.add(user);
			return userList;
		});	
	}
	
	private void setMockOutputTutor() {
		when(userRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			TSUser user = new TSUser();
			user.setName(USER_USERNAME);
			user.setEmail(USER_EMAIL);
			
			List<Tutor> tutorList = new ArrayList<>();
			Tutor tutor = new Tutor();
			tutor.setHourlyRate(HOURLY_RATE);
			tutor.setPassword(TUTOR_PASSWORD);
			tutor.setUsername(TUTOR_USERNAME);
			tutor.setId(TUTOR_ID);
			tutor.setUser(user);
			tutorList.add(tutor);
			return tutorList;
		});	
	}
	
	private void setMockOutputText() {
		when(textRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			List<Text> textList = new ArrayList<>();
	
			//Good description
			Text text1 = new Text();
			text1.setDescription(TEXT_GOOD_DESCRIPTION);
			text1.setIsAllowed(true);
			text1.setReviewID(REVIEW_ID1);
			textList.add(text1);
			
			//Good description
			Text text2 = new Text();
			text2.setDescription(TEXT_GOOD_DESCRIPTION);
			text2.setIsAllowed(true);
			text2.setReviewID(REVIEW_ID2);
			textList.add(text1);
			
			return textList;
		});	
	}
	
	private void setMockOutputRating() {
		when(ratingRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			List<Rating> ratingList = new ArrayList<>();
			
			//Good description
			Rating rating = new Rating();
			rating.setReviewID(REVIEW_ID1);
			ratingList.add(rating);
			
			return ratingList;
		});	
	}
	
	private void setMockOutputReview() {
		when(reviewRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			List<Review> reviewList = new ArrayList<>();
			
			CourseOffering courseO2 = new CourseOffering();
			courseO2.setCourseOfferingID(CO_ID2);
			
			//Good description
			Review review = new Text();
			review.setReviewID(REVIEW_ID1);
			review.setCourseOffering(courseO2);
			
			Review review2 = new Text();
			review2.setReviewID(REVIEW_ID2);
			review2.setCourseOffering(courseO2);
			
			reviewList.add(review2);
			reviewList.add(review);
	
			return reviewList;
		});	
	}
	
	private void setMockOutputCourseOffering() {
		when(courseOfferingRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			
			List<Tutor> tutorList = new ArrayList<>();
			Tutor tutor = new Tutor();
			tutor.setPassword(TUTOR_PASSWORD);
			tutor.setUsername(TUTOR_USERNAME);
			tutorList.add(tutor);
			
			Tutor tutor2 = new Tutor();
			tutor2.setPassword(TUTOR_PASSWORD);
			tutor2.setUsername(TUTOR_USERNAME);
			tutorList.add(tutor2);
			
			Set<Review> listReview = new HashSet<>();
			
			//Good description
			Review review = new Text();
			review.setReviewID(REVIEW_ID1);
			
			Review review2 = new Text();
			review2.setReviewID(REVIEW_ID2);
			
			listReview.add(review);
			listReview.add(review2);
			
			List<CourseOffering> courseOfferingList = new ArrayList<>();
			CourseOffering courseO = new CourseOffering();
			courseO.setTerm(CO_TERM);
			courseO.setYear(CO_YEAR);
			courseO.setCourseOfferingID(CO_ID);
	
			CourseOffering courseO2 = new CourseOffering();
			courseO2.setTerm(CO_TERM);
			courseO2.setYear(CO_YEAR);
			courseO2.setCourseOfferingID(CO_ID2);
			courseO2.setTutors(tutorList);
			courseO2.setReview(listReview);
			
			courseOfferingList.add(courseO);
			courseOfferingList.add(courseO2);
			
			return courseOfferingList;
		});	
	}
	
	private void setMockOutputRatingMakeIntoList() {
		when(ratingRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
		
			CourseOffering co = new CourseOffering();
			co.setCourseOfferingID(CO_ID);
			 
			Role role = new Tutor();
			role.setUsername(TUTOR_USERNAME);
			
			List<Rating> reviewList = new ArrayList<>();
			
			Rating rating1 = new Rating();
			rating1.setRatingValue(RATING_GOOD_REVIEWID);
			rating1.setReviewID(RATING_GOOD_REVIEWID);
			rating1.setCourseOffering(co);
			rating1.setWrittenAbout(role);
			
			reviewList.add(rating1);
			
			Rating rating2 = new Rating();
			rating2.setRatingValue(RATING_BAD_TOOLARGE);
			rating2.setReviewID(RATING_GOOD_REVIEWID2);
			rating2.setCourseOffering(co);
			rating2.setWrittenAbout(role);
			
			reviewList.add(rating2);
			
			Rating rating3 = new Rating();
			rating3.setRatingValue(RATING_BAD_NULL);
			rating3.setReviewID(RATING_GOOD_REVIEWID3);
			rating3.setCourseOffering(co);
			rating3.setWrittenAbout(role);
			
			reviewList.add(rating3);		
			
			return reviewList;
		});	
		
		when(ratingRepository.findRatingByReviewID((anyInt()))).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(TEXT_GOOD_REVIEWID)) {
				Role tutor = new Tutor();
				tutor.setUsername(USER_USERNAME);
				
				Rating rating = new Rating();
				rating.setReviewID(TEXT_GOOD_REVIEWID);
				rating.setRatingValue(RATING_GOOD_REVIEWID);
				rating.setWrittenAbout(tutor);
				return rating;
			
			} else if(invocation.getArgument(0).equals(TEXT_BAD_REVIEWID)) {
				Rating rating = new Rating();
				rating.setReviewID(TEXT_GOOD_REVIEWID);
				return rating;
				
				
			} else return null;
		});
		
	}
	
	//Check finding a list and when searching for single text
	private void setMockOutputTextMakeIntoList() {
		when(textRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			
			CourseOffering co = new CourseOffering();
			co.setCourseOfferingID(CO_ID);
			 
			Role role = new Tutor();
			role.setUsername(TUTOR_USERNAME);
			
			List<Text> textList = new ArrayList<>();
			//Good description
			Text text1 = new Text();
			text1.setDescription(TEXT_GOOD_DESCRIPTION);
			text1.setIsAllowed(true);
			text1.setWrittenAbout(role);
			text1.setCourseOffering(co);
			textList.add(text1);
			
			//empty
			Text text2 = new Text();
			text2.setDescription(TEXT_BAD_EMPTY);
			text2.setIsAllowed(false);
			text2.setWrittenAbout(role);
			text2.setCourseOffering(co);
			textList.add(text2);			
			
			//Too long of a description
			Text text3 = new Text();
			text3.setDescription(TEXT_BAD_TOO_LONG);
			text3.setIsAllowed(false);
			text3.setCourseOffering(co);
			text3.setWrittenAbout(role);
			textList.add(text3);
			
			CourseOffering courseOffering1 = new CourseOffering();
			courseOffering1.setCourseOfferingID(CO_ID2);
			
			Role tutor2 = new Tutor();
			tutor2.setPassword(TUTOR_PASSWORD);
			tutor2.setUsername(TUTOR_USERNAME);
			
			Text text4 = new Text();
			text4.setDescription(TEXT_BAD_TOO_LONG);
			text4.setIsAllowed(false);
			text4.setCourseOffering(courseOffering1);
			text4.setWrittenAbout(tutor2);
			textList.add(text4);
			return textList;
		});	
		//Making specific create text tests.
		when(textRepository.findTextByReviewID((anyInt()))).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(TEXT_GOOD_REVIEWID)) {
				CourseOffering co = new CourseOffering();
				co.setCourseOfferingID(CO_ID);
				
				Role tutor = new Tutor();
				tutor.setUsername(USER_USERNAME);
				tutor.setPassword(TUTOR_PASSWORD);
				tutor.setId(TUTOR_ID);
				
				Text text = new Text();
				text.setReviewID(TEXT_GOOD_REVIEWID);
				text.setDescription(TEXT_GOOD_DESCRIPTION);
				text.setWrittenAbout(tutor);
				text.setCourseOffering(co);
				return text;
				
			} else if(invocation.getArgument(0).equals(TEXT_BAD_REVIEWID)) {
				Text text = new Text();
				text.setReviewID(TEXT_BAD_REVIEWID);
				return text;
				
			} else return null;
		});
		
	}
	


//************************************************* TESTS *************************************************//

		
		@Test
		public void getAllRating() {
			setMockOutputRatingMakeIntoList();
			List<Rating> ratingsList = new ArrayList<>();
			String error = "";
			
			try {
				ratingsList = service.getAllRatings();
			} catch(IllegalArgumentException e) {fail();}
			
			assertEquals(ratingsList.size(), 3);
			assertEquals(ratingsList.get(0).getRatingValue(), RATING_GOOD_REVIEWID);
			assertEquals(ratingsList.get(1).getRatingValue(), RATING_BAD_TOOLARGE);
		}
		
		
		// Check to make sure all the tests are present.
		@Test
		public void getAllTexts() {
			setMockOutputTextMakeIntoList();
			
			List<Text> textList = new ArrayList<>();
			String error = "";
			
			try {
				textList = service.getAllTexts();
			} catch(IllegalArgumentException e) {fail();}
					
			assertEquals(textList.size(), 4);
			assertEquals(textList.get(0).getDescription(), TEXT_GOOD_DESCRIPTION);
			assertEquals(textList.get(0).getIsAllowed(), true);
			assertEquals(textList.get(1).getDescription(), "");
		}
		
		@Test
		public void getTextById() {
			setMockOutputTextMakeIntoList();
			String error = "";
			Text text = new Text();
			
			try {
				text = service.getText(TEXT_GOOD_REVIEWID);
			} catch(IllegalArgumentException e) { error = e.getMessage();}
			
			assertEquals(error, "");
			assertEquals(text.getReviewID(), TEXT_GOOD_REVIEWID);
			assertEquals(text.getWrittenAbout().getUsername(), USER_USERNAME);
		}
		
		//Check bad id for text.
		@Test
		public void getRatingById() {
			setMockOutputRatingMakeIntoList();
			String error = "";
			Rating rating = new Rating();
			
			try {
				rating = service.getRating(TEXT_GOOD_REVIEWID);
			} catch(IllegalArgumentException e) { error = e.getMessage();}
			
			assertEquals(rating.getRatingValue(), RATING_GOOD_REVIEWID);
			assertEquals(error, "");
		}
		
		//Create a rating with too high of a rating value
		@Test
		public void createRatingTooHighRating() {
			setMockOutputRatingMakeIntoList();
			List<Rating> ratingsList = new ArrayList<>();
			String error = "";
			Rating rating = new Rating();
			
			try {
				ratingsList = service.getAllRatings();
			} catch(IllegalArgumentException e) {fail();}
			
			Rating currentRating = ratingsList.get(1);
			
			assertEquals(currentRating.getRatingValue(), RATING_BAD_TOOLARGE);
			assertEquals(currentRating.getWrittenAbout().getUsername(), TUTOR_USERNAME);
			assertEquals(currentRating.getCourseOffering().getCourseOfferingID(), CO_ID);
			
			try {
				rating = service.createRating(currentRating.getRatingValue(), currentRating.getWrittenAbout().getUsername(), currentRating.getCourseOffering().getCourseOfferingID());	
			} catch(IllegalArgumentException e) {error = e.getMessage();}
					
			assertEquals(RATING_BAD_TOOLARGE, currentRating.getRatingValue());
			assertEquals(ErrorStrings.Invalid_Rating_NegativeRatingValue, error);
			
		}
		
		//Check to see if rating it too low
		@Test
		public void createRatingTooLowRating() {
			setMockOutputRatingMakeIntoList();
			List<Rating> ratingsList = new ArrayList<>();
			String error = "";
			Rating rating = new Rating();
			
			try {
				ratingsList = service.getAllRatings();
			} catch(IllegalArgumentException e) {fail();}
			
			Rating currentRating = ratingsList.get(2);
			
			assertEquals(currentRating.getRatingValue(), RATING_BAD_NULL);
			assertEquals(currentRating.getWrittenAbout().getUsername(), TUTOR_USERNAME);
			assertEquals(currentRating.getCourseOffering().getCourseOfferingID(), CO_ID);
			
			try {
				rating = service.createRating(currentRating.getRatingValue(), currentRating.getWrittenAbout().getUsername(), currentRating.getCourseOffering().getCourseOfferingID());	
			} catch(IllegalArgumentException e) {error = e.getMessage();}
					
			assertEquals(RATING_BAD_NULL, currentRating.getRatingValue());
			assertEquals(ErrorStrings.Invalid_Rating_NegativeRatingValue, error);
			
		}
		
		//Check for too large description.
		@Test
		public void createTextTooLongDescription() {
			setMockOutputTextMakeIntoList();
			List<Text> textList = new ArrayList<>();
			String error = "";
			Text rating = new Text();
			
			try {
				textList = service.getAllTexts();
			} catch(IllegalArgumentException e) {fail();}
			
			Text currentText = textList.get(1);
			
			assertEquals(currentText.getDescription(), TEXT_BAD_EMPTY);
			assertEquals(currentText.getWrittenAbout().getUsername(), TUTOR_USERNAME);
			assertEquals(currentText.getCourseOffering().getCourseOfferingID(), CO_ID);
			
			try {
				rating = service.createText(currentText.getDescription(), currentText.getIsAllowed(), currentText.getWrittenAbout().getUsername(), currentText.getCourseOffering().getCourseOfferingID());	
			} catch(IllegalArgumentException e) {error = e.getMessage();}
					
			assertEquals(TEXT_BAD_EMPTY, currentText.getDescription());
			assertEquals(ErrorStrings.Invalid_Text_Description, error);
			
		}
		
		//Check for empty descriptions..
		@Test
		public void createTextEmptyDescription() {
			setMockOutputTextMakeIntoList();
			List<Text> textList = new ArrayList<>();
			String error = "";
			Text rating = new Text();
			
			try {
				textList = service.getAllTexts();
			} catch(IllegalArgumentException e) {fail();}
			
			Text currentText = textList.get(2);
			
			assertEquals(currentText.getDescription(), TEXT_BAD_TOO_LONG);
			assertEquals(currentText.getWrittenAbout().getUsername(), TUTOR_USERNAME);
			assertEquals(currentText.getCourseOffering().getCourseOfferingID(), CO_ID);
			
			try {
				rating = service.createText(currentText.getDescription(), currentText.getIsAllowed(), currentText.getWrittenAbout().getUsername(), currentText.getCourseOffering().getCourseOfferingID());	
			} catch(IllegalArgumentException e) {error = e.getMessage();}
					
			assertEquals(TEXT_BAD_TOO_LONG, currentText.getDescription());
			assertEquals(ErrorStrings.Invalid_Text_Description, error);
		}
		
		@Test
		public void getListOfReviewsByCourseOfferingId() {
			
			Set<Review> reviewList = new HashSet<>();
			String error = "";
			Text rating = new Text();
			
			List<CourseOffering> courseOffering = service.getAllCourseOfferings();
			
			assertEquals(courseOffering.size(), 2);
			
			int courseOfferingId = courseOffering.get(1).getCourseOfferingID();
			
			assertEquals(2, courseOfferingId);
			
			try {
				reviewList = service.getAllReviewsByCO(CO_ID2);
			} catch(IllegalArgumentException e) {error = e.getMessage();}
			
			assertEquals(error, "");
			assertEquals(1, reviewList.size());
		
		}
}
	