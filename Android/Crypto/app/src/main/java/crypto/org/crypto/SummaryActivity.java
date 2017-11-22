package crypto.org.crypto;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import crypto.org.crypto.Classes.UserValuta;

public class SummaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        UserValuta userValuta = (UserValuta) getIntent().getSerializableExtra("userValuta");
        Bundle bundle = new Bundle();
        bundle.putSerializable("userValuta", userValuta);

        this.setTitle(userValuta.getValuta().getName());

        FragmentManager manager = getSupportFragmentManager();
        SummaryPagerAdapter adapter = new SummaryPagerAdapter(manager,bundle);
        ViewPager pager = findViewById(R.id.pager);
        pager.setAdapter(adapter);


//        BaseSummaryFragment baseSummaryFragment =  new BaseSummaryFragment();
//        baseSummaryFragment.setArguments(bundle);
//        manager.beginTransaction()
//                .replace(R.id.baseSummary, baseSummaryFragment, baseSummaryFragment.getTag())
//                .commit();
//
    }


}
