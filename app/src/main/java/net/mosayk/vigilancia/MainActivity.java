package net.mosayk.vigilancia;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetActivityDelegate;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.mosayk.vigilancia.DataStructure.DatabaseHelper.LLAMADA;
import static net.mosayk.vigilancia.DataStructure.DatabaseHelper.LLAMADASALIENTE;
import static net.mosayk.vigilancia.DataStructure.DatabaseHelper.SERVERURL;

public class MainActivity extends AppCompatActivity {

    String urllogo="https://softwaremediafactory.com/mosayk/botonlogo.png";
    String conex, MACWIFI;
    ProgressDialog mProgressDialog;
    String sala;
    boolean errorservidor=false;
    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        VerificaConex();
        View btn_offline = findViewById(R.id.button_logo);
        //sala = "Paciente-Enfermera";


        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Mosayk Call update...");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(true);
        //permiso de escritura y descarga de recurso
        int check = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (check == PackageManager.PERMISSION_GRANTED) {
            //comprobarversion();
            //descargarrecursosoffline();
                //File ruta_sd = Environment.getExternalStorageDirectory();
               // File nuevaCarpeta = new File(ruta_sd.getAbsolutePath(), "mosayk");
               /// if (nuevaCarpeta.exists()){
               //     Log.i("recursos creados", "si");
                //    Log.i("recursos creados", "ya existe");
              ////  }else {
                //    nuevaCarpeta.mkdirs();
               ////     Log.i("recursos creados", "no");
               //     Log.i("recursos creados", "creando");
               // }

        if(conex.equals("1")) {
            Picasso.get().load("https://softwaremediafactory.com/mosayk/botonlogo.png")
                    //.resize(3800, 3800)
                    //.centerCrop()
                    .into((ImageView) btn_offline);
            //descargarlogoffline();
        }else{
            //File file = new File(Environment.getExternalStorageDirectory() + File.separator +"mosayk"+ File.separator+"botonlogocall.png");
            //if (file.exists()){
            //  Picasso.get().load(file)
            //.resize(3800, 2160)
            //.centerCrop()
            //        .into((ImageView) btn_offline);
            //Toast.makeText(getApplicationContext(), "logo de memoria", Toast.LENGTH_SHORT).show();
            //}else{
            //  Toast.makeText(getApplicationContext(), "No se Encontro el recurso Logo Offline", Toast.LENGTH_SHORT).show();
            //}
        }
    } else {
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        //  requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1024);
        //}
    }


        View fondo = findViewById(R.id.imageView9);
        Picasso.get().load(R.drawable.backgroundblue)
                .resize(3800, 2160)
                //.centerCrop()
                .into((ImageView) fondo);

        // Initialize default options for Jitsi Meet conferences.



        Log.e("macwifi", MACWIFI);


        //Sala(MACWIFI);
        //llenado de lista para llamadas seguras a enfermeria o medicos
        //get the spinner from the xml.
        Spinner dropdown = findViewById(R.id.spinner1);
//create a list of items for the spinner.
        //String[] items = new String[]{"","Paciente-Enfermera"};
//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
        //ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
//set the spinners adapter to the previously created one.
        //dropdown.setAdapter(adapter);
        //dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //@Override
            //public void onItemSelected(AdapterView<?> parent, View view,
             //                          int position, long id) {
              //  sala = (String) parent.getItemAtPosition(position);
                //Log.v("item", (String) parent.getItemAtPosition(position));
            //}

            //@Override
           // public void onNothingSelected(AdapterView<?> parent) {
                //TODO Auto-generated method stub
          //  }
       // });

        //levantado automatico de la sala al inciar el app se utiliza cuando esta instalada en stb
        //onBackPressed();


        String text = MACWIFI;
        //URL serverURL;
        //try {
          //  serverURL = new URL("https://www.call.softwaremediafactory.com");
        //} catch (MalformedURLException e) {
         //   e.printStackTrace();
         //   throw new RuntimeException("Invalid server URL!");
       // }

            // Build options object for joining the conference. The SDK will merge the default
            // one we set earlier and this one when joining.
            //JitsiMeetConferenceOptions defaultOptions = new JitsiMeetConferenceOptions.Builder()
                //.setServerURL(serverURL)
                //.setRoom(text)
                //.build();
            // Launch the new activity with the given options. The launch() method takes care
            // of creating the required Intent and passing the options.
            //JitsiMeetActivity.launch(this, defaultOptions);

        consulta_sala();
        //sala = MACWIFI;
        llamada2.run();
        Sala(sala);
    }

    public void consulta_sala(){

        final String ssd = "";
        JSONObject jsonObject = new JSONObject();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, LLAMADA+MACWIFI, jsonObject,
                response -> {
                    try {
                        PackageInfo pInfo = getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0);
                        String versionapp = pInfo.versionName;
                        Log.e("respuesta llamda", String.valueOf(response));
                        //String llamada = pInfo.versionName;

                        JSONArray llamada = response.getJSONArray("LLamadas");

                        JSONObject jo_inside = llamada.getJSONObject(0);
                        String tipo = jo_inside.getString("recepcion_llamada");
                        String sala2 = jo_inside.getString("sala");
                        String[] salados = sala2.split("https://www.call.softwaremediafactory.com/");
                        Log.e("array", String.valueOf(salados[1]));
                        sala = String.valueOf(salados[1]);

                    }
                    catch (JSONException | PackageManager.NameNotFoundException e){
                        e.printStackTrace();
                        Log.e("Error"," "+e);
                        //Destroy();
                        return;
                    }
                }, error -> {
            if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                //Destroy();
                return ;
            } else if (error instanceof AuthFailureError) {
                //Destroy();
                return ;
            } else if (error instanceof ServerError) {
                Toast.makeText(getApplicationContext(), "Error en consulta a la Base de Datos ", Toast.LENGTH_SHORT).show();
                return;
            } else if (error instanceof NetworkError) {
                //Destroy();
                return ;
            } else if (error instanceof ParseError) {
                //Destroy();
                return;
            }
        }){
            @Override
            public Map<String, String> getHeaders() {
                Map<String,String> headers = new HashMap<>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        VolleyQueue.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);


    }

    public void Sala(String sala) {
        //EditText editText = findViewById(R.id.conferenceName);
        //Spinner seleccion = sala;
        //llamada.run();
        String text = sala;
        URL serverURL;
        try {
            serverURL = new URL("https://www.call.softwaremediafactory.com/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("Invalid server URL!");
        }
        //Log.e("urlserver", String.valueOf(serverURL));
        //String[] mac = sala.split(":");
        ////String sala2 ="";
        //for(int i = 0; i < mac.length; i++){
            //sala2 += mac[i];
        //}

        JitsiMeetConferenceOptions defaultOptions
                = new JitsiMeetConferenceOptions.Builder()
                .setServerURL(serverURL)
                .setRoom(sala)
                .setWelcomePageEnabled(false)
                .build();
        JitsiMeetActivity.launch(this, defaultOptions);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();


        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest sr = new StringRequest(Request.Method.POST,LLAMADASALIENTE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //mPostCommentResponse.requestCompleted();
                //llamada.run();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //mPostCommentResponse.requestEndedWithError(error);
                //Destroy();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("mac_wifi",MACWIFI);
                params.put("tipo","");
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(sr);
    }

    public void VerificaConex(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            conex = "1";
            MACWIFI = "cc:4b:73:d9:8b:ca";
            //MACWIFI = getMacAddress();
            //Toast.makeText(this, "Conectado", Toast.LENGTH_SHORT).show();
        } else {
            conex = "0";
            MACWIFI = "cc:4b:73:d9:8b:ca";
            //MACWIFI = getMacAddress();
            //Toast.makeText(this, "Sin Conexión", Toast.LENGTH_SHORT).show();
        }
        //return conex;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(this, "volvio", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest sr = new StringRequest(Request.Method.POST,LLAMADASALIENTE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //mPostCommentResponse.requestCompleted();
                //llamada.run();
                //Destroy();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //mPostCommentResponse.requestEndedWithError(error);
                //Destroy();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("mac_wifi",MACWIFI);
                params.put("tipo","");
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(sr);



    }

    protected void Destroy() {
        //ir atras para minimizar la aplicacion
        moveTaskToBack(true);
        //cerrar la aplicacion luego de ejecutar el proceso de borrado
        int q = android.os.Process.myPid();
        android.os.Process.killProcess(q);
    }

    private Runnable llamada2 = new Runnable() {
        @Override
        public void run() {
            JSONObject jsonObject = new JSONObject();
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, LLAMADA+MACWIFI, jsonObject,
                    response -> {
                        try {
                            PackageInfo pInfo = getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0);
                            String versionapp = pInfo.versionName;
                            Log.e("respuesta llamda", String.valueOf(response));
                            //String llamada = pInfo.versionName;
                            JSONArray llamada = response.getJSONArray("LLamadas");

                            JSONObject jo_inside = llamada.getJSONObject(0);
                            String tipo = jo_inside.getString("tipo");
                            if (tipo.equals("VIG") ) {

                            }else if (tipo.equals("APRB") ){

                            }else if (tipo.isEmpty()  ){
                                Destroy();
                            }else{
                                Destroy();
                            }
                            //  String changes = jo_inside.getString("changes");
                            //  String hostauthor = jo_inside.getString("hostauthor");
                        }
                        catch (JSONException | PackageManager.NameNotFoundException e){
                            e.printStackTrace();
                            Log.e("Error"," "+e);
                            //Destroy();
                        }
                    }, error -> {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
Destroy();
                } else if (error instanceof AuthFailureError) {
Destroy();
                } else if (error instanceof ServerError) {
                    Toast.makeText(getApplicationContext(), "Error en consulta a la Base de Datos ", Toast.LENGTH_SHORT).show();
                    Destroy();
                } else if (error instanceof NetworkError) {
Destroy();
                    //Destroy();
                } else if (error instanceof ParseError) {
Destroy();
                }
            }){
                @Override
                public Map<String, String> getHeaders() {
                    Map<String,String> headers = new HashMap<>();
                    headers.put("Accept", "application/json");
                    headers.put("Content-Type", "application/json");
                    return headers;
                }
            };
            VolleyQueue.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
            mHandler.postDelayed(this, 2000);

        }
    };

    public static String getMacAddress() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X", b) + ":");
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
            Log.e("Error", ex.getMessage());
        }
        return "";
    }
}
