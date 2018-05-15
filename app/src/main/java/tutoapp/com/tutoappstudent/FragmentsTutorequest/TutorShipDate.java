package tutoapp.com.tutoappstudent.FragmentsTutorequest;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;


import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;
import java.util.Date;

import tutoapp.com.tutoappstudent.Objects.TutorShip;
import tutoapp.com.tutoappstudent.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TutorShipDate#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TutorShipDate extends Fragment  implements TimePickerDialog.OnTimeSetListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "SKY";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TutorShip tutoria;
    private static Calendar calendarTuto =Calendar.getInstance();
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
        ImageButton fabNext=(ImageButton) rootView.findViewById(R.id.button_addc);
        Button setTime=(Button) rootView.findViewById(R.id.setTimebtn);
        CalendarView calendarView = (CalendarView) rootView.findViewById(R.id.datepicker);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                setDateOfCalendar(year,month,dayOfMonth);
            }
        });
        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                new android.app.TimePickerDialog(
                        getActivity(),
                        new android.app.TimePickerDialog.OnTimeSetListener(){
                            @Override
                            public void onTimeSet(TimePicker view, int hour, int minute) {
                                Toast.makeText(getContext(),""+hour+" "+minute,Toast.LENGTH_SHORT).show();
                            }
                        },
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        true
                ).show();
            }
        });




        fabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tutoria.setDateString(getDateofCalendar().toString());
                Intent map=new Intent(getContext(),TutorShipLocation.class);
                map.putExtra("tutoria",tutoria);
                startActivity(map);

            }
        });
        return rootView;
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {

    }
    public void setDateOfCalendar(int year,int month,int day){
        calendarTuto.set(Calendar.YEAR, year);
        calendarTuto.set(Calendar.MONTH, month);
        calendarTuto.set(Calendar.DAY_OF_MONTH, day);
    }
    public void setTimeOfCalendar(int year,int month,int day){
        calendarTuto.set(Calendar.HOUR_OF_DAY, 0);
        calendarTuto.set(Calendar.MINUTE, 0);
        calendarTuto.set(Calendar.SECOND, 0);
        calendarTuto.set(Calendar.MILLISECOND, 0);
    }
    public Date getDateofCalendar(){
        return calendarTuto.getTime();
    }
}
