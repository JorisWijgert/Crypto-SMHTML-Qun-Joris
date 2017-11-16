package crypto.org.crypto;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import crypto.org.crypto.Classes.UserValuta;

public class ValutaListAdapter extends RecyclerView.Adapter<ValutaListAdapter.ViewHolder> {
    private List<UserValuta> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View mView;
        public TextView tvName;
        public TextView tvShortName;
        public ImageView ivLogo;

        public ViewHolder(View v) {
            super(v);
            mView = v;
            tvName = v.findViewById(R.id.name);
            tvShortName = v.findViewById(R.id.shortName);
            ivLogo = v.findViewById(R.id.imageView);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ValutaListAdapter(List<UserValuta> myDataset) {
        mDataset = myDataset;
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        String currencyName = mDataset.get(position).getValuta().getName();

        holder.tvName.setText(currencyName);
        holder.tvShortName.setText(mDataset.get(position).getValuta().getShortName());

        switch (currencyName.toLowerCase()) {
            case "bitcoin":
                holder.ivLogo.setImageResource(R.drawable.bitcoin_logo);
                break;
            case "ethereum":
                holder.ivLogo.setImageResource(R.drawable.ethereum_logo);
                break;
            case "litecoin":
                holder.ivLogo.setImageResource(R.drawable.litecoin_logo);
                break;
            case "ripple":
                holder.ivLogo.setImageResource(R.drawable.ripple_logo);
                break;
            case "bitcoin cash":
                holder.ivLogo.setImageResource(R.drawable.bitcoin_cash_logo);
                break;
            default:
                holder.ivLogo.setImageResource(R.mipmap.ic_launcher_foreground);
                break;
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}

