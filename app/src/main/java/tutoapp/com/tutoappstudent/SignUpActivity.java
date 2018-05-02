package tutoapp.com.tutoappstudent;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import tutoapp.com.tutoappstudent.Register.SignupUser;


public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Fragment newFragment;
        newFragment=new SignupUser();
        //newFragment.setArguments();
        getSupportFragmentManager().beginTransaction().replace(R.id.signup_activity_container, newFragment).commit();
    }
}
