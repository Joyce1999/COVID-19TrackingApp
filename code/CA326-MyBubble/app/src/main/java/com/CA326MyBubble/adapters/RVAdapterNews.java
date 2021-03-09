package com.CA326MyBubble.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.CA326MyBubble.model.newsModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import com.CA326MyBubble.R;


public class RVAdapterNews extends RecyclerView.Adapter<RVAdapterNews.ViewHolder>{

  private List<newsModel> mValues = new ArrayList<>();

    private Context mContext;


    public RVAdapterNews(Context context) {

        mContext = context;

    }
    public List<newsModel> getNews() {
        return mValues;
    }
    public class ViewHolder extends RecyclerView.ViewHolder  {

         TextView tvTitle;
         TextView tvSource;
         ImageView imageNews;

        newsModel item;

        public ViewHolder(View v) {

            super(v);
            tvTitle = itemView.findViewById(R.id.tvTitleNews);
            tvSource = itemView.findViewById(R.id.tvSourceNews);
            imageNews = itemView.findViewById(R.id.imageNews);


        }

        public void setData(newsModel item) {
            this.item = item;
            Glide.with(mContext)
                    .load(item.getImageURL())
                    .into(imageNews);
            tvTitle.setText(item.getNewsTitle());
            tvSource.setText(item.getNewsSource().getName());

        }
    }

    @NonNull
    @Override
    public RVAdapterNews.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.row_news, parent, false);

        return new ViewHolder(view);
    }

    public void setNews(List<newsModel> itemModels) {
        if (mValues != null) {
            if (mValues.size() > 0) {
                mValues.clear();
            }
            mValues.addAll(itemModels);
        }
        notifyDataSetChanged();
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