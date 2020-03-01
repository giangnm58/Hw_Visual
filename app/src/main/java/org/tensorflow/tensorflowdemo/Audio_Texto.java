package org.tensorflow.tensorflowdemo;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.Toast;

import org.tensorflow.demo.Adappters;
import org.tensorflow.demo.Adaptadores.AdaptadorAssistent;
import org.tensorflow.demo.Clases.Elemen;
import org.tensorflow.demo.Clases.VariablesYDatos;
import org.tensorflow.demo.Classes;
import org.tensorflow.demo.Datos_Usuario;
import org.tensorflow.demo.Main2Activity;
import org.tensorflow.demo.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import static org.tensorflow.demo.Clases.VariablesYDatos.VerbosInfinitivo;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Audio_Texto.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Audio_Texto#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Audio_Texto extends Fragment {
    RecyclerView rv, recyclerView_palabras;
    Adappters adappters_palabras=null;
    int mayor=0,contador=1;
    String palMayor="";
    ArrayList<Elemen> lista;
    ArrayList<Classes> lista_palabras;
    SearchView sv;
    String Palabra="";
    Switch swalfa;
    int result;
    FloatingActionButton btnOir,imageButton,btnEliminarConversa;
    public TextToSpeech toSpeech;
    SpeechRecognizer mSpeechRecognizer;
    String NombreUsuario="",NombreAsistente="Mundo";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Audio_Texto() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Audio_Texto.
     */
    // TODO: Rename and change types and number of parameters
    public static Audio_Texto newInstance(String param1, String param2) {
        Audio_Texto fragment = new Audio_Texto();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =inflater.inflate(R.layout.fragment_audio__texto, container, false);

        swalfa=view.findViewById(R.id.swalfabeta);
        imageButton=view.findViewById(R.id.botnteclado);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), Main2Activity.class);
                startActivity(intent);
            }
        });
        //
        Datos_Usuario conex=new Datos_Usuario(getActivity(),"DBUsuario",null,2);
        SQLiteDatabase db=conex.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT Nombre FROM Usuario",null);
        try{
            if(cursor.moveToFirst()){
                NombreUsuario=cursor.getString(0);
            }
        }catch (Exception e){
            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
        }
        recyclerView_palabras=view.findViewById(R.id.recycleropciones);
        lista_palabras=new ArrayList<>();
        adappters_palabras=new Adappters(lista_palabras);
        adappters_palabras.setOnClickListener2(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Palabra=lista_palabras.get(recyclerView_palabras.getChildAdapterPosition(v)).getTexto();
//VentanaSenas.llenarRecycler();
                //Toast.makeText(Main2Activity.this, ""+tema, Toast.LENGTH_SHORT).show();
                Decir_y_Mostrar(Palabra);
            }
        });
        adappters_palabras.setOnLongClickListener2(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final String P=lista_palabras.get(recyclerView_palabras.getChildAdapterPosition(v)).getTexto();
                //Toast.makeText(getContext(),P,Toast.LENGTH_SHORT).show();


                final CharSequence[] opciones={"Eliminar frase","Cancelar"};
                final AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setTitle("Eliminar palabra");
                builder.setIcon(R.drawable.ic_action_tache);
                builder.setItems(opciones, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (opciones[i].equals("Cancelar")){
                        }else{
                            Datos_Usuario conex = new Datos_Usuario(getContext(), "DBUsuario_palabras", null, 3);
                            SQLiteDatabase db = conex.getWritableDatabase();
                            int ij = db.delete("PalabrasR", "Frase='"+P+"'", null);
                            if(ij>0){
                                Toast.makeText(getContext(), "Se ha eliminado.", Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(getContext(), "Generada por defecto\nNo es posible eliminarla.", Toast.LENGTH_LONG).show();
                            }

                            llenarRecycler();
                        }
                    }
                });
                builder.show();
                return false;
            }
        });

        lista = new ArrayList<>();
        LinearLayoutManager linearLayoutManager_rapidas=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView_palabras.setLayoutManager(linearLayoutManager_rapidas);
        recyclerView_palabras.setHasFixedSize(true);
        recyclerView_palabras.setAdapter(adappters_palabras);
        llenarRecycler();




        rv = (RecyclerView) view.findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());

        //LinearLayoutManager llm = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        //y esto en el xml
        //android:scrollbars="horizontal"
        rv.setLayoutManager(llm);

        cargarConversacion();

        sv = view.findViewById(R.id.searchview);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
               Decir_y_Mostrar(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });



//Tospeech

        try {
            toSpeech = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status == TextToSpeech.SUCCESS) {
                        result = toSpeech.setLanguage(Locale.getDefault());
                    } else {
                        Toast.makeText(getActivity(), "Feature not supported in your device", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        checkPermission();

//recognizer
        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(getContext());


        final Intent mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault());


        mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

                Log.i("AQUI", "ready");
            }

            @Override
            public void onBeginningOfSpeech() {
                Log.i("AQUI", "begin");
            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {
                Log.i("AQUI", "buffer");
            }

            @Override
            public void onEndOfSpeech() {

                Log.i("AQUI", "end");

            }

            @Override
            public void onError(int i) {
                Log.i("AQUI", "error" + i);
            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResults(Bundle bundle) {
                //getting all the matches
                ArrayList<String> matches = bundle
                        .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                //displaying the first match
                if (matches != null) {
                    String input = matches.get(0);
                 //   Toast.makeText(getContext(),input,Toast.LENGTH_LONG).show();
                    if(swalfa.isChecked()){
                        input = remplazarComunes(input);
                   //     Toast.makeText(getContext(),input,Toast.LENGTH_LONG).show();
                        input= comparar(input.split(" "));
                    }
                    Elemen mess=new Elemen();
                    mess.setLado(false);
                    mess.setNombre(NombreAsistente);
                    mess.setMensaje(input);
                    VariablesYDatos.Conversacion.add(mess);
                    AdaptadorAssistent adapter=new AdaptadorAssistent(VariablesYDatos.Conversacion);
                    rv.setAdapter(adapter);
                }
            }


            @Override
            public void onPartialResults(Bundle bundle) {
                ArrayList<String> matches = bundle
                        .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                Log.i("AQUI", "partial");
            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });
        final Handler handlerSpeech = new Handler();
        Timer timerSpeech = new Timer();
        TimerTask taskSpeech = new TimerTask() {
            @Override
            public void run() {
                handlerSpeech.post(new Runnable() {
                    public void run() {
                        try {
                        //    mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                        } catch (Exception e) {
                            Log.e("error", e.getMessage());
                        }
                    }
                });
            }
        };
        timerSpeech.schedule(taskSpeech, 0, 8000);

        btnOir =view.findViewById(R.id.btnOir);
        btnOir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    mSpeechRecognizer.stopListening();
                mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
            }
        });

        btnEliminarConversa=view.findViewById(R.id.btnEliminarconversacion);
        btnEliminarConversa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VariablesYDatos.Conversacion.clear();
                cargarConversacion();
            }
        });
//

        return view;
    }

    private void cargarConversacion() {
            AdaptadorAssistent adapter = new AdaptadorAssistent(VariablesYDatos.Conversacion);
            rv.setAdapter(adapter);

    }

    private void Hablar(String query) {
    toSpeech.speak(query,TextToSpeech.QUEUE_FLUSH,null);
    while (toSpeech.isSpeaking()){
    }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:" + getActivity().getPackageName()));
                startActivity(intent);

            }
        }
    }
    String remplazarComunes(String frase){
        frase=frase.replace("dé","de");
        frase=frase.replace("qué","que");
        frase=frase.replace("sé","yo saber");
        frase=frase.replace("más","mas");

        String [] c= frase.split(" ");
        for(int i = 0;i<c.length;i++){
            //pasado
            if(c[i].endsWith("í")){
                c[i]=c[i] +" ayer";
            }
            //futuro

            if((c[i].endsWith("é")||c[i].endsWith("ría")||c[i].endsWith("ás"))){
                if(!c[i].equalsIgnoreCase("qué")&&!c[i].equalsIgnoreCase("sé")&&
                        !c[i].equalsIgnoreCase("estás")&&!c[i].equalsIgnoreCase("más")
                        &&!c[i].equalsIgnoreCase("demás")){
                    c[i]=c[i] +" mañana";
                }
            }
        }
        frase ="";
        for(int i = 0;i<c.length;i++){
            frase+= c[i]+ " ";
        }

        frase=LimpiarCadenas(frase);
        frase=frase.replace("yo","");

        frase=frase.replace("abran","abrir");
        frase=frase.replace("has","tu haber");
        frase=frase.replace("ire","ir");
        frase=frase.replace("duele","doler");
        frase=frase.replace("fui","ir ");
        frase=frase.replace("iria","ir");
        frase=frase.replace("fue","ir ayer");
        frase=frase.replace("ire","ir");
        frase=frase.replace("tocarte","tocar yo a tu");
        frase=frase.replace("sigue","seguir");

        frase=frase.replace("piensas","pensar");
        frase=frase.replace("pienso","pensar");
        frase=frase.replace("buenas noches","buenas noches");
        frase=frase.replace("buena noche","buenas noches");
        frase=frase.replace("buenas taredes","buenas tardes");
        frase=frase.replace("buena tarde","buenas tardes");
        frase=frase.replace("buenos dias","buenos dias");
        frase=frase.replace("buen dia","buenos dias");
        frase=frase.replace("como estas","como estar");
        frase=frase.replace("como estan","como estar");
        frase=frase.replace("como esta","comoestas");
        frase=frase.replace("cual es tu nombre","tu nombre");
        frase=frase.replace("cuál es tu nombre","tu nombre");
        frase=frase.replace("como te llamas","tu nombre");
        frase=frase.replace("cómo te llamas","tu nombre");
        frase=frase.replace("dime tu nombre","tu nombre");
        frase=frase.replace("de nada","de nada");
        frase=frase.replace("gusto en conocerte","gusto en conocerte");
        frase=frase.replace("hasta luego","nos vemos");
        frase=frase.replace("nos vemos","nos vemos");
        frase=frase.replace("adios","nos vemos");

        frase=frase.replace("a dios","nos vemos");
        frase=frase.replace("medio","mas o menos");
        frase=frase.replace("por que","porque");
        frase=frase.replace("para que","para que");
        frase=frase.replace("mas o menos","mas o menos");

        frase=frase.replace("en adelante","en adelante");
        frase=frase.replace("para delante","en adelante");
        frase=frase.replace("adelante","en adelante");
        frase=frase.replace("delante","en adelante");
        frase=frase.replace("hacia delante","en adelante");
        frase=frase.replace("posterior","despues");
        frase=frase.replace("posteriormente","despues");
        frase=frase.replace("medio dia","mediodia");
        frase=frase.replace("otra vez","otravez");
        frase=frase.replace("todavia no","todavia no");
        frase=frase.replace("aun no","todavia no");
        frase=frase.replace("una vez","una vez");
        frase=frase.replace("primera vez","primera vez");
        frase=frase.replace("por primera vez","primera vez");
        frase=frase.replace("mi primera vez","primera vez");

        frase=frase.replace("no poder","no poder");
        frase=frase.replace("no puedo","no poder");
        frase=frase.replace("puedo","yo poder");
        frase=frase.replace("pude","poder");
        frase=frase.replace("pudes","tu poder");
        frase=frase.replace("podre","poder");
        frase=frase.replace("no pude","no poder");
        frase=frase.replace("no podre","no poder");

        frase=frase.replace("voy","yo ir");
        frase=frase.replace("hagan","ustedes hacer");
        frase=frase.replace("hagas","tu hacer");
        frase=frase.replace("voy","yo ir");
        frase=frase.replace("date","darte");
        frase=frase.replace("dime","tu decir");

        frase=frase.replace("ocurre","ocurrir");
        frase=frase.replace("ocurrio","ocurrir");
        frase=frase.replace("ocurrira","ocurrir");

        frase=frase.replace("comi","comer");
        frase=frase.replace("comere","comer");

        frase=frase.replace("estes","estar");
        frase=frase.replace("este","estar");
        frase=frase.replace("puedes","poder");
        frase=frase.replace("puede","poder");
        frase=frase.replace("sientes","tu sentir");
        frase=frase.replace(" te "," ");
        //frase=frase.replace("te "," tu ");
        frase=frase.replace("pongan","poner ustedes");
        frase=frase.replace("pon","poner");
        frase=frase.replace("juega","jugar");
        frase=frase.replace("haras","hacer");
        frase=frase.replace("tiene","tener");
        frase=frase.replace(" ve ","ir");
        frase=frase.replace("digas","tu decir");
        frase=frase.replace("beso","besar");
        frase=frase.replace("nado","nadar");

        frase=frase.replace("sal","salir");
        frase=frase.replace("salgo","salir");
        frase=frase.replace("sales","salir");
        frase=frase.replace("abran","abrir");
        frase=frase.replace("abre","abrir");
        frase=frase.replace("lee","leer");
        frase=frase.replace("leean","leer");
        frase=frase.replace("escriban","escribir");
        frase=frase.replace("escribe","escribir");
        frase=frase.replace("saca","sacar");
        frase=frase.replace("saquen","sacar");

        if(!frase.contains("cuando")){
            frase=frase.replace("ndo","");
        }

        frase=frase.replace(" mio "," yo ");
        frase=frase.replace(" mi "," yo ");
        frase=frase.replace(" me "," yo ");
        frase=frase.replace("dijo","decir");
        frase=frase.replace("me "," tu a yo ");
        frase=frase.replace(" usted "," tu ");
        frase=frase.replace("nuestro","nosotros");
        frase=frase.replace(" nos "," nosotros ");
        frase=frase.replace("tuyo","tu");
        frase=frase.replace("su","tu");
        frase=frase.replace("suyo","ellos");
        frase=frase.replace("entiendes","entender");
        frase=frase.replace("entendiste","entender");
        frase=frase.replace("entiendido","entender");

        frase=frase.replace("ahora","ahorita");

        frase=frase.replace("estaba","estar");
        frase=frase.replace("estare","estar");
        frase=frase.replace("estuve","estar");
        frase=frase.replace("estoy","yo estar");
        frase=frase.replace("quiero","querer");
        frase=frase.replace("querre","querer");
        frase=frase.replace("queria","querer");
        frase=frase.replace("quieres","querer");

        frase=frase.replace("comiendo","comer");
        frase=frase.replace("comi","comer");
        frase=frase.replace("como","comer");
        frase=frase.replace("come","comer");
        frase=frase.replace("comemos","comer");

        frase=frase.replace("sentado","sentar");
        frase=frase.replace("sentada","sentar");
        frase=frase.replace("sento","sentar");
        frase=frase.replace("sentaba","sentar");
        frase=frase.replace("sente","sentar");

        //conjunciones
        frase=frase.replace(" a "," ");
        frase=frase.replace(" con "," ");
        frase=frase.replace(" o "," ");
        frase=frase.replace(" igual "," ");
        frase=frase.replace(" sino "," ");
        frase=frase.replace(" fuera "," ");
        frase=frase.replace(" ni "," ");
        frase=frase.replace(" asi "," ");
        frase=frase.replace(" pero "," ");
        frase=frase.replace(" mientras "," ");
        frase=frase.replace(" a "," ");
        frase=frase.replace(" tambien "," ");
        frase=frase.replace(" ademas "," ");
        frase=frase.replace(" de "," ");

        frase=frase.replace("vamos","nosotros ir");


        return frase;
    }

    String LimpiarCadenas(String palabraAC){
        String original = "ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØÙÚÛÜÝßàáâãäåæçèéêëìíîïðñòóôõöøùúûüýÿ";
        // Cadena de caracteres ASCII que reemplazarán los originales.
        String ascii = "AAAAAAACEEEEIIIIDNOOOOOOUUUUYBaaaaaaaceeeeiiiionoooooouuuuyy";
        for (int k=0; k<original.length(); k++) {
            palabraAC = palabraAC.replace(original.charAt(k), ascii.charAt(k));
        }
        palabraAC=palabraAC.toLowerCase();
        palabraAC = palabraAC.replace("?", "");palabraAC = palabraAC.replace("!", "");palabraAC = palabraAC.replace("¿", "");
        palabraAC = palabraAC.replace("¡", "");palabraAC = palabraAC.replace(",", "");palabraAC = palabraAC.replace(".", "");
        //palabraAC = palabraAC.replace(" ", "");
        return palabraAC;
    }

    void compararLetras(String palabraAC,String palabra){

        int total = palabra.length();
        int totalAC = palabraAC.length();
        int aciertos=0,menor=0;
        if(total<totalAC){
            menor=total;
        }else if(total==totalAC){
            menor=total;
        }else{
            menor=totalAC;
        }
        for(int i = 0;i<menor;i++){
            if(palabra.charAt(i)==palabraAC.charAt(i)){
                aciertos++;
            }
        }
        int res = (int) (menor/2);
        //System.out.println("res: "+res+"\nAciertos: "+aciertos+"\n------------------------");
        if(aciertos>mayor && aciertos>res){
            mayor=aciertos;
            palMayor=palabra;
        }

//        return aciertos>=res;
    }

    String comparar(String partes []){
        String frase="";
        String palabraAC="",palabra="";
        boolean sino=false;
        for(int i = 0;i<partes.length;i++) { //repasar el input
            for (int j = 0; j < VerbosInfinitivo.length; j++) {

                palabraAC = LimpiarCadenas(partes[i]);
                palabraAC=palabraAC.replace(" ","");
                palabra = VerbosInfinitivo[j].toString();
                //  System.err.println(palabraAC);
                compararLetras(palabraAC, palabra);
            }
            int pos=0;
            if (mayor >= 4) {
                /*if(mayor<3){
                    Toast.makeText(this,"Palabra no encontrada\nSe le podrían parecer",Toast.LENGTH_LONG).show();
                }*/

                // System.out.println(palabraAC + " se parece más a: " + palMayor);
                for (int k = 0; k < VerbosInfinitivo.length; k++) {
                    if (palMayor.equalsIgnoreCase(VerbosInfinitivo[k].toString())) {
                        pos=k;
                    }
                }
                frase+= VerbosInfinitivo[pos].toString()+" ";
            }else{
                frase+= partes[i]+" ";
            }
            palMayor="";
            mayor=0;

        }

        return frase;
    }
    private void llenarRecycler() {
        borrarRecycler();
        Datos_Usuario conex = new Datos_Usuario(getContext(), "DBUsuario_palabras", null, 3);
        SQLiteDatabase db = conex.getWritableDatabase();
        final Cursor cursor = db.rawQuery("SELECT Frase FROM PalabrasR", null);
        while (cursor.moveToNext()){
            lista_palabras.add(new Classes(cursor.getString(0)));
        }
        lista_palabras.add(new Classes("Mi nombre es: "+NombreUsuario));
        lista_palabras.add(new Classes("Agregar +"));
    }
    private void borrarRecycler() {
        recyclerView_palabras.removeAllViewsInLayout();
        final int size = lista_palabras.size();
        lista_palabras.clear();
        adappters_palabras.notifyItemRangeRemoved(0, size);
    }

    private void Decir_y_Mostrar(String query) {
        //recibir la palabra, sacarla por voz y mostrarla.

        if(query.equalsIgnoreCase("Agregar +")){
            startActivity(new Intent(getContext(),AM_Palabras_Rapidas.class));
        }else {
            Elemen mess = new Elemen();
            mess.setLado(true);
            mess.setNombre(NombreUsuario);
            mess.setMensaje(query);
            VariablesYDatos.Conversacion.add(mess);
            AdaptadorAssistent adapter = new AdaptadorAssistent(VariablesYDatos.Conversacion);
            rv.setAdapter(adapter);

            Hablar(query);
        }

    }


}
