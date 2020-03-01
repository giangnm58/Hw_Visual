package org.tensorflow.tensorflowdemo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.tensorflow.demo.Classes;
import org.tensorflow.demo.Datos_Usuario;
import org.tensorflow.demo.Inicio_Comunicativo;
import org.tensorflow.demo.Opciones;
import org.tensorflow.demo.R;

import at.markushi.ui.CircleButton;

public class AM_Palabras_Rapidas extends AppCompatActivity {
    EditText txtfrase;
    TextView txtListaFrases;
    CircleButton btnAgregar;
    String texto="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_am__palabras__rapidas);
        Datos_Usuario conex = new Datos_Usuario(this, "DBUsuario_palabras", null, 3);
        SQLiteDatabase db = conex.getWritableDatabase();
        final Cursor cursor = db.rawQuery("SELECT Frase FROM PalabrasR", null);
        if(!cursor.moveToFirst()){
            db.execSQL("INSERT INTO PalabrasR (Frase) VALUES ('Soy sordo')");
            db.execSQL("INSERT INTO PalabrasR (Frase) VALUES ('Necesito Ayuda')");
        }
        txtfrase=findViewById(R.id.txtFrase);
        txtListaFrases=findViewById(R.id.txtListaFrases);
        btnAgregar=findViewById(R.id.btnAgregarFrase);
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt=txtfrase.getText().toString();
                Datos_Usuario conex = new Datos_Usuario(getApplicationContext(), "DBUsuario_palabras", null, 3);
                SQLiteDatabase db = conex.getWritableDatabase();
                db.execSQL("INSERT INTO PalabrasR (Frase) VALUES ('"+txt+"')");
                Toast.makeText(getApplicationContext(),"Se ha anexado.",Toast.LENGTH_LONG).show();
                llenar();
            }
        });
        llenar();
    }
    void llenar(){
        texto="";
        Datos_Usuario conex = new Datos_Usuario(getApplicationContext(), "DBUsuario_palabras", null, 3);
        SQLiteDatabase db = conex.getWritableDatabase();
        final Cursor cursor = db.rawQuery("SELECT Frase FROM PalabrasR", null);
        while (cursor.moveToNext()){
            texto+=cursor.getString(0)+"\n";
        }
        texto+="\n\n\nPara eliminar una palabra solo haga una pulsación larga sobre\n ella en el apartado de comunicar.";
        txtListaFrases.setText(String.valueOf(texto));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), Inicio_Comunicativo.class));
    }
}


