package ca.mcgill.ecse321.tutoringservice;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button signupPage= (Button) findViewById(R.id.getstarted);
        Button loginPage= (Button) findViewById(R.id.loginbutton);

        signupPage.setOnClickListener(new View.OnClickListener() {


            public void onClick(View view){
                setContentView(R.layout.signup_page);
            }
        });

        loginPage.setOnClickListener(new View.OnClickListener() {


            public void onClick(View view){
                setContentView(R.layout.login_page);
            }
        });
    }




}
