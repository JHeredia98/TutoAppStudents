package tutoapp.com.tutoappstudent;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

import tutoapp.com.tutoappstudent.Register.SignupUser;


public class SignUpActivity extends AppCompatActivity {
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth=FirebaseAuth.getInstance();
        Intent getIntent =getIntent();
        Bundle b=getIntent.getBundleExtra("bundlesignup");
        String username=b.getString("username");
        String password=b.getString("password");
        String email=b.getString("email");

        b.putString("firebaseid",mAuth.getCurrentUser().getUid());
        Fragment newFragment;
        newFragment=new SignupUser();
        newFragment.setArguments(b);
        //newFragment.setArguments();
        getSupportFragmentManager().beginTransaction().replace(R.id.signup_activity_container, newFragment).commit();
    }
}
