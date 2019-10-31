package ca.mcgill.ecse321.project;

public class ErrorStrings {
    
	public static String Invalid_NULL = null;
	public static String Invalid_EMPTY = "";
	
//============ AVAILABILITY ===========\\
	public static String Invalid_Availability_Name = "Invalid name...";
    public static String Invalid_Availability_Date = "Invalid date parameters...";
    public static String Invalid_Availability_Time = "Invalid time parameters...";
    public static String Invalid_Availability_Null = "Invalid availability ID, the availability does not exist...";
    public static String Invalid_Availability_Tutor = "Please specify a valid Tutor";
    public static String Invalid_Availability_ID_Get = "Incorrect id value for the availability...";
    public static String Invalid_Availability_ID_Delete = "You are searching for an invalid id...";
    

//=========== COURSEOFFERING ===========\\
    public static String Invalid_CourseOffering_Year = "That is far too long ago...";
    public static String Invalid_CourseOffering_CourseID = "Invalid courseID...";
    public static String Invalid_CourseOffering_Term = "Invalid term choice...";
    public static String Invalid_CourseOffering_CantFindCourseOffering = "Please specify a valid course offering...";
    public static String Invalid_CourseOffering_ID = "Incorrect id value for the course offering...";
    
    
//=============== COURSE ================\\
    public static String Invalid_Course_CourseName = "Please insert a course name to search...";
    public static String Invalid_Course_Description = "Please insert a brief description...";
    public static String Invalid_Course_FindUniversityWithID = "Please specify a valid University";
    public static String Invalid_Course_FindCourseWithID = "Please specify a valid Course";
    public static String Invalid_Course_ID = "Incorrect id value for the course search...";
    
    
//================ TEXT ===================\\
    public static String Invalid_Text_RevieweeUsername = "Please insert a reviewee username...";
    public static String Invalid_Text_Description = "Please insert a brief description...";
    public static String Invalid_Text_FindCourseOffering = "Please enter a valid Course Offering";
    public static String Invalid_Text_Reviewee = "Please enter a valid Reviewee";
    public static String Invalid_Text_FindTextReview = "Please enter a valid Text Review to update";
    public static String Invalid_Text_ID = "Incorrect id value for the text request...";
    public static String Invalid_Review_CANTRETURN = "You do not have permission to access the author of the review.";
    
//================ RATING ===================\\
    public static String Invalid_Rating_RevieweeUsername = "Please insert a reviewee username...";
    public static String Invalid_Rating_Reviewee = "Please enter a valid username for the tutor...";
    public static String Invalid_Rating_NegativeRatingValue = "You can't give your tutor a negative rating... [1,5]";
    public static String Invalid_Rating_FindCourseOffering = "Please enter a valid course offering...";
    public static String Invalid_Rating_FindRatingByReview = "Please enter a valid Rating Review to update";
    public static String Invalid_Rating_CourseOfferingID = "Please enter a valid Course Offering";
    public static String Invalid_Rating_IncorrectRatingValue = "Rating value must be between 1 and 5";
    public static String Invalid_Rating_ID = "Incorrect id value for the rating...";
    public static String Invalid_Rating_CourseOffering = "";

//================ TUTOR =====================\\  
    public static String Invalid_Tutor_Username = "Please insert a username...";
    public static String Invalid_Tutor_Password = "Please insert a password...";
    public static String Invalid_Tutor_EmailCheck = "Please insert a proper email...";
    public static String Invalid_Tutor_NegativeHourlyRate = "Don't think you want to pay the student for your session...";
    public static String Invalid_Tutor_NegativeExperience = "Please input a valid number of years for your experience...";
    public static String Invalid_Tutor_NullLevel = "Please provide your education level...";
    public static String Invalid_Tutor_FindUserError = "Please input a valid user";
    public static String Invalid_Tutor_OldUsername = "Please insert a proper username...";
    public static String Invalid_Tutor_FindByTutorError = "Please input a valid tutor to update";
    
//================ STUDENT ===================\\
    public static String Invalid_Student_Username = "Please insert a username...";
    public static String Invalid_Student_Password = "Please insert a password...";
    public static String Invalid_Student_UserEmail = "Please insert a proper email...";
    public static String Invalid_Student_FindUserByEmail = "Please input a valid user";
    public static String Invalid_Student_FindStudentByUsername = "Please input a valid student";
    
    
//================ SESSION ===================\\
    public static String Invalid_Session_StudentName = "Invalid student name...";
    public static String Invalid_Session_TutorName = "Invalid tutor name...";
    public static String Invalid_Session_DateTime = "Invalid time parameters...";
    public static String Invalid_Session_NegativeAmountPaid = "So your student is paying you?? Please provide positive amount paid...";
    public static String Invalid_Session_FindCourseOfferingByID = "Please input a valid course offering";
    public static String Invalid_Session_FindTutorByUsername = "Please input a valid tutor";
    public static String Invalid_Session_FindStudentByUsername = "Please input a valid student";
    public static String Invalid_Session_FindSessionByID = "Please input a valid session";
    public static String Invalid_Session_ID = "Incorrect id value for the session...";
    
//================= UNIVERSITY =================\\
    public static String Invalid_University_Name = "Invalid name...";
    public static String Invalid_University_Address = "Invalid address...";
    public static String Invalid_University_ID = "You are searching for an invalid id...";
    public static String Invalid_University_FindCourse = "No courses offered for this university";
    
//================= USER =====================\\
    public static String Invalid_User_AgeTooYoung = "Must be above the age of 12 for this tutoring service...";
    public static String Invalid_User_Name = "Invalid name...";
    public static String Invalid_User_Email = "Please insert a proper email...";
    public static String Invalid_User_PhoneNumber = "Invalid phone number...";
    
//================== ROOM ====================\\
    public static String Invalid_Room_NegativeNumber = "Room number cannot be negative";
    
//================== CONVERTTODTO ===================\\
    public static String Invalid_DTO_CourseOffering = "There is no such CourseOffering!";
    public static String Invalid_DTO_Course = "There is no such Course!";
    public static String Invalid_DTO_University = "There is no such University!";
    public static String Invalid_DTO_Rating = "There is no such rating!";
    public static String Invalid_DTO_Text = "There is no such text!";
    public static String Invalid_DTO_Session = "There is no such session!";
    public static String Invalid_DTO_Tutor = "There is no such tutor!";
    public static String Invalid_DTO_Student = "There is no such student!";
    public static String Invalid_DTO_Room = "There is no such room!";
    public static String Invalid_DTO_User = "Thre is no such user!";
    public static String Invalid_DTO_Availability = "There is no such availability!";
    public static String Invalid_DTO_Review = "There is no such review!";
    public static String Invalid_DTO_Role = "There is no such role!";
    
//=================== SERVICE ERRORS ======================\\
    public static String Invalid_Service_Tutor = "No tutor by that username";
    public static String Invalid_Service_TutorForCO = "No tutors for this course offering";
    public static String Invalid_Service_CO = "No course offerings offered for this course";
    public static String Invalid_Service_CONone = "No courses offerings offered yet";
    public static String Invalid_Service_CourseOfferedUni = "No courses offered for this university";
    public static String Invalid_Service_COBad = "This course offering does not exist";
    public static String Invalid_Service_Student = "No student by that username";



}
