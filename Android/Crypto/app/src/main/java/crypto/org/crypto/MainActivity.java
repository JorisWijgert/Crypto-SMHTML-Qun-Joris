package crypto.org.crypto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import crypto.org.crypto.Model.UserValuta;
import crypto.org.crypto.volley.AppController;
import crypto.org.crypto.volley.BetterStringRequest;

public class MainActivity extends AppCompatActivity {

    private ValutaListAdapter lvAdapater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);


        final List<UserValuta> userValutaList = new ArrayList<>();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://i329146.venus.fhict.nl/api/users/1";

        BetterStringRequest jsObjRequest = new BetterStringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    Gson gson = new Gson();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray userValutas = jsonObject.getJSONArray("UserValutas");

                    for (int i=0; i < userValutas.length(); i++) {
//                        UserValuta userValuta = gson.fromJson(userValutas.getJSONObject(i), );
//                        JSONObject valuta = userValuta.getJSONObject("Valuta");
                        UserValuta userValuta = objectMapper.readValue(userValutas.getJSONObject(i).toString(), UserValuta.class);
                        userValutaList.add(userValuta);
                    }

                    lvAdapater.notifyDataSetChanged();

                    int i = 0;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppController.getInstance().addToRequestQueue(jsObjRequest);



        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        // specify an adapter (see also next example)
        lvAdapater = new ValutaListAdapter(userValutaList);
        mRecyclerView.setAdapter(lvAdapater);
    }
}
