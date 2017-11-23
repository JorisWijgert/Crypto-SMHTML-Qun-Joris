package crypto.org.crypto;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.util.ArrayList;

import crypto.org.crypto.Classes.User;
import crypto.org.crypto.Classes.UserValuta;
import crypto.org.crypto.volley.AppController;
import crypto.org.crypto.volley.BetterStringRequest;


public class DetailsFragment extends Fragment {
    private View view;
    private UserValuta userValuta;
    private SwipeRefreshLayout swipeContainer;
    private User user;
    private UserValutaListAdapter lvAdapater;
    private ArrayList<UserValuta> userValutas;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_details, container, false);
        Bundle bundle = getArguments();
        userValuta = (UserValuta) bundle.getSerializable("userValuta");

        final RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        InitSwipeContainer(mRecyclerView);

        getUserData();
        return view;

    }

    private void InitSwipeContainer(final RecyclerView mRecyclerView) {
        swipeContainer = view.findViewById(R.id.swipe_container);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                RefreshUserValutaList(mRecyclerView);
                UpdatePieChartFragment();
            }
        });

        swipeContainer.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary);
    }

    private void RefreshUserValutaList(RecyclerView mRecyclerView) {
        getUserData();
        lvAdapater = new UserValutaListAdapter(userValutas, getActivity().getApplicationContext());
        mRecyclerView.setAdapter(lvAdapater);
    }

    private void UpdatePieChartFragment() {
        FragmentManager fm = getFragmentManager();
        PieChartTotalFragment fragm = (PieChartTotalFragment) fm.findFragmentById(R.id.pie_chart_total_fragment);
        fragm.getUserData();
    }

    private void setCoinNameAndSum() {
        TextView coinName = (TextView) view.findViewById(R.id.coin_name);
        TextView totalPrice = (TextView) view.findViewById(R.id.price);

        coinName.setText(userValuta.getValuta().getShortName());
        double totalPriceCoin = 0;
        ArrayList<UserValuta> userValutas = getUserValutas(userValuta, user);
        for (UserValuta userValuta : userValutas) {
            totalPriceCoin += userValuta.getAmount() * userValuta.getValuta().getCurrentPrice();
        }
        double roundedTotalPriceCoin = (double) Math.round(totalPriceCoin * 100) / 100;
        totalPrice.setText("Total: $" + Double.toString(roundedTotalPriceCoin));
    }

    private void fillUserValutas() {
        final RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(this.getContext()));
        userValutas = new ArrayList<>();
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        userValutas = getUserValutas(userValuta, user);
        lvAdapater = new UserValutaListAdapter(userValutas, getActivity());
        lvAdapater.notifyDataSetChanged();
        mRecyclerView.setAdapter(lvAdapater);
    }

    private ArrayList<UserValuta> getUserValutas(UserValuta userValuta, User user) {
        ArrayList<UserValuta> userValutas = new ArrayList<>();
        for (UserValuta userValuta1 : user.getUserValutas()) {
            if (userValuta.getValuta().getName().equals(userValuta1.getValuta().getName())) {
                userValutas.add(userValuta1);
            }
        }
        swipeContainer.setRefreshing(false);
        return userValutas;
    }


    public User getUserData() {
        user = null;
        String url = "https://i329146.venus.fhict.nl/api/users/1";
        BetterStringRequest jsObjRequest = new BetterStringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                user = gson.fromJson(response, User.class);
                fillUserValutas();
                setCoinNameAndSum();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppController.getInstance().addToRequestQueue(jsObjRequest);
        return user;

    }

}
