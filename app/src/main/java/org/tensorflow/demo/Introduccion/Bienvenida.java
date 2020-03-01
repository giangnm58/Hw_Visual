package org.tensorflow.demo.Introduccion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.tensorflow.demo.R;
//cuaderno y libreta pagina resumenv
public class Bienvenida extends AppCompatActivity {

    Button btn_entendido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida);
        btn_entendido=findViewById(R.id.btn_entendido_bienvenida);
        btn_entendido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),tutorial_primer_uso.class));
            }
        });
    }
}
