package tutoapp.com.tutoappstudent.FragmentsTutorequest;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tutoapp.com.tutoappstudent.Class.GlobalTutors;
import tutoapp.com.tutoappstudent.Class.Tutor;
import tutoapp.com.tutoappstudent.Map.CameraUpdateAnimator;
import tutoapp.com.tutoappstudent.Objects.TutorShip;
import tutoapp.com.tutoappstudent.R;

public class TutorShipLocation extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ActivityCompat.OnRequestPermissionsResultCallback {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private String TAG = "HOLA";
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private TutorShip tutoria;
    private DatabaseReference databaseReferenceTutorship;
    private DatabaseReference databaseReferenceTutoRequest;
    private FirebaseAuth mAuth;
    private ArrayList<Tutor> TutosFromrequest;
    private ProgressDialog progressDialog;

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }

    @Override
    public void onConnected(Bundle arg0) {
// Once connected with google api, get the location
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_ship_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        tutoria= (TutorShip) getIntent().getSerializableExtra("tutoria");
        progressDialog= new ProgressDialog(this);
        if(tutoria==null){
            Toast.makeText(getApplicationContext(),"nulo",Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(getApplicationContext(),tutoria.toString(),Toast.LENGTH_LONG).show();
        }
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
        mAuth = FirebaseAuth.getInstance();
        databaseReferenceTutorship = FirebaseDatabase.getInstance().getReference().child("Tutorships");


        Button Finish=(Button) findViewById(R.id.buttonContinue);
        Finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Encontrando el mejor tutor para ti");

                progressDialog.show();
                LatLng latlng = mMap.getProjection().getVisibleRegion().latLngBounds.getCenter();
                final String key = databaseReferenceTutorship.push().getKey();
                tutoria.setLatitude(latlng.latitude);
                tutoria.setLongitude(latlng.longitude);

                databaseReferenceTutorship.child(key).setValue(tutoria).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(),"FB OK",Toast.LENGTH_SHORT).show();
                        OkHttpClient okHttpClient = new OkHttpClient();
                        RequestBody requestBody = new FormBody.Builder()
                                .add("tema", tutoria.getTopicId())
                                .add("latitude", String.valueOf(tutoria.getLatitude()))
                                .add("longitude", String.valueOf(tutoria.getLongitude()))
                                .add("datetime", tutoria.getDateString())
                                .add("duration", "2")
                                .add("iduser", tutoria.getIdUserStudent())
                                .add("motive", tutoria.getMotivo())
                                .add("idrequestfb", tutoria.getIdUserStudent())
                                .add("idtutorshipfb", key)
                                .build();
                        Request request = new Request.Builder()
                                .url("http://ruedadifusion.com/TutoApp/Tuto/RequestTutorship.php")
                                .post(requestBody)
                                .build();
                        okHttpClient.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, final Response response) throws IOException {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(),"HTTP OK",Toast.LENGTH_SHORT).show();
                                    }
                                });
                                String bodyString = response.body().string();
                                Log.i("stringresponse",bodyString);
                                Gson gsito = new Gson();
                                GlobalTutors gTutores=gsito.fromJson(bodyString, GlobalTutors.class);
                                TutosFromrequest=gTutores.getTutores();// una vez respondamos borramos en tuto reques los docentes que hayan recibido la solicitud
                                /***/
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(),"FIREBASE TRIGGER",Toast.LENGTH_SHORT).show();
                                    }
                                });
                                databaseReferenceTutorship.child(key).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        TutorShip tutoria = dataSnapshot.getValue(TutorShip.class);
                                        if(tutoria.getStatus()==1){//quiere decir que fue aceptada
                                            //String tutouid=(String) dataSnapshot.child("Tuto").getValue();
                                            for(int i=0;i<TutosFromrequest.size();i++){
                                                //Toast.makeText(getApplicationContext(),"tutor  "+tutoria.getIdTuto(),Toast.LENGTH_LONG).show();
                                                //Toast.makeText(getApplicationContext(),"tutor  "+TutosFromrequest.get(i).getNombre(),Toast.LENGTH_LONG).show();
                                                /*if(!tutoria.getIdTuto().equals(TutosFromrequest.get(i).getCodFirebase())){
                                                    //FirebaseDatabase.getInstance().getReference().child("Tutorships");
                                                    //se hara para remover el tutorequest
                                                    databaseReferenceTutoRequest= FirebaseDatabase.getInstance().getReference().child("TutoRequest").child(TutosFromrequest.get(i).getCodFirebase());
                                                }*/
                                            }
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(getApplicationContext(), "tutor encontrado", Toast.LENGTH_LONG);
                                                }
                                            });
                                        }

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });


                                /***/

                            }
                        });

                    }
                });


            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //aqui movemos la camara del mapa
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_LOCATION);

        }
        mFusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    Toast.makeText(getApplicationContext(),"lat "+location.getLatitude()+" --- loc "+location.getLongitude(),Toast.LENGTH_SHORT).show();
                    LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(loc).title("Casa"));
                    CameraUpdateAnimator animator = new CameraUpdateAnimator(mMap, null); // Parameters: a GoogleMap instance and an OnCameraIdleListener to return the control to (can be null)
                    animator.add(CameraUpdateFactory.newLatLngZoom(loc, 15f), false, 0); // Move the camera without delay
                    animator.execute();
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
                }
            }
        });



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION :
                if (grantResults.length > 0  &&  grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(this, "Permission Denied for the Location",
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


}
