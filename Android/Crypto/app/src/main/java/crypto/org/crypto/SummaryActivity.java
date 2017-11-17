package crypto.org.crypto;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import crypto.org.crypto.Classes.UserValuta;

public class SummaryActivity extends FragmentActivity {

    private UserValuta userValuta;

    public SummaryActivity(UserValuta userValuta) {
        this.userValuta = userValuta;
    }

    public SummaryActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        UserValuta userValuta = (UserValuta) getIntent().getSerializableExtra("userValuta");
        Toast.makeText(this, userValuta.getValuta().getName(), Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        bundle.putSerializable("userValuta", userValuta);
        bundle.putString("Testing", "Testing");

        DetailsFragment detailsFragment = new DetailsFragment();
        detailsFragment.setArguments(bundle);
        super.onCreate(savedInstanceState);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.details_fragment, detailsFragment, detailsFragment.getTag())
                .commit();

        PieChartTotalFragment pieChartTotalFragment = new PieChartTotalFragment();
        pieChartTotalFragment.setArguments(bundle);
        manager.beginTransaction()
                .replace(R.id.pie_chart_total_fragment, pieChartTotalFragment, pieChartTotalFragment.getTag())
                .commit();

        setContentView(R.layout.activity_summary);

    }


}
