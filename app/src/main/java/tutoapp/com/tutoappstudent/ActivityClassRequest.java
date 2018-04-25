package tutoapp.com.tutoappstudent;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
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
import tutoapp.com.tutoappstudent.FragmentsTutorequest.TutorShipSetUp;


public class ActivityClassRequest extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_request);

        Realm realm= Realm.getDefaultInstance();
        if(realm.isEmpty()) {



            OkHttpClient client = new OkHttpClient();
            RequestBody formbody=new FormBody.Builder()
                    .build();

            Request request =new Request.Builder()
                    .url("http://www.ruedadifusion.com/BuscarMaterias.php")
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    //se muestra un mensaje de que no funciono
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    //se colocan el el realm
                }
            });

        }
        TutorShipSetUp newFragment=new TutorShipSetUp();
        Bundle args=new Bundle();
        newFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.container_tuto_request, newFragment).commit();

    }
}
