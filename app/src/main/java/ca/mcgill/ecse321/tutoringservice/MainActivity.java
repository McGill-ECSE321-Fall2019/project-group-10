package ca.mcgill.ecse321.tutoringservice;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/*
 * This class describes the activity of the app page. Note that the class is rather large,
 * however all the methods are coherent and clearly belong together as the all describe the
 * key functionalities of our application. Also note that the Javdoc comments take up 
 * approximately 300 extra lines. 
 */
public class MainActivity extends AppCompatActivity {

    private String error = null;

    // booleans for book session page dropdown lists
    private boolean createUni = true;
    private boolean createCourse = true;
    private boolean createCourseOffering = true;
    private boolean createTutor = true;
    private boolean createAvailability = true;
    private boolean createSession = true;

    // keep track of the selected items for session creation
    private String selectedUni = "";
    private String courseString = "";
    private String idAvail = "";
    private String selectedCourseOfferingId = "";
    private String selectedTutor = "";
    private String selectedTutorHR = "";
    private String selectedAvailabilityDate = "";
    private String selectedAvailabilityTime = "";
    private String currentlySelectedUsername = "";

    // objects for book a session page dropdown lists
    private List<String> universityNames = new ArrayList<>();
    private ArrayAdapter<String> universityAdapter;

    private List<String> courseNames = new ArrayList<>();
    private ArrayAdapter<String> courseAdapter;

    private List<String> courseOfferingNames = new ArrayList<>();
    private ArrayAdapter<String> courseOfferingAdapter;

    private List<String> tutorNames = new ArrayList<>();
    private ArrayAdapter<String> tutorAdapter;

    private List<String> availabilityNames = new ArrayList<>();
    private ArrayAdapter<String> availabilityAdapter;

    // object setup for dashboard session dropdown list
    private List<String> sessionNames = new ArrayList<>();
    private ArrayAdapter<String> sessionAdapter;

    /**
     * This method defines the app behavior as a part of the Android Lifecycle. This method will be
     * called when the system first creates the activity. Upon creation of this activity, 
     * the activity will enter the created state. This method should only be called once 
     * throughout the activities lifecycle. 
     * @param savedInstanceState This object contains the application's previously saved state in the 
     *                           form of a Bundle object. This parameter is used to load the previous 
     *                           saved state into the program. Note that the object is null if there
     *                           is no saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createSession = true;
        setContentView(R.layout.activity_main);
    }
    
    
    /**
     * This method connects two pages of the app. The user has the chance to create an account. 
     * Immediately following their account creation, they are taken to the login page to
     * login to their new account. 
     * @param v A View object that represents a component of the UI. This is the component 
     *          that was interacted with that caused this method to be called.
     */
    public void goToLoginFromRegister(View v) {
        error = "";
        
        //Transfer all the elements from the view to variables to use 
        //later in the program, for method calls and to determine if 
        //an error message needs to be thrown
        final TextView email = (TextView) findViewById(R.id.signupEmail);
        final TextView username = (TextView) findViewById(R.id.signupUser);
        final TextView password = (TextView) findViewById(R.id.signupPassword);
        final TextView name = (TextView) findViewById(R.id.signupName);
        final TextView age = (TextView) findViewById(R.id.signupAge);
        final TextView phoneNumber = (TextView) findViewById(R.id.signupPhoneNumber);
        
        this.currentlySelectedUsername = username.getText().toString();
        
        if (email.getText().toString().matches("") || username.getText().toString().matches("")
                || password.getText().toString().matches("") || name.getText().toString().matches("")
                || age.getText().toString().matches("") || phoneNumber.getText().toString().matches(""))
        {
            //throw an error if the user attempts to login without filling out the appropriate fields.
        	error = "Please fill out all fields.";
            refreshErrorMessage();
            return;
        }
        
        Integer ageInt = Integer.parseInt(age.getText().toString());
        //configure the relative URL with the parameters specified above
        //Note that this ensures that the RequestParams Object can be set to
        //the empty object. 
        HttpsUtils.post("/createuser2?userName=" + username.getText().toString() +
                "&userPassword=" + password.getText().toString() +
                "&userEmail=" + email.getText().toString() +
                "&age=" + ageInt +
                "&phoneNum=" + phoneNumber.getText().toString() +
                "&name=" + name.getText().toString(), new RequestParams(), new JsonHttpResponseHandler() {
        	
        	/**
             * This method defines the behavior of the successful completion of the HTTP request. 
             * @param statusCode The status code of the response
             * @param headers    The list of headers that are returned, if they exist
             * @param response   A JSONObject that represents the HTTP response data  
             */
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                setContentView(R.layout.login_page);
            }
            
            /**
             * This method defines the behavior of the erroneous completion of the HTTP request. 
             * @param statusCode    The status code of the response, which will indicate what went wrong
             * @param headers       The list of headers that are returned, if they exist
             * @param throwable     A Throwable object that describes the underlying cause of the error
             * @param errorResponse A JSONObject that represents the HTTP response data  
             */
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

                email.setText("");
                username.setText("");
                password.setText("");
                age.setText("");
                phoneNumber.setText("");
                name.setText("");

                try {
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
            }
        });
    }
    
    /**
     * This method controls the direction from the login page to the student Dashboard. This 
     * is important as it is controls the direction from one page to another and ensures that
     * login security is maintained.
     * @param v A View object that represents a component of the UI. This is the component 
     *          that was interacted with that caused this method to be called.
     */
    public void loginUser(View v) {

        error = "";
        final TextView username = (TextView) findViewById(R.id.loginusername);
        final TextView password = (TextView) findViewById(R.id.loginpassword);
        
        if (username.getText().toString().matches("") || password.getText().toString().matches(""))
        {
        	//throw an error if the user attempts to login without filling out the appropriate fields.
        	error = "Please fill out all fields.";
            refreshErrorMessage();
            return;
        }
        
        HttpsUtils.post("/login?username=" + username.getText().toString() + "&password="
                + password.getText().toString(), new RequestParams(), new JsonHttpResponseHandler() {
        	
        	/**
             * This method defines the behavior of the successful completion of the HTTP request. 
             * @param statusCode The status code of the response
             * @param headers    The list of headers that are returned, if they exist
             * @param response   The string that describes the server's response, when no error 
             *                   is thrown.  
             */
            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {
                currentlySelectedUsername = username.getText().toString();
                goToDashboard();
            }
            
            /**
             * This method defines the behavior of the erroneous completion of the HTTP request. 
             * @param statusCode     The status code of the response, which will indicate what went wrong
             * @param headers        The list of headers that are returned, if they exist
             * @param responseString The response String that describes the return value of the HTTP request
             *                       that was sent when an error occurs 
             * @param throwable      A Throwable object that describes the underlying cause of the error 
             */
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                
            	//Check if the Username and Password match and ignore the error
            	//as they exist in the database
                if(responseString.equals("true")){
                    createSession = true;
                    currentlySelectedUsername = username.getText().toString();
                    goToDashboard();
                }
                //if the Username and password do not match, do not allow login
                else if(responseString.equals("false")) {
                    username.setText("");
                    password.setText("");
                    error = "Incorrect information";
                    refreshErrorMessage();
                }
                //if there is an unspecified response - we do not know what the error was
                else{
                    username.setText("");
                    password.setText("");
                    error += responseString;
                    refreshErrorMessage();
                }
            }
        });
    }
    
    /**
     * This method loads the student Dashboard after completing the login
     * operation or the book session operation. 
     */
    public void goToDashboard(){
        setContentView(R.layout.dashboard_page);

        //resetting all the variables to ensure they are in the correct
        //state for calling the book session page
        createUni = true;
        createCourse = true;
        createCourseOffering = true;
        createTutor = true;
        createAvailability = true;

        //view available session for the student
        Spinner sessionSpinner = (Spinner) findViewById(R.id.session_spinner);
        sessionAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sessionNames);
        sessionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sessionSpinner.setAdapter(sessionAdapter);
        
        //Once a session was selected, the app automatically generates spinners
        sessionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            
        	/**
             * This method defines the behavior of the selection box, and describes the full 
             * extent of the operations of this method.  
             * @param parentView       The AdapterView where the selection happened 
             * @param selectedItemView The View within the adapter view that was clicked
             * @param position         The position of the view in the adapter 
             * @param id               The id of the row that was selected  
             */
        	@Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // listener code is run twice - once at creation and once when selection is made
                // ensures that the selection is not requested upon creation
                if(createSession == true){
                    createSession = false;
                }
                else {
                    Object sessionID = parentView.getItemAtPosition(position);
                    String selectedSessionID = sessionID.toString();
                    refreshSessionDashboard(selectedSessionID);
                }
            }
        	
        	/**
             * This method defines the behavior when nothing is selected. Note that
             * this method body is empty to signify that nothing is done, when nothing
             * is selected. 
             * @param parentView The AdapterView that now contains no selected item
             */
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });
        
        refreshList(sessionAdapter, sessionNames,
                "/sessionbystudent?student_name="+currentlySelectedUsername, "sessionid");
    }
    
    /**
     * This method describes the behavior when a session is selected, and the 
     * student Dashboard needs to be updated with the relevant information. This
     * method displays the information about the selected session (identified by the 
     * sessionId) to the screen.
     * @param sessionId The Id of the session about which to display more information
     */
    public void refreshSessionDashboard(String sessionId){
        
        final TextView date = (TextView) findViewById(R.id.date);
        final TextView time = (TextView) findViewById(R.id.time);
        final TextView tutor = (TextView) findViewById(R.id.tutor);
        final TextView course = (TextView) findViewById(R.id.course);
        final TextView courseOffering = (TextView) findViewById(R.id.courseOffering);
        
        HttpsUtils.get("/session?session_id="+sessionId, new RequestParams(), new JsonHttpResponseHandler() {
        	
        	/**
             * This method defines the behavior of the successful completion of the HTTP request. 
             * @param statusCode The status code of the response
             * @param headers    The list of headers that are returned, if they exist
             * @param response   A JSONObject that represents the HTTP response data  
             */
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                	
                    date.setText(response.getString("date"));
                    time.setText(response.getString("time"));
                    tutor.setText(response.getJSONObject("tutorDTO").getString("username"));
                    String coInfo = response.getJSONObject("courseOfferingDTO").getString("courseName");
                    coInfo = coInfo.concat(" ");
                    coInfo = coInfo.concat(response.getJSONObject("courseOfferingDTO").getString("term"));
                    coInfo = coInfo.concat(" ");
                    coInfo = coInfo.concat(response.getJSONObject("courseOfferingDTO").getString("year"));
                    course.setText(coInfo);
                    courseOffering.setText(response.getString("amountPaid"));

                } catch (Exception e) {
                    error += e.getMessage();
                    date.setText("");
                    time.setText("");
                    tutor.setText("");
                    course.setText("");
                    courseOffering.setText("");
                    refreshErrorMessage();
                }

            }
            
            /**
             * This method defines the behavior of the erroneous completion of the HTTP request. 
             * @param statusCode    The status code of the response, which will indicate what went wrong
             * @param headers       The list of headers that are returned, if they exist
             * @param throwable     A Throwable object that describes the underlying cause of the error
             * @param errorResponse A JSONObject that represents the HTTP response data  
             */
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
                //no matter what is done, we will need to refresh the error messages.
                refreshErrorMessage();
            }
        });

    }
    
    /**
     * This method controls the behavior between going from the student Dashboard
     * to the book session views. This is called when the Book Session button is 
     * pressed on the Android front end.  
     * @param v A View object that represents a component of the UI. This is the component 
     *          that was interacted with that caused this method to be called.
     */
    public void goToSessionPage(View v){
        setContentView(R.layout.booksession_page);
        
        //these select lists are required to get the information to book a session
        Spinner uniSpinner = (Spinner) findViewById(R.id.uni_spinner);
        Spinner courseSpinner = (Spinner) findViewById(R.id.course_spinner);
        Spinner courseOfferingSpinner = (Spinner) findViewById(R.id.courseoffering_spinner);
        Spinner tutorSpinner = (Spinner) findViewById(R.id.tutor_spinner);
        Spinner availabilitySpinner = (Spinner) findViewById(R.id.availability_spinner);
        
        universityAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, universityNames);
        universityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        uniSpinner.setAdapter(universityAdapter);

        courseAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, courseNames);
        courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseSpinner.setAdapter(courseAdapter);

        courseOfferingAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, courseOfferingNames);
        courseOfferingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseOfferingSpinner.setAdapter(courseOfferingAdapter);

        tutorAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tutorNames);
        tutorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tutorSpinner.setAdapter(tutorAdapter);

        availabilityAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, availabilityNames);
        availabilityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        availabilitySpinner.setAdapter(availabilityAdapter);

        uniSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            
        	/**
             * This method defines the behavior of the selection box, and describes the full 
             * extent of the operations of this method.  
             * @param parentView       The AdapterView where the selection happened 
             * @param selectedItemView The View within the adapter view that was clicked
             * @param position         The position of the view in the adapter 
             * @param id               The id of the row that was selected  
             */
        	@Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // listener code is run twice - once at creation and once when selection is made
                // ensures that the selection is not requested upon creation
                if(createUni == true){
                    createUni = false;
                }
                else {
                    Object uni = parentView.getItemAtPosition(position);
                    selectedUni = uni.toString();
                    refreshList(courseAdapter, courseNames, "/universities/" + selectedUni, "courseName");
                }
            }
        	
        	/**
             * This method defines the behavior when nothing is selected. Note that
             * this method body is empty to signify that nothing is done, when nothing
             * is selected. 
             * @param parentView The AdapterView that now contains no selected item
             */
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}

        });

        courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            
        	/**
             * This method defines the behavior of the selection box, and describes the full 
             * extent of the operations of this method.  
             * @param parentView       The AdapterView where the selection happened 
             * @param selectedItemView The View within the adapter view that was clicked
             * @param position         The position of the view in the adapter 
             * @param id               The id of the row that was selected  
             */
        	@Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // listener code is run twice - once at creation and once when selection is made
                // ensures that the selection is not requested upon creation
                if(createCourse == true){
                    createCourse = false;
                }
                else {
                    Object course = parentView.getItemAtPosition(position);
                    courseString = course.toString();
                    refreshCourseOfferingList(courseOfferingAdapter, courseOfferingNames, courseString, selectedUni);
                }
            }
            
            /**
             * This method defines the behavior when nothing is selected. Note that
             * this method body is empty to signify that nothing is done, when nothing
             * is selected. 
             * @param parentView The AdapterView that now contains no selected item
             */
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}

        });

        courseOfferingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            
        	/**
             * This method defines the behavior of the selection box, and describes the full 
             * extent of the operations of this method.  
             * @param parentView       The AdapterView where the selection happened 
             * @param selectedItemView The View within the adapter view that was clicked
             * @param position         The position of the view in the adapter 
             * @param id               The id of the row that was selected  
             */
        	@Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // listener code is run twice - once at creation and once when selection is made
                // ensures that the selection is not requested upon creation
                if(createCourseOffering == true){
                    createCourseOffering = false;
                }
                else {
                    String courseOffering = parentView.getItemAtPosition(position).toString();
                    String[] sp = courseOffering.split(" ");
                    selectedCourseOfferingId = sp[3];
                    refreshTutorList(tutorAdapter, tutorNames, selectedCourseOfferingId);
                }
            }
            
            /**
             * This method defines the behavior when nothing is selected. Note that
             * this method body is empty to signify that nothing is done, when nothing
             * is selected. 
             * @param parentView The AdapterView that now contains no selected item
             */
            @Override
            public void onNothingSelected(AdapterView<?> parentView) { }

        });

        tutorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            
        	/**
             * This method defines the behavior of the selection box, and describes the full 
             * extent of the operations of this method.  
             * @param parentView       The AdapterView where the selection happened 
             * @param selectedItemView The View within the adapter view that was clicked
             * @param position         The position of the view in the adapter 
             * @param id               The id of the row that was selected  
             */
        	@Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // listener code is run twice - once at creation and once when selection is made
                // ensures that the selection is not requested upon creation
                if(createTutor == true){
                    createTutor = false;
                }
                else {
                    String username = parentView.getItemAtPosition(position).toString();
                    String[] sp = username.split(" ");
                    selectedTutor = sp[0];
                    int startPos = 6;
                    int endPos = sp[1].indexOf('/');
                    StringBuilder sb = new StringBuilder();
                    for(int i=startPos; i < endPos; i++){
                        char nextDigit = sp[1].charAt(i);
                        sb.append(nextDigit);
                    }
                    selectedTutorHR = sb.toString();
                    
                    refreshAvailabilityList(availabilityAdapter, availabilityNames, selectedTutor);
                }
            }
            
            /**
             * This method defines the behavior when nothing is selected. Note that
             * this method body is empty to signify that nothing is done, when nothing
             * is selected. 
             * @param parentView The AdapterView that now contains no selected item
             */
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}

        });

        availabilitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        	/**
             * This method defines the behavior of the selection box, and describes the full 
             * extent of the operations of this method.  
             * @param parentView       The AdapterView where the selection happened 
             * @param selectedItemView The View within the adapter view that was clicked
             * @param position         The position of the view in the adapter 
             * @param id               The id of the row that was selected  
             */
        	@Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // listener code is run twice - once at creation and once when selection is made
                // ensures that the selection is not requested upon creation
                if(createAvailability == true){
                    createAvailability = false;
                }
                else {
                    idAvail = parentView.getItemAtPosition(position).toString();
                    String[] sp = idAvail.split(" ");
                    selectedAvailabilityDate = sp[0];
                    selectedAvailabilityTime = sp[1];
                }
            }
            
            /**
             * This method defines the behavior when nothing is selected. Note that
             * this method body is empty to signify that nothing is done, when nothing
             * is selected. 
             * @param parentView The AdapterView that now contains no selected item
             */
            @Override
            public void onNothingSelected(AdapterView<?> parentView) { }

        });

        // university doesn't depend on anything else so we can refresh the list upon page creation
        refreshList(universityAdapter, universityNames, "/universities", "name");
    }

    // called when the SUBMIT button is pressed on the session creation page
    /**
     * This method defines the behavior of the app after the session creation view
     * is running and the SUBMIT button is pressed. This will define the actually
     * creation of the session relative to the view.
     * @param v A View object that represents a component of the UI. This is the component 
     *          that was interacted with that caused this method to be called.
     */
    public void createSession(View v){
    	
        if(this.selectedTutor == "" || this.selectedTutor == null || this.currentlySelectedUsername == "" || this.currentlySelectedUsername == null || this.selectedAvailabilityDate == "" || this.selectedAvailabilityDate == null || this.selectedAvailabilityTime == "" || this.selectedAvailabilityTime == null|| this.selectedCourseOfferingId == "" || this.selectedCourseOfferingId == null || this.selectedTutorHR == "" || this.selectedTutorHR == null){
        	
        	//throw an error if you some any of the fields are not filled in
            error = "Please select all fields before creating a session!";
            refreshErrorMessage();
        }
        else {
            HttpsUtils.post("/session?tutor_name=" + this.selectedTutor + "&student_name=" + this.currentlySelectedUsername
                    + "&booking_date=" + this.selectedAvailabilityDate + "&booking_time=" + this.selectedAvailabilityTime
                    + "&course_offering_id=" + this.selectedCourseOfferingId + "&amount_paid=" + this.selectedTutorHR, new RequestParams(), new JsonHttpResponseHandler() {

            	/**
                 * This method defines the behavior of the successful completion of the HTTP request. 
                 * @param statusCode The status code of the response
                 * @param headers    The list of headers that are returned, if they exist
                 * @param response   A JSONObject that represents the HTTP response data  
                 */
            	@Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            		
                    createSession = true;
                    
                    //after the successful creation of a valid session
                    //reset all the storage variables to empty
                    selectedCourseOfferingId = "";
                    selectedTutor = "";
                    selectedTutorHR = "";
                    selectedAvailabilityDate = "";
                    selectedAvailabilityTime = "";
                    goToDashboard();
                }
            	
            	/**
                 * This method defines the behavior of the erroneous completion of the HTTP request. 
                 * @param statusCode    The status code of the response, which will indicate what went wrong
                 * @param headers       The list of headers that are returned, if they exist
                 * @param throwable     A Throwable object that describes the underlying cause of the error
                 * @param errorResponse A JSONObject that represents the HTTP response data  
                 */
                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    try {
                        error += errorResponse.get("message").toString();
                    } catch (JSONException e) {
                        error += e.getMessage();
                    }
                    refreshErrorMessage();
                }
            });
        }
    }

    /**
     * This method controls the view behavior when the logout button is pressed.
     * This method brings the view back to the main view point
     * @param v A View object that represents a component of the UI. This is the component 
     *          that was interacted with that caused this method to be called.
     */
    public void logout(View v){
        HttpsUtils.post("/logout?username=" + this.currentlySelectedUsername, new RequestParams(), new JsonHttpResponseHandler() {

        	/**
             * This method defines the behavior of the successful completion of the HTTP request. 
             * @param statusCode The status code of the response
             * @param headers    The list of headers that are returned, if they exist
             * @param responseString   The string that describes the server's response, when no error 
             *                   is thrown.  
             */
        	@Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                // reset the dropdown boolean
                createSession = true;
                setContentView(R.layout.activity_main);
            }
        	
        	/**
             * This method defines the behavior of the erroneous completion of the HTTP request. 
             * @param statusCode     The status code of the response, which will indicate what went wrong
             * @param headers        The list of headers that are returned, if they exist
             * @param responseString The response String that describes the return value of the HTTP request
             *                       that was sent when an error occurs 
             * @param throwable      A Throwable object that describes the underlying cause of the error 
             */
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                error = responseString;
                //if there is no responseString -> then the situation is still valid
                if(responseString == "" || responseString.isEmpty()){
                    createSession = true;
                    currentlySelectedUsername = "";
                    setContentView(R.layout.activity_main);
                }
            }
        });
    }
    
    /**
     * Direct the current view to the signup page view. This can be called after starting 
     * to register a user to the system.
     * @param v A View object that represents a component of the UI. This is the component 
     *          that was interacted with that caused this method to be called.
     */
    public void goToSignUp(View v){ setContentView(R.layout.signup_page); }

    // called from the startup page, go to the login page
    /**
     * Direct the current view to the login view. This can be called after starting 
     * to login the user to the system.
     * @param v A View object that represents a component of the UI. This is the component 
     *          that was interacted with that caused this method to be called.
     */
    public void goToLogin(View v){ setContentView(R.layout.login_page); }

    /**
     * Direct the current view to the activity main page. This is often called when the user
     * wishes to return to the home page.
     * @param v A View object that represents a component of the UI. This is the component 
     *          that was interacted with that caused this method to be called.
     */
    public void goBack(View v){
        setContentView(R.layout.activity_main);
    }
    
    /**
     * This method defines the behavior that refreshes all the Error Messages on 
     * the view. These messages must likely describe faulty user input when they 
     * are transfered to the UI.
     */
    private void refreshErrorMessage() {
    	
        TextView tvError = (TextView) findViewById(R.id.error);
        tvError.setText(error);

        if (error == null || error.length() == 0) {
            tvError.setVisibility(View.GONE);
        } else {
            tvError.setVisibility(View.VISIBLE);
        }
    }
    
    
    /**
     * This method controls the update and refresh behavior of the List according to the 
     * Parameters passed in. Specifically, it updates the adapter with the information 
     * from the names string (that is fetched from the database using the restFunctionName
     * endpoint that is provided) with an id for each name corresponding to the property
     * identifier of the JSON object.
     * @param adapter          The Array Adapter that references the list to be updated
     * @param names            The names of the current list being displayed, to be updated
     *                         to display the identifier property of each JSON Object
     * @param restFunctionName The name of the relative (or endpoint) URL to pass into the 
     *                         database call to guarantee the correct information is fetched.
     * @param identifier       The property of each JSON Object returned by the database that 
     *                         will serve as its list identifier in the updated list.
     */
    private void refreshList(final ArrayAdapter<String> adapter, final List<String> names,
                             final String restFunctionName, final String identifier) {
        String fcn = restFunctionName;
        HttpsUtils.get(restFunctionName, new RequestParams(), new JsonHttpResponseHandler() {
        	
        	/**
             * This method defines the behavior of the successful completion of the HTTP request. 
             * @param statusCode The status code of the response
             * @param headers    The list of headers that are returned, if they exist
             * @param response   An array of JSON objects that represents the HTTP response data  
             */
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
            	
                names.clear();
                names.add("Please select...");
                for( int i = 0; i < response.length(); i++){
                    try {
                        names.add(response.getJSONObject(i).getString(identifier));
                    } catch (Exception e) {
                        error += e.getMessage();
                    }
                }
                adapter.notifyDataSetChanged();
            }
            
            /**
             * This method defines the behavior of the erroneous completion of the HTTP request. 
             * @param statusCode    The status code of the response, which will indicate what went wrong
             * @param headers       The list of headers that are returned, if they exist
             * @param throwable     A Throwable object that describes the underlying cause of the error
             * @param errorResponse A JSONObject that represents the HTTP response data  
             */
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
            }
        });
    }
    
    /**
     * This method defines the behavior for refreshing a list of course offerings. Note that it is 
     * very similar to the refreshList method, but must be defined separately as it requires multiple
     * items to be displayed.
     * @param adapter    The Array Adapter that references the list to be updated
     * @param names      The names of the current list being displayed, to be updated
     *                   to display the identifier property of each JSON Object
     * @param courseName The course name to which the course offerings to display belong
     * @param uniName    The university to which the course offerings to display belong
     */
    private void refreshCourseOfferingList(final ArrayAdapter<String> adapter, final List<String> names,
                             final String courseName, final String uniName) {
        HttpsUtils.get("/courses/"+uniName+"/"+courseName, new RequestParams(), new JsonHttpResponseHandler() {
        	
        	/**
             * This method defines the behavior of the successful completion of the HTTP request. 
             * @param statusCode The status code of the response
             * @param headers    The list of headers that are returned, if they exist
             * @param response   An array of JSON objects that represents the HTTP response data  
             */
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
            	
                names.clear();
                names.add("Please select...");
                for( int i = 0; i < response.length(); i++){
                    try {
                        String courseIdentifier = response.getJSONObject(i).getString("term");
                        courseIdentifier = courseIdentifier + " ";
                        courseIdentifier = courseIdentifier.concat(response.getJSONObject(i).getString("year"));
                        courseIdentifier = courseIdentifier + " id: ";
                        courseIdentifier = courseIdentifier.concat(response.getJSONObject(i).getString("id"));
                        names.add(courseIdentifier);
                    } catch (Exception e) {
                        error += e.getMessage();
                    }
                }
                adapter.notifyDataSetChanged();
            }
            
            /**
             * This method defines the behavior of the erroneous completion of the HTTP request. 
             * @param statusCode    The status code of the response, which will indicate what went wrong
             * @param headers       The list of headers that are returned, if they exist
             * @param throwable     A Throwable object that describes the underlying cause of the error
             * @param errorResponse A JSONObject that represents the HTTP response data  
             */
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
            }
        });
    }

    //Tutor needs its own refresh method since it requires multiple items to be displayed
    /**
     * This method defines the behavior for refreshing a list of tutors. Note that it is 
     * very similar to the refreshList method, but must be defined separately as it requires multiple
     * items to be displayed.
     * @param adapter          The Array Adapter that references the list to be updated
     * @param names            The names of the current list being displayed, to be updated
     *                         to display the identifier property of each JSON Object
     * @param courseOfferingId The course offering Id for which to display the tutors
     */
    private void refreshTutorList(final ArrayAdapter<String> adapter, final List<String> names,
                                           final String courseOfferingId) {
        HttpsUtils.get("/courseoffering/"+courseOfferingId, new RequestParams(), new JsonHttpResponseHandler() {
        	
        	/**
             * This method defines the behavior of the successful completion of the HTTP request. 
             * @param statusCode The status code of the response
             * @param headers    The list of headers that are returned, if they exist
             * @param response   An array of JSON objects that represents the HTTP response data  
             */
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                names.clear();
                names.add("Please select...");
                for( int i = 0; i < response.length(); i++){
                    try {
                        String tutorIdentifier = response.getJSONObject(i).getString("username");
                        tutorIdentifier = tutorIdentifier + " rate:$";
                        tutorIdentifier = tutorIdentifier.concat(response.getJSONObject(i).getString("hourlyRate"));
                        tutorIdentifier = tutorIdentifier + "/hour";
                        names.add(tutorIdentifier);
                    } catch (Exception e) {
                        error += e.getMessage();
                    }
                }
                adapter.notifyDataSetChanged();
            }
            
            /**
             * This method defines the behavior of the erroneous completion of the HTTP request. 
             * @param statusCode    The status code of the response, which will indicate what went wrong
             * @param headers       The list of headers that are returned, if they exist
             * @param throwable     A Throwable object that describes the underlying cause of the error
             * @param errorResponse A JSONObject that represents the HTTP response data  
             */
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
            }
        });
    }
    
    /**
     * This method defines the behavior for refreshing a list of availabilities. Note that it is 
     * very similar to the refreshList method, but must be defined separately as it requires multiple
     * items to be displayed. Furthermore, it also requires a different form of parsing, as the 
     * HTTP request will return a tutor object, from which the availabilities must be extracted.
     * @param adapter   The Array Adapter that references the list to be updated
     * @param names     The names of the current list being displayed, to be updated
     * @param tutorName The name of the tutor to list the availabilities for
     */
    private void refreshAvailabilityList(final ArrayAdapter<String> adapter, final List<String> names,
                                  final String tutorName) {
        HttpsUtils.get("/tutor/"+tutorName, new RequestParams(), new JsonHttpResponseHandler() {
        	
        	/**
             * This method defines the behavior of the successful completion of the HTTP request. 
             * @param statusCode The status code of the response
             * @param headers    The list of headers that are returned, if they exist
             * @param response   A JSONObject that represents the HTTP response data  
             */
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                
                try {
                    
                    JSONArray tutorAvails = response.getJSONArray("avails");

                    names.clear();
                    names.add("Please select...");
                    
                    for (int i = 0; i < tutorAvails.length(); i++) {
                        try {
                            String availIdentifier = tutorAvails.getJSONObject(i).getString("date");
                            availIdentifier = availIdentifier + " ";
                            availIdentifier = availIdentifier.concat(tutorAvails.getJSONObject(i).getString("time"));
                            names.add(availIdentifier);
                        } catch (Exception e) {
                            error += e.getMessage();
                        }
                    }
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    error += e.getMessage();
                }
            }
            
            /**
             * This method defines the behavior of the erroneous completion of the HTTP request. 
             * @param statusCode    The status code of the response, which will indicate what went wrong
             * @param headers       The list of headers that are returned, if they exist
             * @param throwable     A Throwable object that describes the underlying cause of the error
             * @param errorResponse A JSONObject that represents the HTTP response data  
             */
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
            }
        });
    }

    /**
     * This method returns the current view to the Dashboard, no matter what view it is called 
     * upon
     * @param v A View object that represents a component of the UI. This is the component 
     *          that was interacted with that caused this method to be called.
     */
    public void goBackToDashboard(View v){
        createSession = true;
        goToDashboard();
    }


}
