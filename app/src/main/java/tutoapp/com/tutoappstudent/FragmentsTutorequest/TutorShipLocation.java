package tutoapp.com.tutoappstudent.FragmentsTutorequest;

import android.Manifest;
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
import com.google.android.gms.tasks.OnSuccessListener;

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
        if(tutoria==null){
            Toast.makeText(getApplicationContext(),"nulo",Toast.LENGTH_LONG).show();

        }
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();

        mGoogleApiClient.connect();
        Button Finish=(Button) findViewById(R.id.buttonContinue);
        Finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LatLng latlng = mMap.getProjection().getVisibleRegion().latLngBounds.getCenter();

                tutoria.setLocation(latlng);
                Toast.makeText(getApplicationContext(),"VALUE: "+tutoria.getLongitude()+" --"+tutoria.getLatitude(),Toast.LENGTH_SHORT).show();

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
