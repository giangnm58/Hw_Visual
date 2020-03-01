package org.tensorflow.demo.Introduccion;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import org.tensorflow.demo.Clases.VariablesYDatos;
import org.tensorflow.demo.Datos_Usuario;
import org.tensorflow.demo.Inicio_Visual;
import org.tensorflow.demo.R;

import java.util.Locale;

public class tutorial_primer_uso_visual extends AppCompatActivity {

    public TextToSpeech toSpeech;
    int result;
    ImageView im;
    int milliseconds = 2000;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_primer_uso_visual);

        im = findViewById(R.id.btnimage);
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),
                        "Si sirvo", Toast.LENGTH_SHORT).show();
                Hablar("Putos todos");
            }
        });

        try {
            toSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status == TextToSpeech.SUCCESS) {
                        result = toSpeech.setLanguage(Locale.getDefault());
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Feature not supported in your device", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        checkPermission();
        video();

        //toSpeech.speak("putos todos",TextToSpeech.QUEUE_FLUSH,null);
    }

    public void video() {
        countDownTimer = new CountDownTimer(milliseconds, 1000) { // adjust the milli seconds here
            public void onTick(long millisUntilFinished) {
            }
        public void onFinish () {
            Hablar(VariablesYDatos.Visual_Explicaion);
        }
    }.start();

}

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:" + getApplicationContext().getPackageName()));
                startActivity(intent);
            }
        }
    }

    private void Hablar(String query) {
        toSpeech.speak(query,TextToSpeech.QUEUE_FLUSH,null);
        while (toSpeech.isSpeaking()){
        }
        try {
            Datos_Usuario conex_primera = new Datos_Usuario(getApplicationContext(), "DBUsuarios", null, 3);
            SQLiteDatabase db_pri = conex_primera.getReadableDatabase();
            db_pri.execSQL("UPDATE Primeravezen SET Visual=1 WHERE Visual=0");
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
        startActivity(new Intent(getApplicationContext(), Inicio_Visual.class));
    }
}
