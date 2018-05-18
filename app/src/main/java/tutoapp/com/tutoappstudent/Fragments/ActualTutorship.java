package tutoapp.com.tutoappstudent.Fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import tutoapp.com.tutoappstudent.Objects.TutorShip;
import tutoapp.com.tutoappstudent.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ActualTutorship#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActualTutorship extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String SHAREDNAME ="tutoshared" ;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String key;
    private DatabaseReference tutorshipsReference;
    private TutorShip tutorShip;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public ActualTutorship() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ActualTutorship newInstance(String param1, String param2) {
        ActualTutorship fragment = new ActualTutorship();
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
        }
        sharedPreferences=getActivity().getSharedPreferences(SHAREDNAME,MODE_PRIVATE);
        editor=sharedPreferences.edit();
        key=sharedPreferences.getString("tuto_key","");
        tutorshipsReference= FirebaseDatabase.getInstance().getReference().child("Tutorships").child(key);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView= inflater.inflate(R.layout.fragment_actual_tutorship, container, false);
        RelativeLayout Container =rootView.findViewById(R.id.actual_tutorship_container);
        RelativeLayout Blank=rootView.findViewById(R.id.actual_tutorship_container);

        if(key!=""){
            Blank.setVisibility(View.GONE);
            Container.setVisibility(View.VISIBLE);
            final TextView TutoName=rootView.findViewById(R.id.actual_tutorship_tuto_name);
            final TextView TutorshipTopic=rootView.findViewById(R.id.actual_tutorship_topic);
            final TextView TutorshipLocation=rootView.findViewById(R.id.actual_tutorship_place);
            final TextView TutorshipHour=rootView.findViewById(R.id.actual_tutorship_hour);
            tutorshipsReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    tutorShip = dataSnapshot.getValue(TutorShip.class);
                    TutoName.setText(tutorShip.getIdTuto());
                    TutorshipTopic.setText(tutorShip.getTopicId());
                    TutorshipLocation.setText(tutorShip.getLatitude() +" "+ tutorShip.getLongitude());
                    TutorshipHour.setText(tutorShip.getDateString());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            ImageButton Llamar =(ImageButton) rootView.findViewById(R.id.actual_tutorship_llamar);
            Llamar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "Your Phone_number"));
                    startActivity(intent);
                }
            });
            ImageButton CancelarClase =(ImageButton) rootView.findViewById(R.id.actual_tutorship_llamar);
            CancelarClase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }else{

            Blank.setVisibility(View.VISIBLE);
            Container.setVisibility(View.GONE);
        }

        return rootView;
    }

}
