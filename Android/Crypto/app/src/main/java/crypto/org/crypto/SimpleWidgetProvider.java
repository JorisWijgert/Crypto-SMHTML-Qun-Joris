package crypto.org.crypto;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.Random;

import crypto.org.crypto.Classes.User;
import crypto.org.crypto.Classes.UserValuta;
import crypto.org.crypto.volley.AppController;
import crypto.org.crypto.volley.BetterStringRequest;

/**
 * Created by Joris on 30-11-2017.
 */

public class SimpleWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int count = appWidgetIds.length;

        for (int i = 0; i < count; i++) {
            int widgetId = appWidgetIds[i];

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.simple_widget);
            retrieveData(remoteViews, widgetId, appWidgetManager);
            Intent intent = new Intent(context, SimpleWidgetProvider.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.actionButton, pendingIntent);
            Intent titleIntent = new Intent(context, MainActivity.class);
            PendingIntent titlePendingIntent = PendingIntent.getActivity(context, 0, titleIntent, 0);
            remoteViews.setOnClickPendingIntent(R.id.imageView, titlePendingIntent);

        }
    }

    private void retrieveData(final RemoteViews remoteViews, final int widgetId, final AppWidgetManager appWidgetManager) {
        String url = "https://i329146.venus.fhict.nl/api/users/1";

        BetterStringRequest jsObjRequest = new BetterStringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                User user = gson.fromJson(response, User.class);

                double total = 0;
                double btc = 0;
                double eth = 0;
                double xrp = 0;
                double ltc = 0;
                double bch = 0;

                for (UserValuta userValuta : user.getUserValutas()) {
                    double value = userValuta.getAmount() * userValuta.getValuta().getCurrentPrice();
                    total += value;
                    switch (userValuta.getValuta().getShortName().toLowerCase()) {
                        case "btc":
                            btc += value;
                            break;
                        case "eth":
                            eth += value;
                            break;
                        case "xrp":
                            xrp += value;
                            break;
                        case "ltc":
                            ltc += value;
                            break;
                        case "bch":
                            bch += value;
                            break;
                        default:
                            btc += value;
                            break;
                    }
                }
                DecimalFormat df = new DecimalFormat("#.00");
                remoteViews.setTextViewText(R.id.textView, "$ " + df.format(total));
                remoteViews.setTextViewText(R.id.btcWidgetText, "$ " + df.format(btc));
                remoteViews.setTextViewText(R.id.ethWidgetText, "$ " + df.format(eth));
                remoteViews.setTextViewText(R.id.xrpWidgetText, "$ " + df.format(xrp));
                remoteViews.setTextViewText(R.id.ltcWidgetText, "$ " + df.format(ltc));
                remoteViews.setTextViewText(R.id.bchWidgetText, "$ " + df.format(bch));
                appWidgetManager.updateAppWidget(widgetId, remoteViews);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }
}