package net.mosayk.vigilancia;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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

import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.mosayk.vigilancia.DataStructure.DatabaseHelper.LLAMADA;
import static net.mosayk.vigilancia.DataStructure.DatabaseHelper.LLAMADASALIENTE;

public class MainActivity extends AppCompatActivity {

    String conex, MACWIFI;
    String sala;
    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        VerificaConex();
        MACWIFI = "cc:4b:73:d9:8b:ca";
        //MACWIFI = getMacAddress();

        View btn_offline = findViewById(R.id.button_logo);

        //permiso de escritura y descarga de recurso
        int check = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (check == PackageManager.PERMISSION_GRANTED) {

        if(conex.equals("1")) {
            Picasso.get().load("https://softwaremediafactory.com/mosayk/botonlogo.png")
                    //.resize(3800, 3800)
                    //.centerCrop()
                    .into((ImageView) btn_offline);
        }

        }


        View fondo = findViewById(R.id.imageView9);
        Picasso.get().load(R.drawable.backgroundblue)
                .resize(3800, 2160)
                //.centerCrop()
                .into((ImageView) fondo);

        consulta_sala();
        llamada2.run();

    }

    public void consulta_sala(){

        JSONObject jsonObject = new JSONObject();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, LLAMADA+MACWIFI, jsonObject,
                response -> {
                    try {

                        JSONArray llamada = response.getJSONArray("LLamadas");
                        JSONObject jo_inside = llamada.getJSONObject(0);
                        String sala2 = jo_inside.getString("sala");
                        String[] salados = sala2.split("https://www.call.softwaremediafactory.com/");
                        if(!sala2.isEmpty()) {
                            Log.e("array", "" + salados[1]);
                            sala = String.valueOf(salados[1]);
                            Sala(sala);
                        }

                    }
                    catch (JSONException e){
                        e.printStackTrace();
                        Log.e("Error"," "+e);
                        //Destroy();
                    }
                }, error -> {
            if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                //Destroy();
            } else if (error instanceof AuthFailureError) {
                //Destroy();
            } else if (error instanceof ServerError) {
                Toast.makeText(getApplicationContext(), "Error "+error, Toast.LENGTH_SHORT).show();
            } else if (error instanceof NetworkError) {
                //Destroy();
            } else if (error instanceof ParseError) {
                //Destroy();
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

        URL serverURL;
        try {
            serverURL = new URL("https://www.call.softwaremediafactory.com/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("Invalid server URL!");
        }

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
                params.put("llamada_activa","false");
                params.put("tipo","");
                params.put("familiar","");
                params.put("sala","");
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

        } else {
            conex = "0";
        }
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
                params.put("llamada_activa","false");
                params.put("tipo","");
                params.put("familiar","");
                params.put("sala","");
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

                        }
                        catch (JSONException e){
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
            Log.e("Error", ""+ex.getMessage());
        }
        return "";
    }
}
