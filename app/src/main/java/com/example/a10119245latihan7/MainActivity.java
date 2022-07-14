package com.example.a10119245latihan7;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    TextView tv_sembuh, tv_positif, tv_rawat, tv_dead;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_sembuh = findViewById(R.id.tv_sembuh);
        tv_positif = findViewById(R.id.tv_positif);
        tv_rawat = findViewById(R.id.tv_rawat);
        tv_dead = findViewById(R.id.tv_dead);

        tampilData();
    }

    private void tampilData() {
        loading = ProgressDialog.show(MainActivity.this, "Memuat Data", "Harap Tunggu...");
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://apicovid19indonesia-v2.vercel.app/api/indonesia";
        JSONObject jsonObject = new JSONObject();
        final String requestBody = jsonObject.toString();

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jo = new JSONObject(response.toString());
                    String sembuh = jo.getString("sembuh");
                    String positif = jo.getString("positif");
                    String dirawat = jo.getString("dirawat");
                    String dead = jo.getString("meninggal");

                    tv_sembuh.setText(sembuh);
                    tv_positif.setText(positif);
                    tv_rawat.setText(dirawat);
                    tv_dead.setText(dead);
                    loading.cancel();
                    Toast.makeText(MainActivity.this, "Berhasil Memuat", Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.cancel();
                Toast.makeText(MainActivity.this, "Gagal Ambil Rest API" + error, Toast.LENGTH_SHORT).show();
            }
        }
        );
        queue.add(stringRequest);
    }
}