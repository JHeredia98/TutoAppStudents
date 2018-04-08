package tutoapp.com.tutoappstudent.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import tutoapp.com.tutoappstudent.Objects.Request;
import tutoapp.com.tutoappstudent.R;


public class RequestAdapter extends RecyclerView.Adapter{

    private final int SENT=0;
    private final int RECEIVED=1;
    private ArrayList<Request> mRequestList;
    private DatabaseReference mFriendReqDatabase;
    private DatabaseReference mRootRef;

    private FirebaseAuth mAuth;
    public RequestAdapter(ArrayList<Request> mRequestList) {

        this.mRequestList = mRequestList;
        mAuth = FirebaseAuth.getInstance();
        mFriendReqDatabase = FirebaseDatabase.getInstance().getReference().child("Friend_req");
        mRootRef = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;


        if (viewType == RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_list_request, parent, false);
            return new ReceivedRequestHolder(view);
        } else{
            //Log.i("otro","se inflara el otro");
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_list_request_sent, parent, false);
            return new SentRequestHolder(view);
        }



    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Request rqs= mRequestList.get(position);
        //Log.i("userwhosent",message.getFrom());
        if(holder.getItemViewType()==RECEIVED){//si es mio se encargara de inflar el layout de SenMessageHolder

            ((ReceivedRequestHolder) holder).onBindViewHolder((ReceivedRequestHolder)holder,position,this);

        }else{//se inflara el del mensaje recibido

            ((SentRequestHolder) holder).onBindViewHolder((SentRequestHolder) holder,position);


        }

    }

    @Override
    public int getItemViewType(int position) {
        Request rqs =  mRequestList.get(position);
        Log.i("typerqs",rqs.getType_request());
        if (rqs.getType_request().equals("sent")) {
            // If the current user is the sender of the message
            return SENT;
        } else {
            // If some other user sent the message
            return RECEIVED;
        }
    }

    @Override
    public int getItemCount() {
        return mRequestList.size();
    }


    private class SentRequestHolder extends RecyclerView.ViewHolder  {

        TextView TvRequestUsername;
        Button BtnCancelRequest;

        public SentRequestHolder(View view) {
            super(view);
            TvRequestUsername=(TextView) view.findViewById(R.id.layout_request_username);
            BtnCancelRequest=(Button) view.findViewById(R.id.layout_request_cancel_request);


        }


        public void onBindViewHolder(final SentRequestHolder viewHolder, final int i) {

            final Request rqs = mRequestList.get(i);

            DatabaseReference mUsersDatabase=FirebaseDatabase.getInstance().getReference().child("Users").child(rqs.getUsr_id());
            mUsersDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    TvRequestUsername.setText(dataSnapshot.child("name").getValue().toString());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            BtnCancelRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                RemoveFriendRequest(i);
                }
            });






        }

    }

    private class ReceivedRequestHolder extends RecyclerView.ViewHolder {

        TextView TvRequestUsername;
        Button BtnAcceptRequest;
        Button BtnDeclineRequest;
        public ReceivedRequestHolder(View view) {
            super(view);
            TvRequestUsername =(TextView) view.findViewById(R.id.layout_request_username);
            BtnAcceptRequest= (Button) view.findViewById(R.id.layout_request_accept_request);
            BtnDeclineRequest=(Button) view.findViewById(R.id.layout_request_decline_request);

        }

        public void onBindViewHolder(final ReceivedRequestHolder viewHolder, final int i, final RecyclerView.Adapter adapter) {

            final Request rqs = mRequestList.get(i);


            DatabaseReference mUsersDatabase=FirebaseDatabase.getInstance().getReference().child("Users").child(rqs.getUsr_id());
            mUsersDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    TvRequestUsername.setText(dataSnapshot.child("name").getValue().toString());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            BtnAcceptRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                AcceptFriendRequest(i,adapter);
                }
            });

            BtnDeclineRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RemoveFriendRequest(i);
                }
            });
        }

    }

    public void RemoveFriendRequest(final int position){

        Request rqstoremove=mRequestList.get(position);
        //primero se elimina el  del usuario logueado haciendo referencia a la Id del otro usuario
        DatabaseReference OurReference=mFriendReqDatabase = FirebaseDatabase.getInstance().getReference().child("Friend_req").child(mAuth.getCurrentUser().getUid());
        DatabaseReference UserReference=mFriendReqDatabase = FirebaseDatabase.getInstance().getReference().child("Friend_req").child(rqstoremove.getUsr_id());
        OurReference.child(rqstoremove.getUsr_id()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.i("delete status","mine //usr : "+position);
            }
        });
        UserReference.child(mAuth.getCurrentUser().getUid()).removeValue( ).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.i("delete status","that //usr : "+position);
            }
        });
        mRequestList.remove(position);
    }

    public void AcceptFriendRequest(final int position, final RecyclerView.Adapter adaptador){
        final String currentDate = DateFormat.getDateTimeInstance().format(new Date());

        Map friendsMap = new HashMap();

        Request rqs=mRequestList.get(position);
        friendsMap.put("Friends/" + mAuth.getCurrentUser().getUid() + "/" + rqs.getUsr_id() + "/date", currentDate);
        friendsMap.put("Friends/" + rqs.getUsr_id() + "/"  + mAuth.getCurrentUser().getUid() + "/date", currentDate);


        friendsMap.put("Friend_req/" + mAuth.getCurrentUser().getUid() + "/" + rqs.getUsr_id(), null);
        friendsMap.put("Friend_req/" + rqs.getUsr_id() + "/" + mAuth.getCurrentUser().getUid(), null);


        mRootRef.updateChildren(friendsMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {


                if(databaseError == null){
                    mRequestList.remove(position);
                    adaptador.notifyDataSetChanged();
                    /*mProfileSendReqBtn.setEnabled(true);
                    mCurrent_state = "friends";
                    mProfileSendReqBtn.setText("Eliminar");

                    mDeclineBtn.setVisibility(View.INVISIBLE);
                    mDeclineBtn.setEnabled(false);*/

                } else {

                    String error = databaseError.getMessage();




                }

            }
        });


    }

}
