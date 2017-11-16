package crypto.org.crypto;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import crypto.org.crypto.Classes.UserValuta;

public class ValutaListAdapter extends RecyclerView.Adapter<ValutaListAdapter.ViewHolder>{
    private List<UserValuta> mDataset;
    private Context context;

    public void setClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    private OnItemClickListener clickListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // each data item is just a string in this case
        public View mView;
        public TextView tvName;
        public TextView tvShortName;
        public TextView tvAmount;
        public TextView tvPerc;
        public ImageView ivLogo;

        public ViewHolder(View v) {
            super(v);
            mView = v;
            tvName = v.findViewById(R.id.name);
            tvShortName = v.findViewById(R.id.shortName);
            tvAmount = v.findViewById(R.id.amount);
            tvPerc = v.findViewById(R.id.tvPerc);
            ivLogo = v.findViewById(R.id.imageView);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onClick(view, getAdapterPosition());
        }



    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ValutaListAdapter(List<UserValuta> myDataset, Context context) {
        mDataset = myDataset;
        this.context = context;
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
        double amount = mDataset.get(position).getAmount();
        double currentPrice = mDataset.get(position).getValuta().getCurrentPrice();
        double purchasePrice = mDataset.get(position).getPurchasePrice();
        holder.tvAmount.setText(String.format("$ %.2f", (amount*currentPrice)));
        double perc = (((amount * purchasePrice) - (amount * currentPrice)) / (amount * currentPrice)) * 100;
        holder.tvPerc.setText(String.format("%.2f %%", perc));

        if (perc < 0)
            holder.tvPerc.setTextColor(Color.RED);
        else {
            holder.tvPerc.setTextColor(context.getResources().getColor(R.color.DarkGreen, null));
        }

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

