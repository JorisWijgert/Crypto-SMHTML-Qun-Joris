package crypto.org.crypto;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import crypto.org.crypto.Classes.User;
import crypto.org.crypto.Classes.UserValuta;
import crypto.org.crypto.Classes.Valuta;
import crypto.org.crypto.volley.AppController;
import crypto.org.crypto.volley.BetterStringRequest;

public class MainActivity extends AppCompatActivity implements OnItemClickListener {

    private ValutaListAdapter lvAdapater;
    private SwipeRefreshLayout swipeContainer;

    private List<UserValuta> userValutas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                retrieveData();
                // specify an adapter (see also next example)
                lvAdapater = new ValutaListAdapter(userValutas, getApplicationContext());
                mRecyclerView.setAdapter(lvAdapater);
            }
        });


        userValutas = new ArrayList<>();

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        retrieveData();
        // specify an adapter (see also next example)
        lvAdapater = new ValutaListAdapter(userValutas, this);
        lvAdapater.setClickListener(this);
        mRecyclerView.setAdapter(lvAdapater);

    }

    private void retrieveData() {
        userValutas.clear();
        String url = "https://i329146.venus.fhict.nl/api/users/1";
        BetterStringRequest jsObjRequest = new BetterStringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                User user = gson.fromJson(response, User.class);


                for (UserValuta userValuta : user.getUserValutas()) {
                    UserValuta otherUv = checkUserValutasContains(userValuta);
                    if (otherUv == null) {
                        otherUv = userValuta;
                        userValutas.add(otherUv);
                    } else
                        otherUv.setAmount(otherUv.getAmount() + userValuta.getAmount());

                }

                lvAdapater.notifyDataSetChanged();
                swipeContainer.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }

    /**
     * Checks if the given userValuta name exists in the userValutas list.
     *
     * @param userValuta the userValuta to check
     * @return true if the user exists in the userValutas list, otherwise return false
     */
    private UserValuta checkUserValutasContains(UserValuta userValuta) {
        for (UserValuta uv : userValutas) {
            if (uv.getValuta().getShortName().equals(userValuta.getValuta().getShortName()))
                return uv;
        }
        return null;
    }

    @Override
    public void onClick(View view,  int position) {
        final UserValuta userValuta = userValutas.get(position);
        Intent summaryActivity = new Intent(this, SummaryActivity.class);
        summaryActivity.putExtra("userValuta", (Serializable) userValuta);
        startActivity(summaryActivity);
    }
}
