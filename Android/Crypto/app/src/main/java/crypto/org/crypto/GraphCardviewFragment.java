package crypto.org.crypto;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Joris on 22-11-2017.
 */

public class GraphCardviewFragment extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_graph_card_view, container, false);
        CardView candleGraph = (CardView) view.findViewById(R.id.cardviewCandle);
        candleGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                GraphFragment graphFragment = new GraphFragment();
                manager.beginTransaction()
                        .replace(R.id.graphFragment, graphFragment, graphFragment.getTag())
                        .commit();
            }
        });
        return view;
    }
}
