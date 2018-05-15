package tutoapp.com.tutoappstudent.FragmentsTutorequest;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import tutoapp.com.tutoappstudent.Adapter.CustomSuggestionsAdapter;
import tutoapp.com.tutoappstudent.Class.Tema;
import tutoapp.com.tutoappstudent.Objects.TutorShip;
import tutoapp.com.tutoappstudent.R;


public class TutorShipSetUp extends Fragment implements MaterialSearchBar.OnSearchActionListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static String Topic="";
    private static MaterialSearchBar materialSearchBar;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FirebaseAuth mAuth;
    private OnFragmentInteractionListener mListener;
    private SuggestionsAdapter suggestionsAdapter;
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
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_tutor_ship_set_up, container, false);
        ImageButton fabNext=(ImageButton) rootView.findViewById(R.id.button_addc);
        materialSearchBar = (MaterialSearchBar) rootView.findViewById(R.id.serachViewTema);
        materialSearchBar.setOnSearchActionListener(this);
        materialSearchBar.inflateMenu(R.menu.search);
        materialSearchBar.setCardViewElevation(10);
        materialSearchBar.enableSearch();
        suggestionsAdapter = new CustomSuggestionsAdapter(getLayoutInflater());
        List<Tema> temaList = new ArrayList<>();
        temaList.add(new Tema("Derivadas", "1"));
        temaList.add(new Tema("Integrales", "2"));
        temaList.add(new Tema("Matrices", "3"));
        temaList.add(new Tema("Determinantes", "4"));
        suggestionsAdapter.setSuggestions(temaList);
        materialSearchBar.setCustomSuggestionAdapter(suggestionsAdapter);
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                suggestionsAdapter.getFilter().filter(materialSearchBar.getText());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        final Spinner MotivoSpinner=rootView.findViewById(R.id.spinner_motivo);
        fabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                TutorShip tutoria=new TutorShip();

                tutoria.setTopicId(Topic);
                tutoria.setIdUserStudent(mAuth.getCurrentUser().getUid());
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

    @Override
    public void onSearchStateChanged(boolean enabled) {

    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
        //startSearch(text.toString(), true, null, true);
        materialSearchBar.setText(text.toString());
        Topic = text.toString();
    }

    @Override
    public void onButtonClicked(int buttonCode) {
        materialSearchBar.setText(Topic);
    }



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
