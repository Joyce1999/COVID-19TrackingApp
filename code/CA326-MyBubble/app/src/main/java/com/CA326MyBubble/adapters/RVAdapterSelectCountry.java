package com.CA326MyBubble.adapters;

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
import com.CA326MyBubble.model.CountrySelection;


public class RVAdapterSelectCountry extends RecyclerView.Adapter<RVAdapterSelectCountry.ViewHolder> implements Filterable {

  private List<CountrySelection> mValues;
  private List<CountrySelection> mValuesFilteredList;
    private Context mContext;


    public RVAdapterSelectCountry(Context context, List<CountrySelection> values) {

        mValues = values;
        mValuesFilteredList = values;
        mContext = context;

    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        public TextView textView;
        CountrySelection item;

        public ViewHolder(View v) {

            super(v);
            textView = v.findViewById(R.id.TotalCases);


        }

        public void setData(CountrySelection item) {
            this.item = item;

            textView.setText(item.getName());

        }
    }

    @NonNull
    @Override
    public RVAdapterSelectCountry.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

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
                    List<CountrySelection> filteredList = new ArrayList<>();
                    for (CountrySelection row : mValues) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase().trim())) {
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
                mValuesFilteredList = (List<CountrySelection>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public List<CountrySelection> getmValuesFilteredList(
    )
    {
        return mValuesFilteredList;
    }
}