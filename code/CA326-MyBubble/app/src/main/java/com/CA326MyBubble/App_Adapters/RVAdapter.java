package com.CA326MyBubble.App_Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.CA326MyBubble.R;
import com.CA326MyBubble.Layout_Models.GlobalCountries;


public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> implements Filterable {

  private List<GlobalCountries> mValues;
  private List<GlobalCountries> mValuesFilteredList;
    private Context mContext;


    public RVAdapter(Context context, List<GlobalCountries> values) {

        mValues = values;
        mValuesFilteredList = values;
        mContext = context;

    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        public TextView textView;
        public TextView textView2;
        GlobalCountries item;

        public ViewHolder(View v) {

            super(v);
            textView = v.findViewById(R.id.TotalCases);
            textView2 =  v.findViewById(R.id.tvTwo);

        }

        public void setData(GlobalCountries item) {
            this.item = item;
            textView2.setVisibility(View.VISIBLE);
            textView.setText(Integer.toString(item.getTotalCases()));
            textView2.setText(item.getCountryWithCovid());

        }
    }

    @NonNull
    @Override
    public RVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.list_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder Vholder, int position) {
        Vholder.setData(mValuesFilteredList.get(position));

    }

    @Override
    public int getItemCount() {

        return mValuesFilteredList.size();
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                   mValuesFilteredList = mValues;
                } else {
                    List<GlobalCountries> filteredList = new ArrayList<>();
                    for (GlobalCountries row : mValues) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getCountryWithCovid().toLowerCase().contains(charString.toLowerCase().trim())) {
                            filteredList.add(row);
                        }
                    }

                    mValuesFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mValuesFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mValuesFilteredList = (List<GlobalCountries>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public List<GlobalCountries> getFilteredList(
    )
    {
        return mValuesFilteredList;
    }
}