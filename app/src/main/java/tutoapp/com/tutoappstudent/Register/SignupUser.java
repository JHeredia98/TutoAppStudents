package tutoapp.com.tutoappstudent.Register;


import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import tutoapp.com.tutoappstudent.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SignupUser.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SignupUser#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignupUser extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String CONST_USERNAME = "username";
    private static final String CONST_PASSWORD = "password";
    private static final String CONST_FIREBASE = "firebaseid";
    private static final String CONST_EMAIL = "email";
    private static final String CONST_GENDER = "gender";
    private static final String CONST_NAME = "name";
    private static final String CONST_LASTNAME = "lastname";

    // TODO: Rename and change types of parameters
    private String Username;
    private String Password;
    private String FirebaseUID;
    private String Email;
    private String Gender;
    private String Name;
    private String LastName;

    private OnFragmentInteractionListener mListener;

    public SignupUser() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignupUser.
     */
    // TODO: Rename and change types and number of parameters
    public static SignupUser newInstance(String param1, String param2) {
        SignupUser fragment = new SignupUser();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Username=getArguments().getString(CONST_USERNAME);
            Password=getArguments().getString(CONST_PASSWORD);
            FirebaseUID=getArguments().getString(CONST_FIREBASE);
            Email=getArguments().getString(CONST_EMAIL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview =inflater.inflate(R.layout.fragment_signup_user, container, false);
        Button NextStep= rootview.findViewById(R.id.signupuser_continue);
        TextInputLayout layoutTel= (TextInputLayout) rootview.findViewById(R.id.fragment_signup_user_container_phone);
        TextInputLayout layoutName= (TextInputLayout) rootview.findViewById(R.id.sign_up_container_name);
        TextInputLayout layoutLastName= (TextInputLayout) rootview.findViewById(R.id.sign_up_container_lastname);
        final EditText EditTextPhone=layoutTel.getEditText();
        final EditText EditTextName=layoutTel.getEditText();
        final EditText EditTextLastName=layoutTel.getEditText();

        final Spinner SpinGender=(Spinner)rootview.findViewById(R.id.fragment_signupuser_gender);


        NextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment newFragment;
                //newFragment=new SignUpFillingAcademicProfile();
                newFragment=new FinishSignUp();
                Bundle b=new Bundle();
                b.putString(CONST_USERNAME,Username);
                b.putString(CONST_PASSWORD,Password);
                b.putString(CONST_FIREBASE,FirebaseUID);
                b.putString(CONST_EMAIL,Email);
                String name="";
                String lastname="";
                String selectSpinner=SpinGender.getSelectedItem().toString();

                if(EditTextName.getText().toString().equals("") && EditTextLastName.getText().toString().equals("")){

                    Toast.makeText(getContext(),"Especifique campos importantes", Toast.LENGTH_SHORT).show();
                }else{

                    name=EditTextName.getText().toString();
                    lastname=EditTextLastName.getText().toString();
                    b.putString(CONST_NAME,name);
                    b.putString(CONST_LASTNAME,lastname);
                    b.putString(CONST_GENDER,selectSpinner);
                    //userData.add(SpinSex.getSelectedItem().toString());

                    newFragment.setArguments(b);
                    getFragmentManager().beginTransaction().replace(R.id.signup_activity_container, newFragment).commit();
                }

            }
        });
        return rootview;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

