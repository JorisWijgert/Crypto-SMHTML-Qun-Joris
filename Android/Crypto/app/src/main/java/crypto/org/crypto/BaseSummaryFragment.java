package crypto.org.crypto;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import crypto.org.crypto.Classes.UserValuta;

/**
 * Created by Joris on 22-11-2017.
 */

public class BaseSummaryFragment extends Fragment {

    private View view;


    @Override
    public void onResume() {
        super.onResume();
        createFragments();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    private void createFragments(){
        UserValuta userValuta = (UserValuta) getArguments().getSerializable("userValuta");
        Bundle bundle = new Bundle();
        bundle.putSerializable("userValuta", userValuta);

        FragmentManager manager = getFragmentManager();

        DetailsFragment detailsFragment = new DetailsFragment();
        detailsFragment.setArguments(bundle);
        manager.beginTransaction()
                .replace(R.id.details_fragment, detailsFragment, detailsFragment.getTag())
                .commit();

        PieChartTotalFragment pieChartTotalFragment = new PieChartTotalFragment();
        pieChartTotalFragment.setArguments(bundle);
        manager.beginTransaction()
                .replace(R.id.pie_chart_total_fragment, pieChartTotalFragment, pieChartTotalFragment.getTag())
                .commit();

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_base_summary, container, false);
        return view;
    }
}
