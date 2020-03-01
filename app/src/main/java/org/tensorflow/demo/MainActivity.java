package org.tensorflow.demo;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.tensorflow.demo.Introduccion.Bienvenida;
import org.tensorflow.demo.Introduccion.tutorial_primer_uso;
import org.tensorflow.demo.Introduccion.tutorial_primer_uso_visual;

import java.util.Calendar;

import at.markushi.ui.CircleButton;


public class MainActivity extends AppCompatActivity {
    EditText edit1, edit22, edit3;
    TextView edit2;
    //FloatingActionButton fl;
   CircleButton fl;
    Calendar cal;
    int dia, mes, año;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edit22=findViewById(R.id.editText3);
        edit3=findViewById(R.id.editText5);
edit1=findViewById(R.id.editText);
edit2=findViewById(R.id.editText2);
fl=findViewById(R.id.floatt2);
cal=Calendar.getInstance();
dia=cal.get(Calendar.DAY_OF_MONTH);
mes=cal.get(Calendar.MONTH);
año=cal.get(Calendar.YEAR);

fl.setOnClickListener(new View.OnClickListener() {
    @SuppressLint("ResourceType")
    @Override
    public void onClick(View view) {

        DatePickerDialog datePickerDialog=new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
edit2.setText(i2+"/"+(i1+1)+"/"+i);
            }
        },año,mes,dia);
        datePickerDialog.show();
    }
});

    }

        public void inicio (View view) {

            try{
            String l = edit1.getText().toString();
            String ll = edit2.getText().toString();
String numero=edit22.getText().toString();
String frase=edit3.getText().toString();

            if (l.length() > 0 && ll.length() > 0 && numero.length()>0 && frase.length()>0) {
                try {

                    Datos_Usuario conex_primera=new Datos_Usuario(getApplicationContext(),"DBUsuarios",null,3);
                    SQLiteDatabase db_pri=conex_primera.getReadableDatabase();
                    int aud=0,com=0,vis=0;

                    Cursor cursor=db_pri.rawQuery("SELECT * FROM Primeravezen",null);
                    /*if(!cursor.moveToNext()) {
                        db_pri.execSQL("INSERT INTO Primeravezen (Auditivo, Comunicativo, Visul)VALUES('0','0','0')");
                    }*/
                    //cursor=db_pri.rawQuery("SELECT * FROM Primeravezen",null);
                    /*while (cursor.moveToNext()){
                        aud=Integer.parseInt(cursor.getString(0));
                        com=Integer.parseInt(cursor.getString(1));
                        vis=Integer.parseInt(cursor.getString(2));
                        Log.i("AQUI","Au "+aud+" cm "+com+" vis "+vis);
                        Toast.makeText(getApplicationContext(), "Au "+aud+" cm "+com+" vis "+vis, Toast.LENGTH_SHORT).show();
                    }
                    /*try{
                        if(cursor.moveToFirst()){
                            NombreUsuario=cursor.getString(0);
                        }
                    }catch (Exception e){
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                    }*/
                    Datos_Usuario conex = new Datos_Usuario(this, "DBUsuario", null, 2);
                    SQLiteDatabase db = conex.getWritableDatabase();
                    db.execSQL("INSERT INTO Usuario (Discapacidad, Nombre, Nacimiento, Numero, Frase)VALUES('" + Opciones.disca + "','" + l + "','" + ll + "','"+numero+"','"+frase+"')");
                    if (Opciones.disca.equals("visual")) {
                       // if(vis==0){
                            startActivity(new Intent(getApplication(), tutorial_primer_uso_visual.class));
                        //}else {
                           Intent inte = new Intent(MainActivity.this, Inicio_Visual.class);
                            startActivity(inte);
                        //}
                    }
                    if (Opciones.disca.equals("auditivo")) {
                        //if(aud==0){
                           // startActivity(new Intent(getApplicationContext(), Bienvenida.class));
                        //}else{
                        Intent inte2 = new Intent(MainActivity.this, Inicio_Comunicativo.class);
                        startActivity(inte2);
                   //     }
                    }
                    if (Opciones.disca.equals("comunicativo")) {
                       // if(com==0){
                         //       startActivity(new Intent(getApplicationContext(), Bienvenida.class));
                       // }else{
                        Intent inte3 = new Intent(MainActivity.this, Inicio_Comunicativo.class);
                        startActivity(inte3);
                     //   }
                    }


                } catch (Exception e) {
                    Toast.makeText(this, "error"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Rellena todos los Espacios", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
                Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
            }
    }


}
