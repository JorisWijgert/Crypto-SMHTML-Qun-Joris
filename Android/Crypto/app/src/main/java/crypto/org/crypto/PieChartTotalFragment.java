package crypto.org.crypto;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import crypto.org.crypto.Classes.User;
import crypto.org.crypto.Classes.UserValuta;
import crypto.org.crypto.volley.AppController;
import crypto.org.crypto.volley.BetterStringRequest;

public class PieChartTotalFragment extends Fragment {

    private User user;
    private View view;
    private UserValuta userValuta;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pie_chart_total, container, false);

        Bundle bundle = getArguments();
        userValuta = (UserValuta) bundle.getSerializable("userValuta");
        getUserData();
        return view;
    }



    private void generatePieChart(UserValuta userValuta, User user) {
        PieChart chart = (PieChart) view.findViewById(R.id.chart);
        ArrayList<PieEntry> amountEntries = new ArrayList<PieEntry>();
        for (UserValuta userValuta1 : user.getUserValutas()) {
            if (userValuta1.getValuta().getName().equals(userValuta.getValuta().getName())) {
                amountEntries.add(new PieEntry((float) userValuta1.getAmount(),String.valueOf(userValuta1.getAmount())));
            }
        }
        PieDataSet dataSet = new PieDataSet(amountEntries, "");
        dataSet.setValueTextSize(15f);
        dataSet.setSelectionShift(10f);
        dataSet.setValueFormatter(new PercentFormatter());
        dataSet.setColors(CustomColorTemplate.Colors);
        setLegendSettings(chart);
        PieData data = new PieData(dataSet);
        chart.setData(data);
        chart.setUsePercentValues(true);
        chart.animateY(3000, Easing.EasingOption.EaseOutBack);
        chart.invalidate();

    }

    private void setLegendSettings(PieChart chart) {
        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        l.setEnabled(true);
        chart.getLegend().setWordWrapEnabled(true);
        chart.setEntryLabelColor(Color.WHITE);
        chart.setEntryLabelTextSize(12f);
    }


    public User getUserData() {
        user = null;
        String url = "https://i329146.venus.fhict.nl/api/users/1";
        BetterStringRequest jsObjRequest = new BetterStringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                user = gson.fromJson(response, User.class);
                generatePieChart(userValuta, user);
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
