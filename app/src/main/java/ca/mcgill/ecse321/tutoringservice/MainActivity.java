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

    // called from book a session or login to load the personal dashboard
    /**
     * This method loads the student Dashboard
     */
    public void goToDashboard(){
        setContentView(R.layout.dashboard_page);

        // need to reset booleans before going to the book a session page creation
        createUni = true;
        createCourse = true;
        createCourseOffering = true;
        createTutor = true;
        createAvailability = true;

        // set up object for the session dropdown list
        Spinner sessionSpinner = (Spinner) findViewById(R.id.session_spinner);
        sessionAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sessionNames);
        sessionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sessionSpinner.setAdapter(sessionAdapter);

        sessionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                    // populate the rest of the page
                    refreshSessionDashboard(selectedSessionID);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });

        // get all the sesisons for the currently logged in user upon dashboard page creation
        refreshList(sessionAdapter, sessionNames,
                "/sessionbystudent?student_name="+currentlySelectedUsername, "sessionid");
    }

    // called when a session is selected and the dashboard needs to be populated with it's information
    /**
     * @param sessionId
     */
    public void refreshSessionDashboard(String sessionId){
        // get the text objects by id from the view so that they can be populated
        final TextView date = (TextView) findViewById(R.id.date);
        final TextView time = (TextView) findViewById(R.id.time);
        final TextView tutor = (TextView) findViewById(R.id.tutor);
        final TextView course = (TextView) findViewById(R.id.course);
        final TextView courseOffering = (TextView) findViewById(R.id.courseOffering);

        // send the HTTP request to get the session information by its ID
        HttpsUtils.get("/session?session_id="+sessionId, new RequestParams(), new JsonHttpResponseHandler() {

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

    // called when Book session is pressed from the dashboard
    /**
     * @param v
     */
    public void goToSessionPage(View v){
        setContentView(R.layout.booksession_page);

        Spinner uniSpinner = (Spinner) findViewById(R.id.uni_spinner);
        Spinner courseSpinner = (Spinner) findViewById(R.id.course_spinner);
        Spinner courseOfferingSpinner = (Spinner) findViewById(R.id.courseoffering_spinner);
        Spinner tutorSpinner = (Spinner) findViewById(R.id.tutor_spinner);
        Spinner availabilitySpinner = (Spinner) findViewById(R.id.availability_spinner);

        //Set up objects for each list
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
                    // populate the course list based on the selected university
                    refreshList(courseAdapter, courseNames, "/universities/" + selectedUni, "courseName");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}

        });

        courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                    // populate the course offering list based on the selected course
                    refreshCourseOfferingList(courseOfferingAdapter, courseOfferingNames, courseString, selectedUni);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}

        });

        courseOfferingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // listener code is run twice - once at creation and once when selection is made
                // ensures that the selection is not requested upon creation
                if(createCourseOffering == true){
                    createCourseOffering = false;
                }
                else {
                    String courseOffering = parentView.getItemAtPosition(position).toString();
                    // course offering dropdown lists the TERM YEAR id: ID
                    // must parse the string and take the 3rd element (0 index)
                    String[] sp = courseOffering.split(" ");
                    selectedCourseOfferingId = sp[3];
                    // populate the tutor list based on the selected course offering
                    refreshTutorList(tutorAdapter, tutorNames, selectedCourseOfferingId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) { }

        });

        tutorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

                    // save the hourly rate for session creation
                    // tutor list has the form USERNAME rate:$RATE/hour
                    // the first number is in the 6th position (0 index)
                    // the last number is right before the '/'
                    int startPos = 6;
                    int endPos = sp[1].indexOf('/');
                    StringBuilder sb = new StringBuilder();
                    for(int i=startPos; i < endPos; i++){
                        char nextDigit = sp[1].charAt(i);
                        sb.append(nextDigit);
                    }
                    selectedTutorHR = sb.toString();

                    // populate the availability list based on the selected tutor
                    refreshAvailabilityList(availabilityAdapter, availabilityNames, selectedTutor);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}

        });

        availabilitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // listener code is run twice - once at creation and once when selection is made
                // ensures that the selection is not requested upon creation
                if(createAvailability == true){
                    createAvailability = false;
                }
                else {
                    idAvail = parentView.getItemAtPosition(position).toString();
                    // save the selected availability date and time for session creation
                    // parse the string into DATE and TIME components
                    String[] sp = idAvail.split(" ");
                    selectedAvailabilityDate = sp[0];
                    selectedAvailabilityTime = sp[1];
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) { }

        });

        // university doesn't depend on anything else so can refresh the list upon page creation
        refreshList(universityAdapter, universityNames, "/universities", "name");
    }

    // called when the SUBMIT button is pressed on the session creation page
    /**
     * @param v
     */
    public void createSession(View v){

        // make sure that all items are selected
        if(this.selectedTutor == "" || this.selectedTutor == null || this.currentlySelectedUsername == "" || this.currentlySelectedUsername == null || this.selectedAvailabilityDate == "" || this.selectedAvailabilityDate == null || this.selectedAvailabilityTime == "" || this.selectedAvailabilityTime == null|| this.selectedCourseOfferingId == "" || this.selectedCourseOfferingId == null || this.selectedTutorHR == "" || this.selectedTutorHR == null){

            error = "Please select all fields before creating a session!";
            refreshErrorMessage();
        }
        // send the HTTP request to create a session based on the previously saved information
        else {
            HttpsUtils.post("/session?tutor_name=" + this.selectedTutor + "&student_name=" + this.currentlySelectedUsername
                    + "&booking_date=" + this.selectedAvailabilityDate + "&booking_time=" + this.selectedAvailabilityTime
                    + "&course_offering_id=" + this.selectedCourseOfferingId + "&amount_paid=" + this.selectedTutorHR, new RequestParams(), new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    // reset the dropdown boolean
                    createSession = true;
                    // reset all the chosen fields
                    selectedCourseOfferingId = "";
                    selectedTutor = "";
                    selectedTutorHR = "";
                    selectedAvailabilityDate = "";
                    selectedAvailabilityTime = "";
                    goToDashboard();
                }

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
     * @param v
     */
    public void logout(View v){
        HttpsUtils.post("/logout?username=" + this.currentlySelectedUsername, new RequestParams(), new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                // reset the dropdown boolean
                createSession = true;
                setContentView(R.layout.activity_main);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                error = responseString;
                if(responseString == "" || responseString.isEmpty()){
                    createSession = true;
                    currentlySelectedUsername = "";
                    setContentView(R.layout.activity_main);
                }
                //refreshErrorMessage();
            }
        });
    }
    // called from the startup page, go to the register page
    /**
     * @param v
     */
    public void goToSignUp(View v){ setContentView(R.layout.signup_page); }

    // called from the startup page, go to the login page
    /**
     * @param v
     */
    public void goToLogin(View v){ setContentView(R.layout.login_page); }

    /**
     * @param v
     */
    public void goBack(View v){
        setContentView(R.layout.activity_main);
    }

    //We want to refresh last thus put it at the end
    /**
     * 
     */
    private void refreshErrorMessage() {

        // set the error message
        TextView tvError = (TextView) findViewById(R.id.error);
        tvError.setText(error);

        if (error == null || error.length() == 0) {
            tvError.setVisibility(View.GONE);
        } else {
            tvError.setVisibility(View.VISIBLE);
        }
    }

    // Refreshes and updates the list. Used for university, course and session lists.
    // identifier is used to parse the JSON object to get the correct information
    /**
     * @param adapter
     * @param names
     * @param restFunctionName
     * @param identifier
     */
    private void refreshList(final ArrayAdapter<String> adapter, final List<String> names,
                             final String restFunctionName, final String identifier) {
        String fcn = restFunctionName;
        HttpsUtils.get(restFunctionName, new RequestParams(), new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                // iterate through the objects in the response to display the information in the
                // dropdown list
                names.clear();
                names.add("Please select...");
                for( int i = 0; i < response.length(); i++){
                    try {
                        names.add(response.getJSONObject(i).getString(identifier));
                    } catch (Exception e) {
                        error += e.getMessage();
                    }
                    //refreshErrorMessage();
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
                //refreshErrorMessage();
            }
        });
    }

    //Course Offering needs its own refresh method since it requires multiple items to be displayed
    /**
     * @param adapter
     * @param names
     * @param courseName
     * @param uniName
     */
    private void refreshCourseOfferingList(final ArrayAdapter<String> adapter, final List<String> names,
                             final String courseName, final String uniName) {
        HttpsUtils.get("/courses/"+uniName+"/"+courseName, new RequestParams(), new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                // iterate through the returned course offerings and display TERM YEAR id: iD
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
                    //refreshErrorMessage();
                }
                adapter.notifyDataSetChanged();
            }

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
     * @param adapter
     * @param names
     * @param courseOfferingId
     */
    private void refreshTutorList(final ArrayAdapter<String> adapter, final List<String> names,
                                           final String courseOfferingId) {
        HttpsUtils.get("/courseoffering/"+courseOfferingId, new RequestParams(), new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                names.clear();
                names.add("Please select...");
                // iterate through the returned tutors and display USERNAME rate:$RATE/hour
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
                    //refreshErrorMessage();
                }
                adapter.notifyDataSetChanged();
            }

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

    // Availability needs its own refresh method since it requires multiple items to be displayed
    // The returned availability is also parsed differently since the HTTP request returns a tutor
    // object and the availabilities must be extracted from that
    /**
     * @param adapter
     * @param names
     * @param tutorName
     */
    private void refreshAvailabilityList(final ArrayAdapter<String> adapter, final List<String> names,
                                  final String tutorName) {
        HttpsUtils.get("/tutor/"+tutorName, new RequestParams(), new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // returns tutor object
                try {
                    // get the availabilities from the tutor JSON object
                    JSONArray tutorAvails = response.getJSONArray("avails");

                    names.clear();
                    names.add("Please select...");
                    // iterate through the availabilities and display DATE TIME
                    for (int i = 0; i < tutorAvails.length(); i++) {
                        try {
                            String availIdentifier = tutorAvails.getJSONObject(i).getString("date");
                            availIdentifier = availIdentifier + " ";
                            availIdentifier = availIdentifier.concat(tutorAvails.getJSONObject(i).getString("time"));
                            names.add(availIdentifier);
                        } catch (Exception e) {
                            error += e.getMessage();
                        }
                        //refreshErrorMessage();
                    }
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    error += e.getMessage();
                }
            }

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

    //        Has all the go-back codes
    /**
     * @param v
     */
    public void goBackToDashboard(View v){
        createSession = true;
        goToDashboard();
    }


}
