package crypto.org.crypto;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import crypto.org.crypto.Model.UserValuta;

public class ValutaListAdapter extends RecyclerView.Adapter<ValutaListAdapter.ViewHolder> {
    private List<UserValuta> userValutas;

    public ValutaListAdapter(List<UserValuta> userValutas){
        this.userValutas = userValutas;
    }
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View mView;
        public TextView mTVValutaName;
        public TextView mTVValutaPrice;

        public ViewHolder(View v) {
            super(v);
            mView = v;
            mTVValutaName = v.findViewById(R.id.valutaName);
            mTVValutaPrice = v.findViewById(R.id.valutaPrice);
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ValutaListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.valuta_list, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int index) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        UserValuta userValuta = userValutas.get(index);
        holder.mTVValutaName.setText(userValuta.getValuta().getName());
        holder.mTVValutaName.setText(String.valueOf(userValuta.getValuta().getCurrentPrice()));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return userValutas.size();
    }
}

