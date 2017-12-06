package crypto.org.crypto;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import crypto.org.crypto.Classes.GraphData;
import crypto.org.crypto.Classes.User;
import crypto.org.crypto.Classes.UserGraph;
import crypto.org.crypto.Classes.UserValuta;
import crypto.org.crypto.volley.AppController;
import crypto.org.crypto.volley.BetterStringRequest;


public class CandleGraphFragment extends Fragment {
    private View view;
    private UserValuta userValuta;
    private User user;
    private float referenceTimeStamp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_candle_graph, container, false);
        Bundle bundle = getArguments();
        userValuta = (UserValuta) bundle.getSerializable("userValuta");
        generateChartStart(userValuta);
        return view;

    }

    public User generateChartStart(final UserValuta userValuta) {
        user = null;
        String url = "https://i329146.venus.fhict.nl/api/users/1";
        BetterStringRequest jsObjRequest = new BetterStringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                user = gson.fromJson(response, User.class);
                generateCandleChart(user.getUserGraphs(), userValuta);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppController.getInstance().addToRequestQueue(jsObjRequest);
        return user;
    }

    private void generateCandleChart(List<UserGraph> userGraphs, UserValuta userValuta) {
        CandleStickChart lineChart = getActivity().findViewById(R.id.chart_candle_graph);
        List<CandleEntry> entries = getEntries(userGraphs, userValuta);

        CandleDataSet dataSet = getLineDataSet(entries);

        CandleData data = new CandleData(dataSet);
//        dataSet.setDrawIcons(false);
        dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
//        dataSet.setColor(Color.rgb(80, 80, 80));
        dataSet.setShadowColor(Color.DKGRAY);
        dataSet.setShadowWidth(0.7f);
        dataSet.setDecreasingColor(Color.RED);
        dataSet.setDecreasingPaintStyle(Paint.Style.FILL);
        dataSet.setIncreasingColor(Color.rgb(122, 242, 84));
        dataSet.setIncreasingPaintStyle(Paint.Style.STROKE);
        dataSet.setNeutralColor(Color.BLUE);
        //dataSet.setHighlightLineWidth(1f);



        ArrayList<CandleEntry> yVals1 = new ArrayList<CandleEntry>();

        for (int i = 0; i < 10; i++) {
            float mult = (10 + 1);
            float val = (float) (Math.random() * 40) + mult;

            float high = (float) (Math.random() * 9) + 8f;
            float low = (float) (Math.random() * 9) + 8f;

            float open = (float) (Math.random() * 6) + 1f;
            float close = (float) (Math.random() * 6) + 1f;

            boolean even = i % 2 == 0;

            yVals1.add(new CandleEntry(
                    i, val + high,
                    val - low,
                    even ? val + open : val - open,
                    even ? val - close : val + close
            ));
        }

        CandleDataSet set1 = new CandleDataSet(yVals1, "Data Set");

//        set1.setDrawIcons(false);
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
//        set1.setColor(Color.rgb(80, 80, 80));
        set1.setShadowColor(Color.DKGRAY);
        set1.setShadowWidth(0.7f);
        set1.setDecreasingColor(Color.RED);
        set1.setDecreasingPaintStyle(Paint.Style.FILL);
        set1.setIncreasingColor(Color.rgb(122, 242, 84));
        set1.setIncreasingPaintStyle(Paint.Style.STROKE);
        set1.setNeutralColor(Color.BLUE);
        //set1.setHighlightLineWidth(1f);

        //TODO Fix for our candles. Now using random data
        data = new CandleData(set1);


        lineChart.setData(data);
        lineChart.invalidate();
    }

    @NonNull
    private CandleDataSet getLineDataSet(List<CandleEntry> entries) {
        CandleDataSet dataSet = new CandleDataSet(entries, "Dates");
//        dataSet.setColor(0xff000000);
//        dataSet.setValueTextSize(5f);
        return dataSet;
    }

    @NonNull
    private List<CandleEntry> getEntries(List<UserGraph> userGraphs, UserValuta userValuta) {
        List<CandleEntry> entries = new ArrayList<CandleEntry>();
        List<GraphData> graphDataList = getGraphData(userGraphs, userValuta);
        setReferenceTimeStamp(graphDataList);
        int count = 0;
        for (GraphData graphData : graphDataList) {
            float unixTimeStamp = graphData.getTimeStamp();
            float relativeUnixTimeStamp = unixTimeStamp - referenceTimeStamp;
            count++;
            entries.add(new CandleEntry(count, (float) graphData.getHigh(), (float) graphData.getLow(), (float)graphData.getOpen(), (float)graphData.getClose()));
        }
        return entries.subList((entries.size()-5), entries.size());
    }

    @NonNull
    private List<GraphData> getGraphData(List<UserGraph> userGraphs, UserValuta userValuta) {
        List<GraphData> graphDataList = new ArrayList<>();
        for (UserGraph graph : userGraphs) {
            if (graph.getGraph().getValuta().getId() == userValuta.getValuta().getId()) {
                graphDataList = graph.getGraph().getGraphData();
            }
        }

        Collections.sort(graphDataList, new Comparator<GraphData>() {
            public int compare(GraphData p1, GraphData p2) {
                return (int) p1.getTimeStamp() - p2.getTimeStamp();
            }
        });
        return graphDataList;
    }

    private void setReferenceTimeStamp(List<GraphData> graphDataList) {
        GraphData firstGraphDataPoint = graphDataList.get(0);
        referenceTimeStamp = firstGraphDataPoint.getTimeStamp();
    }

}
