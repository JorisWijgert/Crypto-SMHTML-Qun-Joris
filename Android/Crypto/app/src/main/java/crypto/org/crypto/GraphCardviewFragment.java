package crypto.org.crypto;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
        final Bundle bundle = getArguments();
        final Intent mIntent = new Intent(getActivity(), GraphActivity.class);
        mIntent.putExtra("bundle", bundle);

        CardView candleGraph = (CardView) view.findViewById(R.id.cardviewCandle);
        candleGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntent.putExtra("type", "candle");
                startActivity(mIntent);
            }
        });
        CardView lineGraph = view.findViewById(R.id.cardviewPlot);
        lineGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntent.putExtra("type", "line");
                startActivity(mIntent);
            }
        });
        CardView cardviewBar = view.findViewById(R.id.cardviewBar);
        cardviewBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntent.putExtra("type", "bar");
                startActivity(mIntent);
            }
        });
        CardView cardViewScatter = view.findViewById(R.id.cardviewScatter);
        cardViewScatter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntent.putExtra("type", "scatter");
                startActivity(mIntent);
            }
        });
        return view;
    }
}
