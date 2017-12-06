package crypto.org.crypto;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.gson.Gson;

import java.text.DecimalFormat;
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


public class LineGraphFragment extends Fragment {
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
        view = inflater.inflate(R.layout.fragment_line_graph, container, false);
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
                generateLineChart(user.getUserGraphs(), userValuta);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppController.getInstance().addToRequestQueue(jsObjRequest);
        return user;
    }

    private void generateLineChart(List<UserGraph> userGraphs, UserValuta userValuta) {
        LineChart lineChart = getActivity().findViewById(R.id.chart_line_graph);
        List<Entry> entries = getEntries(userGraphs, userValuta);

        LineDataSet dataSet = getLineDataSet(entries);
        dataSet.setColor(0xff000000);
        dataSet.setValueTextSize(5f);
        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);
        HourAxisValueFormatter xAxisFormatter = new HourAxisValueFormatter((long) referenceTimeStamp);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(xAxisFormatter);
        MyMarkerView myMarkerView = new MyMarkerView(getActivity(), R.layout.my_marker_view_layout, (long) referenceTimeStamp);
        lineChart.setMarkerView(myMarkerView);
        lineChart.setDescription("");
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.invalidate();
    }

    @NonNull
    private LineDataSet getLineDataSet(List<Entry> entries) {
        LineDataSet dataSet = new LineDataSet(entries, "Dates");
        dataSet.setColor(0xff000000);
        dataSet.setValueTextSize(5f);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        return dataSet;
    }

    @NonNull
    private List<Entry> getEntries(List<UserGraph> userGraphs, UserValuta userValuta) {
        List<Entry> entries = new ArrayList<Entry>();
        List<GraphData> graphDataList = getGraphData(userGraphs, userValuta);
        setReferenceTimeStamp(graphDataList);

        for (GraphData graphData : graphDataList) {
            float unixTimeStamp = graphData.getTimeStamp();
            float relativeUnixTimeStamp = unixTimeStamp - referenceTimeStamp;
            entries.add(new Entry(relativeUnixTimeStamp, (float) graphData.getOpen()));
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
