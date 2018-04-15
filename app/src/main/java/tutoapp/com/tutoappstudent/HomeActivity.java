package tutoapp.com.tutoappstudent;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import tutoapp.com.tutoappstudent.Fragments.TutosNotCompleted;
import tutoapp.com.tutoappstudent.Fragments.HomeFragment;
import tutoapp.com.tutoappstudent.Fragments.Profile;


public class HomeActivity extends AppCompatActivity {

    private android.support.v7.widget.Toolbar toolbar;
    private FirebaseAuth mAuth;
    private DatabaseReference mUserRef;
    private String mCurrent_user_id;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {


            Fragment newFragment;

            Bundle args;

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    args = new Bundle();

                    newFragment=new HomeFragment();
                    newFragment.setArguments(args);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentview, newFragment).commit();
                    return true;
                case R.id.navigation_clase_actual:


                    return true;
                case R.id.navigation_clases_pasadas:

                    args = new Bundle();

                    newFragment=new TutosNotCompleted();
                    newFragment.setArguments(args);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentview, newFragment).commit();
                    return true;
                case R.id.navigation_social:


                    if(mAuth==null){

                        Intent i =new Intent();
                        i.setClass(getApplicationContext(),FirebaseAccountActivity.class);
                        startActivity(i);
                    }else{

                        Intent i =new Intent();
                        i.setClass(getApplicationContext(),TabChatActivity.class);
                        startActivity(i);

                    }

                    //finish();
                    return true;
                case R.id.navigation_profile:

                    args = new Bundle();

                    newFragment=new Profile();
                    newFragment.setArguments(args);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentview, newFragment).commit();
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar =  findViewById(R.id.main_page_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Inicio");
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mAuth = FirebaseAuth.getInstance();
        //Toast.makeText(getApplicationContext(),mAuth.getCurrentUser().getUid(),Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId()==R.id.main_logout_btn){
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(getApplicationContext(), "Cerrado", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    public FirebaseAuth getmAuth() {
        return mAuth;
    }

    public void setmAuth(FirebaseAuth mAuth) {
        this.mAuth = mAuth;
    }
}

