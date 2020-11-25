package com.nurbk.ps.urlconnection.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nurbk.ps.urlconnection.network.NetworkUtils;
import com.nurbk.ps.urlconnection.network.OnResponseListener;
import com.nurbk.ps.urlconnection.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {


    TextView textView;
    Button btnGetData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.txtUrl);
        btnGetData = findViewById(R.id.btnGetData);

        btnGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetworkUtils.getInstance().getData(new OnResponseListener() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("weatherObservations");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                Double lng = object.getDouble("lng");
                                String obv = object.getString("observation");
                                String ico = object.getString("ICAO");

                                textView.append("lng=" + lng + " obv=" + obv + " ico=" + ico + "\n---------\n\n");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(String error) {

                    }
                });


            }
        });
    }


}