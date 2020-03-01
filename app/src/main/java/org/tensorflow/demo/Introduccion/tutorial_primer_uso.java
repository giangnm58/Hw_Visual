package org.tensorflow.demo.Introduccion;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import org.tensorflow.demo.Datos_Usuario;
import org.tensorflow.demo.Inicio_Comunicativo;
import org.tensorflow.demo.R;


import static org.tensorflow.demo.Clases.VariablesYDatos.Num_apartado;
import static org.tensorflow.demo.Clases.VariablesYDatos.Num_columna;
import static org.tensorflow.demo.Clases.VariablesYDatos.Tutorial_Primer_uso;

public class tutorial_primer_uso extends AppCompatActivity {

    TextView txtTitulo,txtSubTitulo;
    TextView txtDescripcion;
    Button btnSiguiente;
    VideoView vv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida_auditivo_1);
        txtDescripcion=findViewById(R.id.txtDescripcion_);
        txtTitulo=findViewById(R.id.txtTitulo_);
        vv=findViewById(R.id.video_tuto);
        txtSubTitulo=findViewById(R.id.txtSubTitulo_);

        btnSiguiente=findViewById(R.id.btn_tuto_sig);
        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Num_columna==(Tutorial_Primer_uso[1].length-1)){
                    try {
                        Datos_Usuario conex_primera = new Datos_Usuario(getApplicationContext(), "DBUsuarios", null, 3);
                        SQLiteDatabase db_pri = conex_primera.getReadableDatabase();
                        db_pri.execSQL("UPDATE Primeravezen SET Auditivo='1' WHERE Auditivo='0'");
                        db_pri.execSQL("UPDATE Primeravezen SET Comunicativo='1' WHERE Comunicativo ='0'");
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                    startActivity(new Intent(getApplication(), Inicio_Comunicativo.class));
                }else {
                    Num_columna++;
                    if(Num_columna==2||Num_columna==4||Num_columna==6){
                        Num_apartado++;
                    }
                    startActivity(new Intent(getApplication(), tutorial_primer_uso.class));
                }
            }
        });
        settearDatos();
    }

    private void settearDatos() {
        btnSiguiente.setText("Siguiente   "+(Num_columna+1)+"/9");
        txtTitulo.setText(                      Tutorial_Primer_uso [0] [Num_apartado]+"");
        txtSubTitulo.setText(                   Tutorial_Primer_uso [1] [Num_columna]+"");
        txtDescripcion.setText(                  Tutorial_Primer_uso [2] [Num_columna]+"");
       // gf.setImageResource(Integer.parseInt(Tutorial_Primer_uso [3] [Num_columna]+""));
        SettearVideo();
    }
    void SettearVideo(){
        try {
            String path = "android.resource://org.tensorflow.tensorflowdemo/" +
                    Integer.parseInt(Tutorial_Primer_uso [3] [Num_columna]+"");
            Uri uri = Uri.parse(path);
            vv.setVideoURI(uri);
            vv.setMediaController(new MediaController(this));
            vv.start();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}
