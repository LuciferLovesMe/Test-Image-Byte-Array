package com.abim.imagebyte;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {
    Button btn;
    ImageView image;
    RequestQueue queue;
    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.button3);
        image = findViewById(R.id.img);
        ctx = this;
        queue = Volley.newRequestQueue(ctx);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest request = new StringRequest(Request.Method.GET, "http://192.168.1.29:5003/api/image", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            byte[] b = Base64.decode(response, Base64.DEFAULT);
                            Bitmap bm = BitmapFactory.decodeByteArray(b, 0, b.length);
                            image.setImageBitmap(bm);
                        }
                        catch (Exception ex){
                            Toast.makeText(ctx, ""+ex, Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ctx, ""+error, Toast.LENGTH_SHORT).show();
                    }
                });

                queue.add(request);
            }
        });
    }
}