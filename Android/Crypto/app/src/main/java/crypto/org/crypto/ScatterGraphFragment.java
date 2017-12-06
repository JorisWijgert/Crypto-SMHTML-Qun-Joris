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
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
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


public class ScatterGraphFragment extends Fragment {
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
        view = inflater.inflate(R.layout.fragment_scatter_graph, container, false);
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
        ScatterChart lineChart = getActivity().findViewById(R.id.chart_scatter_graph);
        List<Entry> entries = getEntries(userGraphs, userValuta);

        ScatterDataSet dataSet = getLineDataSet(entries);

        ScatterData data = new ScatterData(dataSet);





        lineChart.setData(data);
        lineChart.invalidate();
    }

    @NonNull
    private ScatterDataSet getLineDataSet(List<Entry> entries) {
        ScatterDataSet dataSet = new ScatterDataSet(entries, "Dates");
//        dataSet.setColor(0xff000000);
//        dataSet.setValueTextSize(5f);
        return dataSet;
    }

    @NonNull
    private List<Entry> getEntries(List<UserGraph> userGraphs, UserValuta userValuta) {
        List<Entry> entries = new ArrayList<Entry>();
        List<GraphData> graphDataList = getGraphData(userGraphs, userValuta);
        setReferenceTimeStamp(graphDataList);
        int count = 0;
        for (GraphData graphData : graphDataList) {
            float unixTimeStamp = graphData.getTimeStamp();
            float relativeUnixTimeStamp = unixTimeStamp - referenceTimeStamp;
            count++;
            entries.add(new Entry((float)graphData.getOpen(), 1));
        }
        return entries;
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
