package tutoapp.com.tutoappstudent.Fragments;

import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tutoapp.com.tutoappstudent.Class.Materia;
import tutoapp.com.tutoappstudent.Class.Materias;
import tutoapp.com.tutoappstudent.Class.Tema;
import tutoapp.com.tutoappstudent.Class.Temas;
import tutoapp.com.tutoappstudent.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ClassRequest.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ClassRequest#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClassRequest extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ArrayList<Tema> ArrayListTopics;
    private ArrayList<Materia> ArrayListSubjects;
    public ClassRequest() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TutosNotCompleted.
     */
    // TODO: Rename and change types and number of parameters
    public static ClassRequest newInstance(String param1, String param2) {
        ClassRequest fragment = new ClassRequest();
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
        ArrayListTopics=new ArrayList<>();
        ArrayListSubjects=new ArrayList<>();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_class_request, container, false);
        final Spinner materiasSpinner = (Spinner) view.findViewById(R.id.spinner_materia);
        final Spinner temasSpinner = (Spinner) view.findViewById(R.id.spinner_tema);

        Spinner motivoSpinner = (Spinner) view.findViewById(R.id.spinner_motivo);

        Button pedirClaseButton = (Button) view.findViewById(R.id.PedirClase);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);
        final AlertDialog d = builder.show();


        final ArrayList<Materia> SubList=new ArrayList<>();
        final List<String> SubListNames=new ArrayList<>();
        final ArrayList<Tema> TopicList=new ArrayList<>();
        final List<String> TopicListNames=new ArrayList<>();
        OkHttpClient client = new OkHttpClient();
        RequestBody formbody=new FormBody.Builder()
                .build();

        Request request =new Request.Builder()
                .url("http://www.ruedadifusion.com/BuscarMaterias.php")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Materias materias;
                final String Ans = response.body().string();
                Gson gson = new Gson();
                materias = gson.fromJson(Ans, Materias.class);
                for (int i = 0; i <materias.getMaterias().size() ; i++) {
                    SubList.add(materias.getMaterias().get(i));
                    SubListNames.add(materias.getMaterias().get(i).getNombreMateria());
                    ArrayListSubjects.add(materias.getMaterias().get(i));
                }
                Log.i("sizelist","valor de la lista "+SubListNames.size());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                                getContext(), android.R.layout.simple_spinner_item, SubListNames);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        materiasSpinner.setAdapter(adapter);
                    }
                });


            }
        });



        materiasSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                OkHttpClient client = new OkHttpClient();
                RequestBody formbody=new FormBody.Builder()
                        .add("idMateria",ArrayListSubjects.get(i).getIdMateria())
                        .build();

                Request request =new Request.Builder()
                        .url("http://www.ruedadifusion.com/BuscarTema.php")
                        .post(formbody)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        Temas temas;
                        final String Ans = response.body().string();
                        Gson gson = new Gson();
                        temas = gson.fromJson(Ans, Temas.class);

                        TopicList.clear();
                        TopicListNames.clear();
                        ArrayListTopics.clear();

                        for (int i = 0; i <temas.getTemas().size() ; i++) {
                            TopicList.add(temas.getTemas().get(i));
                            TopicListNames.add(temas.getTemas().get(i).getNombreTema());
                            ArrayListTopics.add(temas.getTemas().get(i));
                        }
                        Log.i("sizelist","valor de la lista "+TopicListNames.size());

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                                        getContext(), android.R.layout.simple_spinner_item, TopicListNames);

                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                adapter.notifyDataSetChanged();
                                temasSpinner.setAdapter(adapter);
                            }
                        });
                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        pedirClaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
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
