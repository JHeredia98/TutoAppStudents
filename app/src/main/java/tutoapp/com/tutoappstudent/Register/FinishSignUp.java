package tutoapp.com.tutoappstudent.Register;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tutoapp.com.tutoappstudent.HomeActivity;
import tutoapp.com.tutoappstudent.R;

public class FinishSignUp extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<String> data;
    private OnFragmentInteractionListener mListener;
    private Bundle bundle;
    public FinishSignUp() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FinishSignUp.
     */
    // TODO: Rename and change types and number of parameters
    public static FinishSignUp newInstance(String param1, String param2) {
        FinishSignUp fragment = new FinishSignUp();
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
            bundle=getArguments();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview= inflater.inflate(R.layout.fragment_finish_sign_up, container, false);
        Button NextStep= rootview.findViewById(R.id.finishsignup_buttonfinish);
        NextStep.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                OkHttpClient clientefirebase=new OkHttpClient();
                RequestBody requestBodyfirebase = new FormBody.Builder()
                        .add("firebase",bundle.getString("firebaseid"))
                        .build();
                Request requestfirebase = new Request.Builder()
                        .url("www.ruedadifusion.com/TutoApp/Tuto/InsertFirebaseUID.php")
                        .post(requestBodyfirebase)
                        .build();
                clientefirebase.newCall(requestfirebase).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        OkHttpClient clientuser=new OkHttpClient();
                        RequestBody requestBody = new FormBody.Builder()
                                .add("email",bundle.getString("email"))
                                .add("password",bundle.getString("password"))
                                .add("lastname", bundle.getString("lastname"))
                                .add("username",bundle.getString("username"))
                                .add("gender", bundle.getString("gender"))
                                .add("firebase", bundle.getString("firebaseid"))
                                .add("usertype", "1")
                                .add("name", bundle.getString("name"))
                                .build();
                        Request request = new Request.Builder()
                                .url("/TutoApp/Tuto/InsertUser.php")
                                .post(requestBody)
                                .build();
                        clientuser.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                Intent i=new Intent();
                                i.setClass(getContext(), HomeActivity.class);
                                startActivity(i);
                                getActivity().finish();
                            }
                        });
                    }
                });




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
