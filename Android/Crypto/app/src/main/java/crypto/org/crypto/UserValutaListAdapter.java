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

public class UserValutaListAdapter extends RecyclerView.Adapter<UserValutaListAdapter.ViewHolder>{
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
        public TextView tvAmount;
        public TextView tvPrice;

        public ViewHolder(View v) {
            super(v);
            mView = v;

            tvAmount = v.findViewById(R.id.amount);
            tvPrice = v.findViewById(R.id.price);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onClick(view, getAdapterPosition());
        }



    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public UserValutaListAdapter(List<UserValuta> myDataset, Context context) {
        mDataset = myDataset;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public UserValutaListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_valuta_list, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String price = getRoundedDouble(mDataset.get(position).getPurchasePrice());
        String amount = getRoundedDouble(mDataset.get(position).getAmount());

        holder.tvAmount.setText(amount);
        holder.tvPrice.setText("$" + price);

    }

    private String getRoundedDouble(Double number){
        return String.format("%.2f", number);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}

