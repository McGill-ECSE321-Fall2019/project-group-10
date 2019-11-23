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
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private String error = null;

    private List<String> universityNames = new ArrayList<>();
    private ArrayAdapter<String> universityAdapter;

    private List<String> courseNames = new ArrayList<>();
    private ArrayAdapter<String> courseAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
 
    public void goToDashBoard (View v) {

        error = "";
        final TextView email = (TextView) findViewById(R.id.signupEmail);
        final TextView username = (TextView) findViewById(R.id.signupUser);
        final TextView password = (TextView) findViewById(R.id.signupPassword);
        final TextView name = (TextView) findViewById(R.id.signupName);
        final TextView age = (TextView) findViewById(R.id.signupAge);
        final TextView phoneNumber = (TextView) findViewById(R.id.signupPhoneNumber);

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

                //setContentView(R.layout.dashboard_page);
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
}
