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

    private boolean createUni = true;
    private boolean createCourse = true;
    private boolean createCourseOffering = true;
    private boolean createTutor = true;
    private boolean createAvailability = true;
    private boolean createSession = true;

    private String selectedUni = "";
    private String courseString = "";
    private String idAvail = "";

    private String selectedCourseOfferingId = "";
    private String selectedTutor = "";
    private String selectedTutorHR = "";
    private String selectedAvailabilityDate = "";
    private String selectedAvailabilityTime = "";
    private String currentlySelectedUsername = "";

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

    private List<String> sessionNames = new ArrayList<>();
    private ArrayAdapter<String> sessionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //onCreateSessionBooking(savedInstanceState);
    }

    public void goToLoginFromRegister(View v) {
        error = "";

        final TextView email = (TextView) findViewById(R.id.signupEmail);
        final TextView username = (TextView) findViewById(R.id.signupUser);
        final TextView password = (TextView) findViewById(R.id.signupPassword);
        final TextView name = (TextView) findViewById(R.id.signupName);
        final TextView age = (TextView) findViewById(R.id.signupAge);
        final TextView phoneNumber = (TextView) findViewById(R.id.signupPhoneNumber);

        //Store for use for username later.
        this.currentlySelectedUsername = username.getText().toString();

        if (email.getText().toString().matches("") || username.getText().toString().matches("") || password.getText().toString().matches("") || name.getText().toString().matches("")|| age.getText().toString().matches("") || phoneNumber.getText().toString().matches(""))
        {
            error = "Please fill out all fields.";
            refreshErrorMessage();
            return;
        }

        HttpsUtils.post("/createuser2?userName=" + username.getText().toString() +
                "&userPassword=" + password.getText().toString() +
                "&userEmail=" + email.getText().toString() +
                "&age=" + age.getText().toString() +
                "&phoneNum=" + phoneNumber.getText().toString() +
                "&name=" + name.getText().toString(), new RequestParams(), new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                setContentView(R.layout.login_page);
            }
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

    //updates dashboard
    public void loginUser(View v) {

        error = "";
        final TextView username = (TextView) findViewById(R.id.loginusername);
        final TextView password = (TextView) findViewById(R.id.loginpassword);

        if (username.getText().toString().matches("") || password.getText().toString().matches(""))
        {
            error = "Please fill out all fields.";
            refreshErrorMessage();
            return;
        }

        HttpsUtils.post("/login?username=" + username.getText().toString() + "&password="
                + password.getText().toString(), new RequestParams(), new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {
                //Setup dashboard info and load page
                //onCreateDashboardSessions();

                setContentView(R.layout.dashboard_page);
                currentlySelectedUsername = username.getText().toString();

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                //Set all texts field to empty
                if(responseString.equals("true")){
                    currentlySelectedUsername = username.getText().toString();
                    goToDashboard();
                }
                else {
                    username.setText("");
                    password.setText("");

                    // change error message.
                    error = "Incorrect information";
                    refreshErrorMessage();
                }
            }
        });
    }

    public void goToDashboard(){
        setContentView(R.layout.dashboard_page);

        Spinner sessionSpinner = (Spinner) findViewById(R.id.session_spinner);
        sessionAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sessionNames);
        sessionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sessionSpinner.setAdapter(sessionAdapter);

        sessionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(createSession == true){
                    createSession = false;
                }
                else {
                    Object session = parentView.getItemAtPosition(position);
                    String selectedSession = session.toString();
                    refreshSessionDashboard(selectedSession);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        refreshList(sessionAdapter, sessionNames,
                "/sessionbystudent?student_name="+currentlySelectedUsername, "sessionid");
    }

    public void refreshSessionDashboard(String sessionId){
        final TextView date = (TextView) findViewById(R.id.date);
        final TextView time = (TextView) findViewById(R.id.time);
        final TextView tutor = (TextView) findViewById(R.id.tutor);
        final TextView course = (TextView) findViewById(R.id.course);
        final TextView courseOffering = (TextView) findViewById(R.id.courseOffering);

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

    public void goToSessionPage(View v){
        setContentView(R.layout.booksession_page);

        Spinner uniSpinner = (Spinner) findViewById(R.id.uni_spinner);
        Spinner courseSpinner = (Spinner) findViewById(R.id.course_spinner);
        Spinner courseOfferingSpinner = (Spinner) findViewById(R.id.courseoffering_spinner);
        Spinner tutorSpinner = (Spinner) findViewById(R.id.tutor_spinner);
        Spinner availabilitySpinner = (Spinner) findViewById(R.id.availability_spinner);

        //Update each list
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
                if(createUni == true){
                    createUni = false;
                }
                else {
                    Object uni = parentView.getItemAtPosition(position);
                    selectedUni = uni.toString();
                    refreshList(courseAdapter, courseNames, "/universities/" + selectedUni, "courseName");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(createCourse == true){
                    createCourse = false;
                }
                else {
                    Object course = parentView.getItemAtPosition(position);
                    courseString = course.toString();
                    refreshCourseOfferingList(courseOfferingAdapter, courseOfferingNames, courseString, selectedUni);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        courseOfferingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
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

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        tutorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(createTutor == true){
                    createTutor = false;
                }
                else {
                    String username = parentView.getItemAtPosition(position).toString();
                    String[] sp = username.split(" ");
                    selectedTutor = sp[0];

                    // get the hourly rate from the text rate:$##/hour
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

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        availabilitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
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

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        refreshUniversityList(this.getCurrentFocus());
    }

    public void createSession(View v){

        if(this.selectedTutor == "" || this.selectedTutor == null || this.currentlySelectedUsername == "" || this.currentlySelectedUsername == null || this.selectedAvailabilityDate == "" || this.selectedAvailabilityDate == null || this.selectedAvailabilityTime == "" || this.selectedAvailabilityTime == null|| this.selectedCourseOfferingId == "" || this.selectedCourseOfferingId == null || this.selectedTutorHR == "" || this.selectedTutorHR == null){

            error = "Please select all fields before creating a session!";
            refreshErrorMessage();
            return;
        }

        HttpsUtils.post("/session?tutor_name=" + this.selectedTutor + "&student_name=" + this.currentlySelectedUsername
                + "&booking_date=" + this.selectedAvailabilityDate + "&booking_time=" + this.selectedAvailabilityTime
                + "&course_offering_id=" + this.selectedCourseOfferingId + "&amount_paid=" + this.selectedTutorHR, new RequestParams(), new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

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

    public void goToSignUp(View v){ setContentView(R.layout.signup_page); }

    public void goToLogin(View v){ setContentView(R.layout.login_page); }

    public void goBack(View v){
        setContentView(R.layout.activity_main);
    }

    //We want to regresh last thus put it at the end
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

    public void refreshUniversityList(View view) {
        refreshList(universityAdapter, universityNames, "/universities", "name");
    }

    //Refreshes and updates the list.
    private void refreshList(final ArrayAdapter<String> adapter, final List<String> names,
                             final String restFunctionName, final String identifier) {
        String fcn = restFunctionName;
        HttpsUtils.get(restFunctionName, new RequestParams(), new JsonHttpResponseHandler() {

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

    //Course Offering needs its own refresh method since it requires multiple items to be displayed
    private void refreshCourseOfferingList(final ArrayAdapter<String> adapter, final List<String> names,
                             final String courseName, final String uniName) {
        HttpsUtils.get("/courses/"+uniName+"/"+courseName, new RequestParams(), new JsonHttpResponseHandler() {

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
    private void refreshTutorList(final ArrayAdapter<String> adapter, final List<String> names,
                                           final String courseOfferingId) {
        HttpsUtils.get("/courseoffering/"+courseOfferingId, new RequestParams(), new JsonHttpResponseHandler() {

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

    //Availability needs its own refresh method since it requires multiple items to be displayed
    private void refreshAvailabilityList(final ArrayAdapter<String> adapter, final List<String> names,
                                  final String tutorName) {
        HttpsUtils.get("/tutor/"+tutorName, new RequestParams(), new JsonHttpResponseHandler() {

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
}
