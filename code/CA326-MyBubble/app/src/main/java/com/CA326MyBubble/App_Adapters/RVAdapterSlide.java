package com.CA326MyBubble.App_Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.CA326MyBubble.R;
import com.CA326MyBubble.Layout_Models.SlideModel;


public class RVAdapterSlide extends RecyclerView.Adapter<RVAdapterSlide.ViewHolder>{

  private List<SlideModel> mValues;

    private Context mContext;


    public RVAdapterSlide(Context context, List<SlideModel> values) {

        mValues = values;

        mContext = context;

    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        public TextView textView;
        public ImageView imageView;
        SlideModel item;

        public ViewHolder(View v) {

            super(v);
            textView = v.findViewById(R.id.text);
            imageView =  v.findViewById(R.id.imageView);

        }

        public void setData(SlideModel item) {
            this.item = item;

            textView.setText(item.getSubHeading());
            imageView.setImageResource(item.getImage());

        }
    }

    @NonNull
    @Override
    public RVAdapterSlide.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.row_recycler, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder Vholder, int position) {
        Vholder.setData(mValues.get(position));

    }

    @Override
    public int getItemCount() {

        return mValues.size();
    }

}