package tutoapp.com.tutoappstudent.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import tutoapp.com.tutoappstudent.Adapter.RequestAdapter;
import tutoapp.com.tutoappstudent.Objects.Request;
import tutoapp.com.tutoappstudent.R;


public class RequestsFragment extends Fragment {

    private RecyclerView mRequestList;

    private  DatabaseReference mFriendReqDatabase;
    private FirebaseAuth mAuth;

    private String mCurrent_user_id;
    RecyclerView.Adapter mAdapter;
    private View mMainView;
    private int itemPos = 0;

    private String mLastKey = "";
    private String mPrevKey = "";
    DatabaseReference mRootRef;
    private final ArrayList<Request> listaSolicitudes = new ArrayList<>();
    private ArrayList<Integer> indexToDelete;
    public RequestsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mMainView= inflater.inflate(R.layout.fragment_requests, container, false);
        mRequestList= mMainView.findViewById(R.id.fragment_request_recylcer_view);
        mAuth = FirebaseAuth.getInstance();

        mCurrent_user_id = mAuth.getCurrentUser().getUid();

        mRootRef = FirebaseDatabase.getInstance().getReference();
        mFriendReqDatabase = FirebaseDatabase.getInstance().getReference().child("Friend_req").child(mCurrent_user_id);


        loadRequest();
        mAdapter = new RequestAdapter(listaSolicitudes);
        //Toast.makeText(getContext(),wow,Toast.LENGTH_SHORT).show();
        mRequestList.setHasFixedSize(true);
        mRequestList.setLayoutManager(new LinearLayoutManager(getContext()));

        mRequestList.setAdapter(mAdapter);
        return mMainView;
    }


    @Override
    public void onStart() {
        super.onStart();


    }
    private void loadMoreRequest() {

        DatabaseReference messageRef = mRootRef.child("friend_req").child(mCurrent_user_id);

        Query messageQuery = messageRef;
        Toast.makeText(getContext(), messageRef.toString(), Toast.LENGTH_SHORT).show();
        messageQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                Request request = dataSnapshot.getValue(Request.class);
                String messageKey = dataSnapshot.getKey();

                if (!mPrevKey.equals(messageKey)) {

                    listaSolicitudes.add(itemPos++, request);

                } else {

                    mPrevKey = mLastKey;

                }


                if (itemPos == 1) {

                    mLastKey = messageKey;

                }


                Log.d("TOTALKEYS", "Last Key : " + mLastKey + " | Prev Key : " + mPrevKey + " | Message Key : " + messageKey);

                mAdapter.notifyDataSetChanged();

                //mRefreshLayout.setRefreshing(false);

                ///mLinearLayout.scrollToPositionWithOffset(10, 0);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    private void loadRequest() {

        DatabaseReference messageRef = mRootRef.child("Friend_req").child(mCurrent_user_id);

        Query messageQuery = messageRef;

        Log.i("query",messageQuery.toString());
        messageQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                HashMap<String,Object> mapaSolicitud = (HashMap<String, Object>) dataSnapshot.getValue();
                Log.i("SNAP",dataSnapshot.getKey()+""+dataSnapshot.getValue()+" -: "+s);
                String valor=dataSnapshot.getValue().toString();

                String [] separated=valor.split("=");
                valor=separated[1];
                valor = valor.substring(0, valor.length() - 1);
                Log.i("value",valor);

                Request newRequest=new Request(dataSnapshot.getKey(),valor);
                Boolean apperas=false;
                for (int i = 0; i <listaSolicitudes.size() ; i++) {
                    Request NowIteratorRequest=listaSolicitudes.get(i);
                    if(NowIteratorRequest.getUsr_id().equals(newRequest.getUsr_id())){

                        apperas=true;
                        break;
                    }
                }
                if(apperas==false){

                    listaSolicitudes.add(newRequest);
                }

                itemPos++;

                if(itemPos == 1){

                    String messageKey = dataSnapshot.getKey();

                    mLastKey = messageKey;
                    mPrevKey = messageKey;

                }
                for (Map.Entry<String,Object> entry : mapaSolicitud.entrySet()) {
                    Log.i("map","clave=" + entry.getKey() + ", valor=" + entry.getValue());

                }
                if(listaSolicitudes.size()>0){
                    Log.i("dimlist",listaSolicitudes.size()+"");
                }else{Log.i("dimlist","empty");}

                mAdapter.notifyDataSetChanged();

                //mMessagesList.scrollToPosition(ListMessages.size() - 1);

                //mRefreshLayout.setRefreshing(false);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                HashMap<String,Object> mapaSolicitud = (HashMap<String, Object>) dataSnapshot.getValue();
                Log.i("SNAP",dataSnapshot.getKey()+""+dataSnapshot.getValue());
                String valor=dataSnapshot.getValue().toString();

                String [] separated=valor.split("=");
                valor=separated[1];
                valor = valor.substring(0, valor.length() - 1);
                Log.i("value",valor);

                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

}

