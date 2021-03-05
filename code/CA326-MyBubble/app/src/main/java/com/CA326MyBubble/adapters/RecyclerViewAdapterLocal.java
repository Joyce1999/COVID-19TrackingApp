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
import com.CA326MyBubble.model.CountryLocal;


public class RecyclerViewAdapterLocal extends RecyclerView.Adapter<RecyclerViewAdapterLocal.ViewHolder> implements Filterable {

  private List<CountryLocal> mValues;
  private List<CountryLocal> mValuesFilteredList;
    private Context mContext;


    public RecyclerViewAdapterLocal(Context context, List<CountryLocal> values) {

        mValues = values;
        mValuesFilteredList = values;
        mContext = context;

    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        public TextView textView;
        CountryLocal item;

        public ViewHolder(View v) {

            super(v);
            textView = v.findViewById(R.id.tvOne);


        }

        public void setData(CountryLocal item) {
            this.item = item;

            textView.setText(item.getName());

        }
    }

    @NonNull
    @Override
    public RecyclerViewAdapterLocal.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

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
                    List<CountryLocal> filteredList = new ArrayList<>();
                    for (CountryLocal row : mValues) {

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
                mValuesFilteredList = (List<CountryLocal>) filterResults.values;
                //mValuesFilteredList.addAll((List)filterResults.values);
                notifyDataSetChanged();
            }
        };
    }

    public List<CountryLocal> getmValuesFilteredList(
    )
    {
        return mValuesFilteredList;
    }
}