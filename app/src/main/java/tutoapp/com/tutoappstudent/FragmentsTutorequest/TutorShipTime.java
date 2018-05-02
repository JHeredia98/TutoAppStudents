package tutoapp.com.tutoappstudent.FragmentsTutorequest;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import tutoapp.com.tutoappstudent.Objects.TutorShip;
import tutoapp.com.tutoappstudent.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TutorShipTime.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TutorShipTime#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TutorShipTime extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG ="5MORE" ;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TutorShip tutoria;
    private OnFragmentInteractionListener mListener;

    public TutorShipTime() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TutorShipTime.
     */
    // TODO: Rename and change types and number of parameters
    public static TutorShipTime newInstance(String param1, String param2) {
        TutorShipTime fragment = new TutorShipTime();
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
            tutoria = (TutorShip) getArguments().getSerializable("tutoria");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_tutor_ship_time, container, false);
        ImageButton fabNext=(ImageButton) rootView.findViewById(R.id.button_addc);
        TimePicker timepicker= (TimePicker) rootView.findViewById(R.id.timepicker);
        int hora=timepicker.getHour();
        int minuto=timepicker.getMinute();
        String StringTime=hora+":"+minuto;
        tutoria.setDate(convertDateTime(tutoria.getDate(),StringTime));
        fabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent map=new Intent(getContext(),TutorShipLocation.class);
                map.putExtra("tutoria",tutoria);
                startActivity(map);

            }
        });
        return rootView;
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
    public Date convertDateTime(Date date,String timeString){

        try {

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
            date = sdf.parse(date.toString()+" "+timeString);



            Log.d(TAG, "Got the date: " + date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

}
