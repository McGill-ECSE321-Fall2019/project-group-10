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
	private static final String USER_EMAIL = "testman@man,com";
	private static final String USER_USERNAME2 = "teddy2";
	private static final String USER_EMAIL2 = "testman2@man.com";
	
	// constants for user
	private static final String TUTOR_USERNAME = "teddy";
	private static final String TUTOR_USERNAME2 = "teddy2";
	private static final int TUTOR_ID = 10;
	private static final String TUTOR_PASSWORD = "123";
	private static final String TUTOR_PASSWORD2 = "1234";
	
	@Before
	public void setMockOutput() {
		
		setMockOutputTutor();
		setMockOutputCourseOffering();
	}
	
	//********************************************* MOCK OUTPUTS *********************************************//
	
	
	//create 2 tutors.
	private void setMockOutputTutor() {
		when(tutorRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			//link users to the tutors.
			TSUser user = new TSUser();
			user.setEmail(USER_EMAIL);
			
			TSUser user1 = new TSUser();
			user1.setEmail(USER_EMAIL2);
			
			List<Tutor> tutorList = new ArrayList<>();
			
			//Create tutors
			Tutor tutor = new Tutor();
			tutor.setPassword(TUTOR_PASSWORD);
			tutor.setUsername(TUTOR_USERNAME);
			tutor.setUser(user);
			
			Tutor tutor1 = new Tutor();
			tutor1.setPassword(TUTOR_PASSWORD2);
			tutor1.setUsername(TUTOR_USERNAME2);
			tutor1.setUser(user1);
			
			tutorList.add(tutor);
			tutorList.add(tutor1);
			
			return tutorList;
		});	
		
		when(tutorRepository.findTutorByUsername((anyString()))).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(TUTOR_USERNAME2)) {
				
				//Create a tutor and link with user
				TSUser user = new TSUser();
				user.setEmail(USER_EMAIL2);
				
				Tutor tutor = new Tutor();
				tutor.setUsername(TUTOR_USERNAME2);
				tutor.setPassword(TUTOR_PASSWORD2);
				tutor.setUser(user);
				
				return tutor;
			
			} else if(invocation.getArgument(0).equals(TUTOR_USERNAME)) {
				
				//Create a tutor and link with user
				TSUser user = new TSUser();
				user.setEmail(USER_EMAIL);
				
				Tutor tutor = new Tutor();
				tutor.setUsername(TUTOR_USERNAME);
				tutor.setPassword(TUTOR_PASSWORD);
				tutor.setUser(user);
				
				return tutor;
				
			} else return null;
		});
	}
	
	//Create setup course offerings.
	private void setMockOutputCourseOffering() {
		when(courseOfferingRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			
			List<Tutor> tutorList = new ArrayList<>();
			
			//Link tutors
			Tutor tutor = new Tutor();
			tutor.setPassword(TUTOR_PASSWORD);
			tutor.setUsername(TUTOR_USERNAME);
			tutorList.add(tutor);
			
			Tutor tutor2 = new Tutor();
			tutor2.setPassword(TUTOR_PASSWORD2);
			tutor2.setUsername(TUTOR_USERNAME2);
			tutorList.add(tutor2);
			
			Set<Review> listReview = new HashSet<>();
			
			//Create review link
			Review review = new Text();
			review.setReviewID(REVIEW_ID1);
			
			Review review2 = new Text();
			review2.setReviewID(REVIEW_ID2);
			
			listReview.add(review);
			listReview.add(review2);
			
			List<CourseOffering> courseOfferingList = new ArrayList<>();
			
			//Create course offerings for linking.
			CourseOffering courseO = new CourseOffering();
			courseO.setTerm(CO_TERM);
			courseO.setYear(CO_YEAR);
			courseO.setCourseOfferingID(CO_ID);
	
			//A second offering for the list.
			CourseOffering courseO2 = new CourseOffering();
			courseO2.setTerm(CO_TERM);
			courseO2.setYear(CO_YEAR);
			courseO2.setCourseOfferingID(CO_ID2);

			courseO2.setTutors(tutorList);
			courseO2.setReview(listReview);
			
			//Setup all links.
			courseOfferingList.add(courseO);
			courseOfferingList.add(courseO2);
			
			return courseOfferingList;
			
		});
			when(courseOfferingRepository.findCourseOfferingByCourseOfferingID((anyInt()))).thenAnswer((InvocationOnMock invocation) -> {
				if (invocation.getArgument(0).equals(CO_ID2)) {
					
					//Create course offerings for linking.
					CourseOffering course = new CourseOffering();
					course.setTerm(CO_TERM);
					course.setYear(CO_YEAR);
					course.setCourseOfferingID(CO_ID2);
					
					return course;
				
				} else if(invocation.getArgument(0).equals(CO_ID)) {
					
					//Create a tutor and link with user
					CourseOffering course = new CourseOffering();
					course.setTerm(CO_TERM);
					course.setYear(CO_YEAR);
					course.setCourseOfferingID(CO_ID);
					
					return course;
					
				} else return null;
		});
	}

	//Create a list of all ratings with unique ids - linked with tutors and course offerings.
	private void setMockOutputRatingMakeIntoList() {
		when(ratingRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
		
			//linking course offerings.
			CourseOffering co = new CourseOffering();
			co.setCourseOfferingID(CO_ID);
			 
			//linking roles.
			Role role = new Tutor();
			role.setUsername(TUTOR_USERNAME);
			
			List<Rating> reviewList = new ArrayList<>();
			
			//linking the ratings and texts together.
			Rating rating1 = new Rating();
			rating1.setRatingValue(RATING_GOOD_REVIEWID);
			rating1.setReviewID(RATING_GOOD_REVIEWID);
			rating1.setCourseOffering(co);
			rating1.setWrittenAbout(role);
			
			reviewList.add(rating1);
			
			//Create a rating with too high of a rating.
			Rating rating2 = new Rating();
			rating2.setRatingValue(RATING_BAD_TOOLARGE);
			rating2.setReviewID(RATING_GOOD_REVIEWID2);
			rating2.setCourseOffering(co);
			rating2.setWrittenAbout(role);
			
			reviewList.add(rating2);
			
			//Create a rating with too low of a value - null.
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
				
				//Create a rating and link it with the tutor.
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
	
	//Create a list of text created and attach to tutor and course offering.
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
			
			//link course offering.
			CourseOffering courseOffering1 = new CourseOffering();
			courseOffering1.setCourseOfferingID(CO_ID2);
			
			//link a second tutor
			Role tutor2 = new Tutor();
			tutor2.setPassword(TUTOR_PASSWORD);
			tutor2.setUsername(TUTOR_USERNAME);
			
			//add another texst with a unique id
			Text text4 = new Text();
			text4.setDescription(TEXT_BAD_TOO_LONG);
			text4.setIsAllowed(true);
			text4.setCourseOffering(courseOffering1);
			text4.setWrittenAbout(tutor2);
			textList.add(text4);
			
			//add a 5th text for testing - different inputs for each.
			Text text5 = new Text();
			text5.setDescription(TEXT_BAD_TOO_LONG);
			text5.setIsAllowed(false);
			text5.setCourseOffering(co);
			textList.add(text5);
			
			return textList;
		});	
		//Making specific create text tests.
		when(textRepository.findTextByReviewID((anyInt()))).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(TEXT_GOOD_REVIEWID)) {
				CourseOffering co = new CourseOffering();
				co.setCourseOfferingID(CO_ID);
				
				//Create tutor to link to the text.
				Role tutor = new Tutor();
				tutor.setUsername(USER_USERNAME);
				tutor.setPassword(TUTOR_PASSWORD);
				tutor.setId(TUTOR_ID);
				
				//create text to link.
				Text text = new Text();
				text.setReviewID(TEXT_GOOD_REVIEWID);
				text.setDescription(TEXT_GOOD_DESCRIPTION);
				text.setWrittenAbout(tutor);
				text.setCourseOffering(co);
				return text;
				
			} else if(invocation.getArgument(0).equals(TEXT_BAD_REVIEWID)) {
				//Just create a text and send
				Text text = new Text();
				text.setReviewID(TEXT_BAD_REVIEWID);
				return text;
				
			} else return null;
		});
		
	}
	


//************************************************* TESTS *************************************************//

		@Test
		public void createText() {
			Text text = new Text();
		
			//Get all the ratings that have been written.
			try {
				text = service.createText(TEXT_GOOD_DESCRIPTION, true, TUTOR_USERNAME2, CO_ID2);
			} catch(IllegalArgumentException e) {fail();}
			
			assertEquals(text.getDescription(), TEXT_GOOD_DESCRIPTION);
			assertEquals(text.getWrittenAbout().getUsername(), TUTOR_USERNAME2);
			assertEquals(text.getCourseOffering().getCourseOfferingID(), CO_ID2);
		}	
	
		@Test
		public void createTextWithNonExistantTutor() {
			String error = "";
			//Get all the ratings that have been written.
			try {
				service.createText(TEXT_GOOD_DESCRIPTION, true, TUTOR_PASSWORD, CO_ID2);
			} catch(IllegalArgumentException e) {error = e.getMessage();}
			
			assertEquals(ErrorStrings.Invalid_Text_Reviewee, error);
		}	
		

		@Test
		public void createRating() {
			Rating rating = new Rating();
		
			//Get all the ratings that have been written.
			try {
				rating = service.createRating(3, TUTOR_USERNAME2, CO_ID2);
			} catch(IllegalArgumentException e) {fail();}
			
			assertEquals(rating.getRatingValue(), 3);
			assertEquals(rating.getWrittenAbout().getUsername(), TUTOR_USERNAME2);
			assertEquals(rating.getCourseOffering().getCourseOfferingID(), CO_ID2);
		}	
	
		@Test
		public void createRatingWithNonExistantTutor() {
			String error = "";
			//Get all the ratings that have been written.
			try {
				service.createRating(3, TUTOR_PASSWORD, CO_ID2);
			} catch(IllegalArgumentException e) {error = e.getMessage();}
			
			assertEquals(ErrorStrings.Invalid_Rating_Reviewee, error);
		}	
		
		
		@Test
		public void getAllRating() {
			setMockOutputRatingMakeIntoList();
			List<Rating> ratingsList = new ArrayList<>();
			
			//Get all the ratings that have been written.
			try {
				ratingsList = service.getAllRatings();
			} catch(IllegalArgumentException e) {fail();}
			
			assertEquals(ratingsList.size(), 3);
			assertEquals(ratingsList.get(0).getRatingValue(), RATING_GOOD_REVIEWID);
			assertEquals(ratingsList.get(1).getRatingValue(), RATING_BAD_TOOLARGE);
		}
		
		
		// Check to make sure all the tests are present.
		@Test
		public void getAllTextsWithoutBannedOnes() {
			setMockOutputTextMakeIntoList();
			
			List<Text> textList = new ArrayList<>();
			
			//get all the texts that have been written.
			try {
				textList = service.getAllTextsThatAreAllowed();
			} catch(IllegalArgumentException e) {fail();}
					
			assertEquals(textList.size(), 2);
			assertEquals(textList.get(0).getDescription(), TEXT_GOOD_DESCRIPTION);
			assertEquals(textList.get(0).getIsAllowed(), true);
		}
		
		// Check to make sure all the tests are present.
		@Test
		public void getAllTextsIncludingBannedOnes() {
			setMockOutputTextMakeIntoList();
			
			List<Text> textList = new ArrayList<>();
			
			//get all the texts that have been written.
			try {
				textList = service.getAllTexts();
			} catch(IllegalArgumentException e) {fail();}
					
			assertEquals(textList.size(), 5);
			assertEquals(textList.get(0).getDescription(), TEXT_GOOD_DESCRIPTION);
			assertEquals(textList.get(0).getIsAllowed(), true);
			assertEquals(textList.get(1).getDescription(), "");
		}
		
		//test if text can be found using id
		@Test
		public void getTextById() {
			setMockOutputTextMakeIntoList();
			String error = "";
			Text text = new Text();
			
			//get the right text by the id.
			try {
				text = service.getText(TEXT_GOOD_REVIEWID);
			} catch(IllegalArgumentException e) { error = e.getMessage();}
			
			assertEquals(error, "");
			assertEquals(text.getReviewID(), TEXT_GOOD_REVIEWID);
			assertEquals(text.getWrittenAbout().getUsername(), USER_USERNAME);
		}
		
		//test if rating is returned when id is used.
		@Test
		public void getRatingById() {
			setMockOutputRatingMakeIntoList();
			String error = "";
			Rating rating = new Rating();
			
			//Get a rating by a specific id.
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
			
			//Rating max is 5 - test if it is caught
			try {
				ratingsList = service.getAllRatings();
			} catch(IllegalArgumentException e) {fail();}
			
			Rating currentRating = ratingsList.get(1);
			
			assertEquals(currentRating.getRatingValue(), RATING_BAD_TOOLARGE);
			assertEquals(currentRating.getWrittenAbout().getUsername(), TUTOR_USERNAME);
			assertEquals(currentRating.getCourseOffering().getCourseOfferingID(), CO_ID);
			
			//check to make sure the appropriate error was registered.
			try {
				service.createRating(currentRating.getRatingValue(), currentRating.getWrittenAbout().getUsername(), currentRating.getCourseOffering().getCourseOfferingID());	
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

			//Create a review with a negative rating.
			try {
				ratingsList = service.getAllRatings();
			} catch(IllegalArgumentException e) {fail();}
			
			Rating currentRating = ratingsList.get(2);
			
			assertEquals(currentRating.getRatingValue(), RATING_BAD_NULL);
			assertEquals(currentRating.getWrittenAbout().getUsername(), TUTOR_USERNAME);
			assertEquals(currentRating.getCourseOffering().getCourseOfferingID(), CO_ID);
			
			//Make sure the correct error was found
			try {
				service.createRating(currentRating.getRatingValue(), currentRating.getWrittenAbout().getUsername(), currentRating.getCourseOffering().getCourseOfferingID());	
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
			
			//Check to make sure the description was not too long.
			try {
				textList = service.getAllTexts();
			} catch(IllegalArgumentException e) {fail();}
			
			Text currentText = textList.get(1);
			
			assertEquals(currentText.getDescription(), TEXT_BAD_EMPTY);
			assertEquals(currentText.getWrittenAbout().getUsername(), TUTOR_USERNAME);
			assertEquals(currentText.getCourseOffering().getCourseOfferingID(), CO_ID);
			
			//Check to see if the appropriate error was found.
			try {
				service.createText(currentText.getDescription(), currentText.getIsAllowed(), currentText.getWrittenAbout().getUsername(), currentText.getCourseOffering().getCourseOfferingID());	
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
			
			//Check if description written is empty..
			try {
				textList = service.getAllTexts();
			} catch(IllegalArgumentException e) {fail();}
			
			Text currentText = textList.get(2);
			
			assertEquals(currentText.getDescription(), TEXT_BAD_TOO_LONG);
			assertEquals(currentText.getWrittenAbout().getUsername(), TUTOR_USERNAME);
			assertEquals(currentText.getCourseOffering().getCourseOfferingID(), CO_ID);
			
			try {
				service.createText(currentText.getDescription(), currentText.getIsAllowed(), currentText.getWrittenAbout().getUsername(), currentText.getCourseOffering().getCourseOfferingID());	
			} catch(IllegalArgumentException e) {error = e.getMessage();}
			
			//Check to make sure the correect error was found.
			assertEquals(TEXT_BAD_TOO_LONG, currentText.getDescription());
			assertEquals(ErrorStrings.Invalid_Text_Description, error);
		}
		
		//Check for a non existant user reviewee
		@Test
		public void createTextNullUserreviewee() {
			setMockOutputTextMakeIntoList();
			List<Text> textList = new ArrayList<>();
			String error = "";
			Text text = new Text();
			
			//test to make sure a text can not be created unless a target reviewee existed.
			try {
				textList = service.getAllTexts();
			} catch(IllegalArgumentException e) {fail();}
					
			try {
				text = service.createText(textList.get(4).getDescription(), textList.get(4).getIsAllowed(), null, textList.get(4).getCourseOffering().getCourseOfferingID());	
			} catch(IllegalArgumentException e) {error = e.getMessage();}
							
			assertEquals(ErrorStrings.Invalid_Text_RevieweeUsername, error);
			assertEquals(text.getWrittenAbout(), null);
		}
		
		//Check for incorrect course offering id
		@Test
		public void createTextNonExistanceCourseOffering() {
			setMockOutputTextMakeIntoList();
			List<Text> textList = new ArrayList<>();
			String error = "";
							
			//Check for what happens when creating text with no course offering existing.
			try {
				textList = service.getAllTexts();
			} catch(IllegalArgumentException e) {fail();}
					
			try {
				service.createText(textList.get(0).getDescription(), textList.get(0).getIsAllowed(), textList.get(0).getWrittenAbout().getUsername(), 101);	
			} catch(IllegalArgumentException e) {error = e.getMessage();}
							
			assertEquals(ErrorStrings.Invalid_Text_FindCourseOffering, error);
		}
		
		//Check for a non existent user reviewer
		@Test
		public void createRatingNullUserreviewee() {
			setMockOutputRatingMakeIntoList();
			List<Rating> ratingList = new ArrayList<>();
			String error = "";
			Rating rating = new Rating();
							
			//create a rating with no user attached
			try {
				ratingList = service.getAllRatings();
			} catch(IllegalArgumentException e) {fail();}
					
			try {
				rating = service.createRating(ratingList.get(0).getRatingValue(), null, ratingList.get(0).getCourseOffering().getCourseOfferingID());	
			} catch(IllegalArgumentException e) {error = e.getMessage();}
							
			//Tests check to make sure the user error was sent and that nothing was created.
			assertEquals(ErrorStrings.Invalid_Text_RevieweeUsername, error);
			assertEquals(rating.getWrittenAbout(), null);
		}
				
		//Check for incorrect course offering id
		@Test
		public void createReviewNonExistanceCourseOffering() {
			setMockOutputRatingMakeIntoList();
			List<Rating> ratingList = new ArrayList<>();
			String error = "";
		
			//Create a rating with the incorrect course id.
			try {
				ratingList = service.getAllRatings();
			} catch(IllegalArgumentException e) {fail();}
			try {
				//Check for error -> no course id.
				service.createRating(ratingList.get(0).getRatingValue(), ratingList.get(0).getWrittenAbout().getUsername(), 199);	
			} catch(IllegalArgumentException e) {error = e.getMessage();}
									
			assertEquals(ErrorStrings.Invalid_Rating_FindCourseOffering, error);
		}
		
		//Get all the reviews that are written about a given course offering.
		@Test
		public void getListOfTextsByCourseOfferingId() {
			
			//Set up lists
			Set<Review> reviewList = new HashSet<>();
			String error = "";
			
			List<CourseOffering> courseOffering = service.getAllCourseOfferings();
			
			assertEquals(courseOffering.size(), 2);
			
			int courseOfferingId = courseOffering.get(1).getCourseOfferingID();
			
			assertEquals(2, courseOfferingId);
			
			CourseOffering courseoffering = courseOffering.get(0);
			
			try {
				reviewList = service.getAllReviewsByCO(courseoffering.getCourseOfferingID());
			} catch(IllegalArgumentException e) {error = e.getMessage();}
			
			//Check if the call returned a list and the size.
			assertEquals(error, "");
			assertEquals(null, reviewList);
		
		}
		
		//Get all the reviews that are written about a given tutoring.
		@Test
		public void getListOfReviewByTutorName() {
			
			//Create the review set
			Set<Review> reviewList = new HashSet<>();
			String error = "";
				
			List<Tutor> tutorList = service.getAllTutors();
					
			//Preliminary tests
			assertEquals(tutorList.size(), 2);
			assertEquals(tutorList.get(1).getUsername(), USER_USERNAME2);

			@SuppressWarnings("unused")
			Tutor tutor = tutorList.get(1);
			
			try {
				reviewList = service.getAllReviewsByTutor(tutorList.get(1).getUsername());
			} catch(IllegalArgumentException e) {error = e.getMessage();}
			
			//After tests
			assertEquals(error, "");
			assertEquals(null, reviewList);
		}
		
		//Attempt to get writer of the review - no method exists - this test is just used to prove that no matter what - cannot access user.
		@Test
		public void getAuthorOfTheReview() {
			
			//Create the review set
			Review review = new Text();
			String error = "";
			
			try {
				service.getReviewer(review);
			} catch(IllegalArgumentException e) {error = e.getMessage();}
				
			assertEquals(error, ErrorStrings.Invalid_Review_CANTRETURN);
		}	
		
		//Get all reviews with null tutor.
		@Test
		public void getAllReviewsWithNullUser() {
			
			String error = "";
			
			try {
				service.getAllReviewsByTutor(TUTOR_PASSWORD2);
			} catch(IllegalArgumentException e) {error = e.getMessage();}
				
			assertEquals(error, ErrorStrings.Invalid_DTO_Tutor);
		}	
		
		//Get all reviews with null co.
		@Test
		public void getAllReviewsWithNullCourseOffering() {
					
			String error = "";
					
			try {
				service.getAllReviewsByCO(-1);
			} catch(IllegalArgumentException e) {error = e.getMessage();}
						
			assertEquals(error, ErrorStrings.Invalid_DTO_CourseOffering);
		}                    
}

	