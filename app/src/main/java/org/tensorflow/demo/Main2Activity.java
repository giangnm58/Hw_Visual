package org.tensorflow.demo;

import android.app.Fragment;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.tensorflow.demo.Adaptadores.Adaptador_Titulos;
import org.tensorflow.demo.Adaptadores.TitulosA;
import org.tensorflow.demo.Clases.VariablesYDatos;

import java.util.ArrayList;
import java.util.Locale;

public class Main2Activity extends AppCompatActivity {
    public static android.support.v4.app.Fragment f=null;
    ArrayList<TitulosA>datos;
    //ArrayList<String>datos2;
    RecyclerView recyclerView, recyclerView2;
    ImageButton hablar,borrar;
    public static String TEMA="";
    int result;
    public static TextView textView;
    public TextToSpeech toSpeech;
    String frase="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        try {
            toSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status == TextToSpeech.SUCCESS) {
                        result = toSpeech.setLanguage(Locale.getDefault());
                    } else {

                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        hablar=findViewById(R.id.hablar);

        textView=findViewById(R.id.texttt);
        recyclerView=findViewById(R.id.recycleropciones);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        datos=new ArrayList<>();
        llenarrecycler();
        borrar=findViewById(R.id.borrar);
        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VentanaSenas.texto="";
                frase="";
                textView.setText("");
            }
        });
        hablar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frase=textView.getText().toString();
                toSpeech.speak(frase,TextToSpeech.QUEUE_FLUSH, null);
            }
        });
        Adaptador_Titulos adaptador_titulos=new Adaptador_Titulos(datos);

        recyclerView.setAdapter(adaptador_titulos);

        adaptador_titulos.setOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VentanaSenas.limpiarrecycler();
                TEMA=datos.get(recyclerView.getChildAdapterPosition(v)).getTituloo();

                if(TEMA.equalsIgnoreCase("Casa")){
                    VentanaSenas.desaparecerimagen();
                    VentanaSenas.recyclerSubTCasa();
                    VentanaSenas.llenarrecycler();

                }
                if (TEMA.equalsIgnoreCase("Hospital")){
                    VentanaSenas.desaparecerimagen();
                    VentanaSenas.recyclerSubTHospital();
                    VentanaSenas.llenarrecycler();
                }
                if (TEMA.equalsIgnoreCase("Escuela")){
                    VentanaSenas.desaparecerimagen();
                    VentanaSenas.recyclerSubTEscuela();
                    VentanaSenas.llenarrecycler();
                }
            }
        });
        mostrar();
    }

    private void mostrar() {
        f = new VentanaSenas();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmm,f).commit();
    }

    private void llenarrecycler() {
        datos.add(new TitulosA("Casa",R.drawable.fondo1, R.drawable.cassa));
        datos.add(new TitulosA("Hospital",R.drawable.fondo2,R.drawable.hospitall));
        datos.add(new TitulosA("Escuela",R.drawable.fondo3,R.drawable.escuelaa));

    }
}


