package tutoapp.com.tutoappstudent.FragmentsTutorequest;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import tutoapp.com.tutoappstudent.Objects.TutorShip;
import tutoapp.com.tutoappstudent.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TutorShipDate#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TutorShipDate extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "SKY";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TutorShip tutoria;

    public TutorShipDate() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TutorShipDate.
     */
    // TODO: Rename and change types and number of parameters
    public static TutorShipDate newInstance(String param1, String param2) {
        TutorShipDate fragment = new TutorShipDate();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_tutor_ship_date, container, false);
        CircleImageView fabNext=(CircleImageView) rootView.findViewById(R.id.button_addc);
        final DatePicker datepicker=(DatePicker) rootView.findViewById(R.id.datepicker);



        fabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int day = datepicker.getDayOfMonth();
                int month = datepicker.getMonth() + 1;
                int year = datepicker.getYear();
                String dateString=day+"/"+month+"/"+year;
                tutoria.setDate(new Date());
                Bundle bundle=new Bundle();
                bundle.putSerializable("tutoria",tutoria);
                TutorShipTime fragment = new TutorShipTime();
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.container_tuto_request, fragment, "TAG");
                transaction.commit();

            }
        });
        return rootView;
    }
    public Date convertirFecha(String dateString){
        Date date=null;
        try {

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            date = sdf.parse(dateString);



            Log.d(TAG, "Got the date: " + date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
