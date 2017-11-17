package crypto.org.crypto;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import crypto.org.crypto.Classes.UserValuta;

public class PieChartTotalFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pie_chart_total, container, false);
        TextView textView = (TextView)view.findViewById(R.id.tv_valutaName);
        Bundle bundle = getArguments();
//        UserValuta userValuta = (UserValuta)bundle.getSerializable("userValuta");
        textView.setText(bundle.getString("Testing"));
        return view;
    }
}
