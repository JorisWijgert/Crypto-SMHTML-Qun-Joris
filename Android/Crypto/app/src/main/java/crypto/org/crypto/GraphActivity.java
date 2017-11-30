package crypto.org.crypto;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.EventLogTags;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import crypto.org.crypto.Classes.GraphData;
import crypto.org.crypto.Classes.User;
import crypto.org.crypto.Classes.UserGraph;
import crypto.org.crypto.Classes.UserValuta;
import crypto.org.crypto.volley.AppController;
import crypto.org.crypto.volley.BetterStringRequest;

public class GraphActivity extends AppCompatActivity {

    private UserValuta userValuta;
    private User user;
    private float referenceTimeStamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        Bundle bundle = getIntent().getExtras().getBundle("bundle");
        userValuta = (UserValuta) bundle.getSerializable("userValuta");
        ChartType chosenType = ChartType.Bar;

        generateChartStart(chosenType, userValuta);
    }

    private Object generateChart(ChartType chosenType, List<UserGraph> userGraphs, UserValuta userValuta) {
        RelativeLayout rl = (RelativeLayout) this.findViewById(R.id.layout_graph);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        switch (chosenType) {
            case Bar:
                BarChart barChart = createBarChart(userGraphs, userValuta);
                rl.addView(barChart,params);
                break;
            case LINE:
                LineChart lineChart = createLineChart(userGraphs, userValuta);
                rl.addView(lineChart,params);
                break;
            case CANDLE:
                CandleStickChart candleStickChart = createCandleStickChart(userGraphs, userValuta);
                rl.addView(candleStickChart,params);
                break;
            case SCATTER:
                ScatterChart scatterChart = createScatterChart(userGraphs, userValuta);
                rl.addView(scatterChart,params);
                break;
            case RADARCHART:
                RadarChart radarChart = createRadarChart(userGraphs, userValuta);
                rl.addView(radarChart,params);
                break;
            default:
                Toast.makeText(this, "error retrieving chosen type graph", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    private RadarChart createRadarChart(List<UserGraph> userGraphs, UserValuta userValuta) {
        RadarChart radarChart = new RadarChart(this);
        return radarChart;
    }

    private ScatterChart createScatterChart(List<UserGraph> userGraphs, UserValuta userValuta) {
        ScatterChart scatterChart = new ScatterChart(this);
        return scatterChart;
    }

    private CandleStickChart createCandleStickChart(List<UserGraph> userGraphs, UserValuta userValuta) {
        CandleStickChart candleStickChart = new CandleStickChart(this);
        return candleStickChart;
    }

    private BarChart createBarChart(List<UserGraph> userGraphs, UserValuta userValuta) {
        BarChart barChart = new BarChart(this);
        List<BarEntry> barEntries = new ArrayList<>();
        List<Entry> entries = getEntries(userGraphs, userValuta);
        for (Entry entry: entries) {
            barEntries.add(new BarEntry(entry.getX()+1,(long)entry.getY()));
        }
        barEntries = barEntries.subList(0,5);
        BarDataSet dataSet = getBarDataSet(barEntries);

        BarData lineData = new BarData(dataSet);
        dataSet.setColor(Color.YELLOW);
        dataSet.setValueTextSize(5f);
        barChart.setData(lineData);
//        HourAxisValueFormatter xAxisFormatter = new HourAxisValueFormatter((long)referenceTimeStamp);
//        XAxis xAxis = barChart.getXAxis();
//        xAxis.setValueFormatter(xAxisFormatter);
        MyMarkerView myMarkerView= new MyMarkerView(getApplicationContext(), R.layout.my_marker_view_layout, (long)referenceTimeStamp);
        barChart.setMarkerView(myMarkerView);
        barChart.setDescription("");
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.fitScreen();
//        barChart.getAxisRight().setEnabled(false);
        barChart.invalidate();
        return barChart;
    }

    private LineChart createLineChart(List<UserGraph> userGraphs, UserValuta userValuta) {
        LineChart lineChart = new LineChart(this);
        List<Entry> entries = getEntries(userGraphs, userValuta);

        LineDataSet dataSet = getLineDataSet(entries);
        dataSet.setColor(0xff000000);
        dataSet.setValueTextSize(5f);
        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);
        HourAxisValueFormatter xAxisFormatter = new HourAxisValueFormatter((long)referenceTimeStamp);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(xAxisFormatter);
        MyMarkerView myMarkerView= new MyMarkerView(getApplicationContext(), R.layout.my_marker_view_layout, (long)referenceTimeStamp);
        lineChart.setMarkerView(myMarkerView);
        lineChart.setDescription("");
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.invalidate();
        return lineChart;
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
    private BarDataSet getBarDataSet(List<BarEntry> entries) {
        BarDataSet dataSet = new BarDataSet(entries, "Dates");

        return dataSet;
    }

    @NonNull
    private List<Entry> getEntries(List<UserGraph> userGraphs, UserValuta userValuta) {
        List<Entry> entries = new ArrayList<Entry>();
        List<GraphData> graphDataList = getGraphData(userGraphs, userValuta);
        setReferenceTimeStamp(graphDataList);

        for(GraphData graphData : graphDataList){
            float unixTimeStamp = graphData.getTimeStamp();
            float relativeUnixTimeStamp = unixTimeStamp - referenceTimeStamp;
            entries.add(new Entry(relativeUnixTimeStamp, (float)graphData.getOpen()));
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
                return (int) p1.getTimeStamp()-p2.getTimeStamp();
            }
        });
        return graphDataList;
    }

    private void setReferenceTimeStamp(List<GraphData> graphDataList) {
        GraphData firstGraphDataPoint = graphDataList.get(0);
        referenceTimeStamp = firstGraphDataPoint.getTimeStamp();
    }

    public User generateChartStart(final ChartType chosenType, final UserValuta userValuta) {
        user = null;
        String url = "https://i329146.venus.fhict.nl/api/users/1";
        BetterStringRequest jsObjRequest = new BetterStringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                user = gson.fromJson(response, User.class);
                generateChart(chosenType, user.getUserGraphs(), userValuta);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppController.getInstance().addToRequestQueue(jsObjRequest);
        return user;
    }

    public static String unixToCalendar(int unixTime){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(unixTime*1000);

        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM HH:mm");
        String formattedCalender = format1.format(calendar.getTime());
        return formattedCalender;
    }
}
