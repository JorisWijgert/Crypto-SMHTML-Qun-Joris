package crypto.org.crypto;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;
import crypto.org.crypto.Classes.UserValuta;

public class GraphActivity extends AppCompatActivity {

    private View view;


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        createFragments();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
                // if this doesn't work as desired, another possibility is to call

                //stopActivityTask();  // finish() here.
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createFragments(){
        Bundle bundle = getIntent().getExtras();
        String type = bundle.getString("type");
        bundle = bundle.getBundle("bundle");
        UserValuta userValuta = null; // or other values
        if(bundle != null) {
            userValuta = (UserValuta)bundle.getSerializable("userValuta");
        }

        FragmentManager manager = getSupportFragmentManager();

        switch(type){
            case "line":
                LineGraphFragment lineGraphFragment = new LineGraphFragment();
                lineGraphFragment.setArguments(bundle);

                manager.beginTransaction()
                        .replace(R.id.line_graph_fragment, lineGraphFragment, lineGraphFragment.getTag())
                        .commit();
                break;

            case "candle":
                CandleGraphFragment candleGraphFragment = new CandleGraphFragment();
                candleGraphFragment.setArguments(bundle);

                manager.beginTransaction()
                        .replace(R.id.line_graph_fragment, candleGraphFragment, candleGraphFragment.getTag())
                        .commit();
                break;
            case "bar":
                BarGraphFragment barGraphFragment = new BarGraphFragment();
                barGraphFragment.setArguments(bundle);

                manager.beginTransaction()
                        .replace(R.id.line_graph_fragment, barGraphFragment, barGraphFragment.getTag())
                        .commit();
                break;
            case "scatter":
                ScatterGraphFragment scatterGraphFragment = new ScatterGraphFragment();
                scatterGraphFragment.setArguments(bundle);

                manager.beginTransaction()
                        .replace(R.id.line_graph_fragment, scatterGraphFragment, scatterGraphFragment.getTag())
                        .commit();
                break;

            default:
                Toast.makeText(this, "Failed to generate graph", Toast.LENGTH_SHORT).show();
                break;

        }


    }

}
