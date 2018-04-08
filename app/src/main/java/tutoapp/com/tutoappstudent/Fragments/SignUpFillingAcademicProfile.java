package tutoapp.com.tutoappstudent.Fragments;


import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import tutoapp.com.tutoappstudent.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SignUpFillingAcademicProfile.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SignUpFillingAcademicProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFillingAcademicProfile extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<String> data;
    private OnFragmentInteractionListener mListener;

    public SignUpFillingAcademicProfile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignUpFillingAcademicProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUpFillingAcademicProfile newInstance(String param1, String param2) {
        SignUpFillingAcademicProfile fragment = new SignUpFillingAcademicProfile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            data=getArguments().getStringArrayList("collect_data");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview= inflater.inflate(R.layout.fragment_sign_up_filling_academic_profile, container, false);

        Button NextStep= rootview.findViewById(R.id.sign_up_filling_academic_profile_continue);//android:id="@+id/signupfillingacademicprofile_continuebtn"


        Button SelectProfession= (Button) rootview.findViewById(R.id.sign_up_filling_academic_profile_profession);//android:id="@+id/signupfillingacademicprofile_continuebtn"
        Button SelectPD= (Button) rootview.findViewById(R.id.sign_up_filling_academic_post_education);//android:id="@+id/signupfillingacademicprofile_continuebtn"

        SelectProfession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {// se lanzara como si tuviera solo profesion
                LaunchAlertDialog(getLayoutInflater(),0);
            }
        });
        SelectPD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {// se lanzara como si tuviera solo profesion
                LaunchAlertDialog(getLayoutInflater(),1);
            }
        });

        NextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment newFragment;
                newFragment=new FinishSignUp();

                //data.add(SpinRama.getSelectedItem().toString());
                Bundle b=new Bundle();
                b.putStringArrayList("collect_data",data);
                newFragment.setArguments(b);
                getFragmentManager().beginTransaction().replace(R.id.signup_activity_container, newFragment).commit();
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




    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void LaunchAlertDialog(LayoutInflater inflater,int value){

        if(value==0){
            final View dialoglayout = inflater.inflate(R.layout.layout_customdialog_pick_professional_degree, null);
            Button Seguir=(Button) dialoglayout.findViewById(R.id.layout_customdialog_pick_professional_degree_button);
            final Spinner Area=(Spinner) dialoglayout.findViewById(R.id.layout_customdialog_pick_professional_degree_spin_area);
            Spinner Prof=(Spinner) dialoglayout.findViewById(R.id.layout_customdialog_pick_professional_degree_spin_prof);

            TextView TextViewAOcultar=dialoglayout.findViewById(R.id.layout_customdialog_pick_professional_textview_pd);
            Spinner spinner=dialoglayout.findViewById(R.id.layout_customdialog_pick_professional_degree_spinner_esp);
            TextInputLayout textInputLayout=dialoglayout.findViewById(R.id.layout_customdialog_pick_professional_layouttext_pd);

            TextViewAOcultar.setVisibility(View.GONE);
            spinner.setVisibility(View.GONE);
            textInputLayout.setVisibility(View.GONE);

            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setView(dialoglayout);
            builder.show();

            final int indiceCiudad=Prof.getSelectedItemPosition();
            final Object[] some_array = getResources().getStringArray(R.array.areas);
            Prof.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            Seguir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });


        }else{

            final View dialoglayout = inflater.inflate(R.layout.layout_customdialog_pick_professional_degree, null);
            Button Seguir=(Button) dialoglayout.findViewById(R.id.layout_customdialog_pick_professional_degree_button);

            LinearLayout layout=dialoglayout.findViewById(R.id.layout_customdialog_pick_professional_degree_container);
            layout.setVisibility(View.GONE);

            Spinner spinner=dialoglayout.findViewById(R.id.layout_customdialog_pick_professional_degree_spinner_esp);
            TextInputLayout textInputLayout=dialoglayout.findViewById(R.id.layout_customdialog_pick_professional_layouttext_pd);


            EditText editText=textInputLayout.getEditText();



            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setView(dialoglayout);
            builder.show();


            final Object[] some_array = getResources().getStringArray(R.array.areas);

            Seguir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

        }
       //LayoutInflater inflater = getLayoutInflater();


   }
}
