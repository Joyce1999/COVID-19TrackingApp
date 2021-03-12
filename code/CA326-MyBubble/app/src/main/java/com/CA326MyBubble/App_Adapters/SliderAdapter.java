package com.CA326MyBubble.App_Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

import com.CA326MyBubble.R;
import com.CA326MyBubble.Layout_Models.SlideModel;

public class SliderAdapter extends PagerAdapter {



    private Context context;

    private List<SlideModel> slideModelItems;



    public SliderAdapter(Context context, List<SlideModel> slideModelItems) {

        this.context = context;
        this.slideModelItems = slideModelItems;

    }
    @Override
    public int getCount() {

        return slideModelItems.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

        return view == object;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        View view = layoutInflater.inflate(R.layout.slide_layout, container,false);
        container.addView(view);

        ImageView imageView = view.findViewById(R.id.imageView);
        TextView slideHeading = view.findViewById(R.id.layoutTitle);
        TextView slideDescription = view.findViewById(R.id.tvSubHeading);




        imageView.setImageResource(slideModelItems.get(position).getImage());

        slideHeading.setText(slideModelItems.get(position).getHeading());
        slideDescription.setText(slideModelItems.get(position).getSubHeading());



        return view;

    }



    @Override
    public void destroyItem(ViewGroup container, int position, @NonNull Object object) {

        container.removeView((ConstraintLayout)object);
    }

}


