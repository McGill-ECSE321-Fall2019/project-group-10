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

    private String currentlySelectedUsername = "";
    private String currentlySelectedUniversity = "";
    private String currentlySelectedCourse = "";
    private String currentlySelectedCourseOffering = "";
    private String currentlySelectedTutor = "";
    private String currentlySelectedAvailability = "";
    private String currentlySelectedSession = "";

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

        Spinner uniSpinner = (Spinner) findViewById(R.id.session_spinner);
        Spinner courseSpinner = (Spinner) findViewById(R.id.course_spinner);
        Spinner courseOfferingSpinner = (Spinner) findViewById(R.id.courseOffering_spinner);
        Spinner tutorSpinner = (Spinner) findViewById(R.id.tutor_spinner);
        Spinner availabilitySpinner = (Spinner) findViewById(R.id.availability_spinner);

        Spinner sessionSpinner = (Spinner) findViewById(R.id.session_spinner);

        //Update each list
        universityAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, universityNames);
        universityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        uniSpinner.setAdapter(universityAdapter);

        courseAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, courseNames);
        courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseSpinner.setAdapter(courseAdapter);


        refreshErrorMessage();

        // Get initial content for spinners
        refreshLists(this.getCurrentFocus());

        setContentView(R.layout.activity_main);
    }


    public void goToDashBoard (View v) { }

    public void goToLoginFromRegister(View v) {
        error = "";
        final TextView email = (TextView) findViewById(R.id.signupEmail);
        final TextView username = (TextView) findViewById(R.id.signupUser);
        final TextView password = (TextView) findViewById(R.id.signupPassword);
        final TextView name = (TextView) findViewById(R.id.signupName);
        final TextView age = (TextView) findViewById(R.id.signupAge);
        final TextView phoneNumber = (TextView) findViewById(R.id.signupPhoneNumber);

        //Store for use for username later.
        this.username = username.getText().toString();

        HttpsUtils.post("createuser2/userName=" + username.getText().toString() +
                "&userPassword=" + password.getText().toString() +
                "&userEmail=" + email.getText().toString() +
                "&age=" + age.getText().toString() +
                "&phoneNum=" + phoneNumber.getText().toString() +
                "&name=" + name.getText().toString(), new RequestParams(), new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                email.setText("");
                username.setText("");
                password.setText("");
                age.setText("");
                phoneNumber.setText("");
                name.setText("");

                setContentView(R.layout.login_page);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

                email.setText("FAILURE");

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
    public void login(View v) {

        error = "";
        final TextView username = (TextView) findViewById(R.id.loginusername);
        final TextView password = (TextView) findViewById(R.id.loginpassword);


        setContentView(R.layout.dashboard_page);
    }

    public String getCurrentlyLoggedIn (){ return this.username; }

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

    //Refresh each list.
    public void refreshAllLists(View view) {
        refreshList(universityAdapter ,universityNames, "universities/" + );
        refreshList(courseAdapter, courseNames, "courses/" + getCurrentlyLoggedIn());
        refreshList(courseOfferingAdapter, courseOfferingNames, "events");
        refreshList(tutorAdapter, tutorNames, "events");
        refreshList(availabilityAdapter, availabilityNames, "events");
        refreshList(sessionAdapter, sessionNames, "events");
    }

    //Refreshes and updates the list.
    private void refreshList(final ArrayAdapter<String> adapter, final List<String> names, final String restFunctionName) {
        HttpsUtils.get(restFunctionName, new RequestParams(), new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                names.clear();
                names.add("Please select...");
                for( int i = 0; i < response.length(); i++){
                    try {
                        names.add(response.getJSONObject(i).getString("name"));
                    } catch (Exception e) {
                        error += e.getMessage();
                    }
                    refreshErrorMessage();
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
}
