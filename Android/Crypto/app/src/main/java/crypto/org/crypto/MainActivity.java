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
import android.widget.TextView;

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
import java.text.DecimalFormat;
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
    private TextView tvName;
    private TextView tvPortfolioTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retrieveData();
                lvAdapater = new ValutaListAdapter(userValutas, getApplicationContext());
                lvAdapater.setClickListener(MainActivity.this);
                mRecyclerView.setAdapter(lvAdapater);
            }
        });

        tvName = findViewById(R.id.tvName);
        tvPortfolioTotal = findViewById(R.id.tvPortfolioTotal);

        swipeContainer.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary);
        userValutas = new ArrayList<>();
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        retrieveData();
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
                double total = 0d;
                Gson gson = new Gson();
                User user = gson.fromJson(response, User.class);
                tvName.setText(String.format("Portfolio of %s", user.getUsername()));

                for (UserValuta userValuta : user.getUserValutas()) {
                    total += userValuta.getAmount() * userValuta.getValuta().getCurrentPrice();
                    UserValuta otherUv = checkUserValutasContains(userValuta);
                    if (otherUv == null) {
                        otherUv = userValuta;
                        userValutas.add(otherUv);
                    } else
                        otherUv.setAmount(otherUv.getAmount() + userValuta.getAmount());

                }
                DecimalFormat df = new DecimalFormat("#.00");

                tvPortfolioTotal.setText("$ " + df.format(total));
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
