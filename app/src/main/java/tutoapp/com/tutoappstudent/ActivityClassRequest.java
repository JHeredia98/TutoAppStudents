package tutoapp.com.tutoappstudent;

import android.app.AlertDialog;
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

public class ActivityClassRequest extends AppCompatActivity {

    private ArrayList<Tema> ArrayListTopics;
    private ArrayList<Materia> ArrayListSubjects;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_request);

        final Spinner materiasSpinner = (Spinner) findViewById(R.id.spinner_materia);
        final Spinner temasSpinner = (Spinner) findViewById(R.id.spinner_tema);

        Spinner motivoSpinner = (Spinner) findViewById(R.id.spinner_motivo);
        Button setDateButton=findViewById(R.id.Dia);
        Button setTimeButton=findViewById(R.id.Hora);
        Button setPlaceButton=findViewById(R.id.Lugar);


        Button pedirClaseButton = (Button) findViewById(R.id.PedirClase);

        ArrayListTopics=new ArrayList<>();
        ArrayListSubjects=new ArrayList<>();
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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                                getApplicationContext(), android.R.layout.simple_spinner_item, SubListNames);

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

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                                        getApplicationContext(), android.R.layout.simple_spinner_item, TopicListNames);

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

        setDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Quiero que lanze un buen alert dialog con un calendario bien shingon
                LaunchAlertDialog(getLayoutInflater(),1);
            }
        });
        setPlaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LaunchAlertDialog(getLayoutInflater(),3);
            }
        });
        setTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LaunchAlertDialog(getLayoutInflater(),2);
            }
        });
    }
    public void LaunchAlertDialog(LayoutInflater inflater,int Value){
        View dialoglayout=null;
            switch(Value){
                case 1:
                    dialoglayout = inflater.inflate(R.layout.date_picker_ad, null);
                    DatePicker datePicker=dialoglayout.findViewById(R.id.datepicker);
                    break;
                case 2:
                    dialoglayout = inflater.inflate(R.layout.time_picker_ad, null);
                    TimePicker timePicker=dialoglayout.findViewById(R.id.timepicker);
                    break;
                case 3:
                    dialoglayout = inflater.inflate(R.layout.place_picker_ad, null);
                    break;
                default:
                    break;
            }
            Button Seguir=(Button) dialoglayout.findViewById(R.id.acceptBtn);//android:id="@+id/acceptBtn"



            final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
            builder.setView(dialoglayout);
            builder.show();
            builder.setCancelable(true);


            Seguir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

    }
}
