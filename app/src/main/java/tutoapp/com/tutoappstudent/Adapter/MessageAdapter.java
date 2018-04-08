package tutoapp.com.tutoappstudent.Adapter;

import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.List;

import tutoapp.com.tutoappstudent.Objects.Messages;
import tutoapp.com.tutoappstudent.R;


public class MessageAdapter extends RecyclerView.Adapter{

    private final int IsMine=1;
    private final int FromAnother=0;
    private List<Messages> mMessageList;
    private DatabaseReference mUserDatabase;

    final String pivote;
    private FirebaseAuth mAuth;
    public MessageAdapter(List<Messages> mMessageList) {

        this.mMessageList = mMessageList;
        mAuth = FirebaseAuth.getInstance();
        pivote= mAuth.getCurrentUser().getUid();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == IsMine) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_single_layout, parent, false);
            return new SentMessageHolder(view);
        } else{
            //Log.i("otro","se inflara el otro");
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_that_layout, parent, false);
            return new ReceivedMessageHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Messages message=mMessageList.get(position);
        Log.i("userwhosent",message.getFrom());
        if(holder.getItemViewType()==IsMine){//si es mio se encargara de inflar el layout de SenMessageHolder

            ((SentMessageHolder) holder).onBindViewHolder((SentMessageHolder) holder,position);

        }else{//se inflara el del mensaje recibido
            ((ReceivedMessageHolder) holder).onBindViewHolder((ReceivedMessageHolder)holder,position);

        }

    }

    @Override
    public int getItemViewType(int position) {
        Messages message =  mMessageList.get(position);
        if (message.getFrom().equals(pivote)) {
            // If the current user is the sender of the message
            return IsMine;
        } else {
            // If some other user sent the message
            return FromAnother;
        }
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }


    private class SentMessageHolder extends RecyclerView.ViewHolder  {

        TextView TvtMessage;
        TextView TvHour;
        public SentMessageHolder(View view) {
            super(view);
            TvtMessage=(TextView) view.findViewById(R.id.message_text_layout);
            TvHour=(TextView) view.findViewById(R.id.time_text_layout);


        }


        public void onBindViewHolder(final SentMessageHolder viewHolder, int i) {

            Messages c = mMessageList.get(i);

            String from_user = c.getFrom();
            String message_type = c.getType();


            mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(from_user);

            mUserDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {//cambiar alguna informacion del perfil del usuario como imagen o el nombre


                    String name = dataSnapshot.child("name").getValue().toString();
                    String image = dataSnapshot.child("thumb_image").getValue().toString();

                    //viewHolder.TvContactName.setText(name);

                    /*Picasso.with(viewHolder.profileImage.getContext()).load(image)
                            .placeholder(R.drawable.default_avatar).into(viewHolder.profileImage);*/

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            if(message_type.equals("text")) {// es un texto entonces se seteara en el textview

                viewHolder.TvtMessage.setText(c.getMessage());
                viewHolder.TvHour.setText(DateFormat.format("hh:mm:ss", new Date(c.getTime())).toString());
                //viewHolder.messageImage.setVisibility(View.INVISIBLE);


            } else {//quiere decir que hay un link y ese link lo abrira el picasso

                /*viewHolder.messageText.setVisibility(View.INVISIBLE);
                Picasso.with(viewHolder.profileImage.getContext()).load(c.getMessage())
                        .placeholder(R.drawable.default_avatar).into(viewHolder.messageImage);*/

            }

        }

    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {

        TextView TvMessage;
        TextView TvHour;
        TextView TvContactName;
        public ReceivedMessageHolder(View view) {
            super(view);
            TvMessage=(TextView) view.findViewById(R.id.message_text_layout);
            TvHour=(TextView) view.findViewById(R.id.time_text_layout);
            TvContactName=(TextView) view.findViewById(R.id.name_text_layout);

        }

        public void onBindViewHolder(final ReceivedMessageHolder viewHolder, int i) {

            Messages c = mMessageList.get(i);

            String from_user = c.getFrom();
            String message_type = c.getType();


            mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(from_user);

            mUserDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    String name = dataSnapshot.child("name").getValue().toString();
                    String image = dataSnapshot.child("thumb_image").getValue().toString();

                    viewHolder.TvContactName.setText(name);



                    /*Picasso.with(viewHolder.profileImage.getContext()).load(image)
                            .placeholder(R.drawable.default_avatar).into(viewHolder.profileImage);*/

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            if(message_type.equals("text")) {

                viewHolder.TvMessage.setText(c.getMessage());
                viewHolder.TvHour.setText(DateFormat.format("hh:mm:ss", new Date(c.getTime())).toString());
                //viewHolder.messageImage.setVisibility(View.INVISIBLE);


            } else {

                /*viewHolder.messageText.setVisibility(View.INVISIBLE);
                Picasso.with(viewHolder.profileImage.getContext()).load(c.getMessage())
                        .placeholder(R.drawable.default_avatar).into(viewHolder.messageImage);*/

            }

        }

    }
}
