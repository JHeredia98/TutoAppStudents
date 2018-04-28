package tutoapp.com.tutoappstudent.FragmentsTutorequest;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import de.hdodenhof.circleimageview.CircleImageView;
import tutoapp.com.tutoappstudent.Objects.TutorShip;
import tutoapp.com.tutoappstudent.R;


public class TutorShipSetUp extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TutorShipSetUp() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static TutorShipSetUp newInstance(String param1, String param2) {
        TutorShipSetUp fragment = new TutorShipSetUp();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_tutor_ship_set_up, container, false);
        CircleImageView fabNext=(CircleImageView) rootView.findViewById(R.id.button_addc);
        Spinner SubjectSpinner=rootView.findViewById(R.id.spinner_materia);
        Spinner TopicSpinner=rootView.findViewById(R.id.spinner_tema);
        final Spinner MotivoSpinner=rootView.findViewById(R.id.spinner_motivo);

        fabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                TutorShip tutoria=new TutorShip();

                tutoria.setIdUserStudent("yo");
                tutoria.setTopicId("lo del spinner");
                tutoria.setMotivo(MotivoSpinner.getSelectedItem().toString());

                bundle.putSerializable("tutoria",tutoria);
                TutorShipDate fragment = new TutorShipDate();
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.container_tuto_request, fragment, "TAG");
                transaction.commit();

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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
