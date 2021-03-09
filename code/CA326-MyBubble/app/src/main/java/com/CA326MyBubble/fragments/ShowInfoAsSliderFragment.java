package com.CA326MyBubble.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import com.CA326MyBubble.adapters.SliderAdapter;
import com.CA326MyBubble.R;
import com.CA326MyBubble.interfaces.ListenerForInfoSlides;
import com.CA326MyBubble.model.SlideModel;


public class ShowInfoAsSliderFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";


    // TODO: Rename and change types of parameters
    private String sliderRequest;


    private ViewPager sViewPager;
    private LinearLayout dotsLayout;

    private SliderAdapter sliderAdapter;

    private Button btnNext, btnCancel;

    private int slideCount;

    private ListenerForInfoSlides mListener;

    public ShowInfoAsSliderFragment() {
        // Required empty public constructor
    }


    public static ShowInfoAsSliderFragment newInstance(String param1) {
        ShowInfoAsSliderFragment fragment = new ShowInfoAsSliderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            sliderRequest = getArguments().getString(ARG_PARAM1);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View root = inflater.inflate(R.layout.fragment_slider, container, false);

       // getActivity().getActionBar().setDisplayShowHomeEnabled(false);

        sViewPager =  root.findViewById(R.id.sViewPager);
        dotsLayout = root.findViewById(R.id.layoutDots);
        btnNext =  root.findViewById(R.id.btnNext);
        btnCancel= root.findViewById(R.id.btnCancel);


        List<SlideModel> slideModelList = new ArrayList<>();

        if (sliderRequest != null) {
            switch (sliderRequest)
            {
                case "spread":
                    slideModelList = howSpread();
                    break;
                case "quarantine":
                    slideModelList = quarantine();
                    break;
                case "prevention":
                    slideModelList = prevention();
                    break;
                case "signs":
                    slideModelList = signs();
                    break;
                case "reduce":
                    slideModelList = reduce();
                    break;
            }
        }


        sliderAdapter = new SliderAdapter(getActivity(), slideModelList);

        slideCount = sliderAdapter.getCount();

        // set adapter in ViewPager
        sViewPager.setAdapter(sliderAdapter);

        // set PageChangeListener
        sViewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        addBottomDots(0);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnNextClick(v);

            }
        });


       return root;
    }



    private int getItem(int i) {
        return sViewPager.getCurrentItem() + i;
    }

    //btnNextClick
    public void btnNextClick(View v) {
        // checking for last page
        // if last page home screen will be launched
        int current = getItem(1);
        if (current < sliderAdapter.getCount()) {
            // move to next screen
            sViewPager.setCurrentItem(current);
        } else {
            onBackPressed();
        }
    }

    // viewPagerPage ChangeListener according to Dots-Points
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
//            sliderAdapter.checkReadyBox();

            if (position == slideCount - 1) {
                btnNext.setText("finish");
                btnCancel.setVisibility(View.GONE);
            } else {
                btnNext.setText("next");
                btnCancel.setVisibility(View.VISIBLE);
                btnCancel.setText("cancel");
            }
        }
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }
        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    };
    // set of Dots points
    private void addBottomDots(int currentPage) {
        TextView[] dots = new TextView[slideCount];
        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(getActivity());
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.textPrimary));  // dot_inactive
            dotsLayout.addView(dots[i]);
        }
        if (dots.length > 0)
            dots[currentPage].setTextColor(getResources().getColor(R.color.colorAccent)); // dot_active
    }
    // Map of slides on how COVID-19 spreads
    public List<SlideModel> howSpread()
    {
        List<SlideModel> slideModelList = new ArrayList<>();
        slideModelList.add(new SlideModel(
                getResources().getString(R.string.infection1_heading),
                getResources().getString(R.string.infection1),
                R.drawable.waterdrop));
        slideModelList.add(new SlideModel(getResources().getString(R.string.infection2_heading),
                getResources().getString(R.string.infection2),R.drawable.closecontact));
        slideModelList.add(new SlideModel(getResources().getString(R.string.infection3_heading),
                getResources().getString(R.string.infection3),R.drawable.surface));
        return slideModelList;
    }
    // Map of slides on how to quarantine
    public List<SlideModel> quarantine()
    {
        List<SlideModel> slideModelList = new ArrayList<>();
        slideModelList.add(new SlideModel(
                getResources().getString(R.string.quarantine1_heading),
                getResources().getString(R.string.quarantine1_body),
                R.drawable.quarantinedays));
        slideModelList.add(new SlideModel(getResources().getString(R.string.quarantine2_heading),
                getResources().getString(R.string.quarantine2_body),
                R.drawable.stayhome));
        slideModelList.add(new SlideModel(getResources().getString(R.string.quarantine3_heading),
                getResources().getString(R.string.quarantine3_body),
                R.drawable.spread));
        slideModelList.add(new SlideModel(getResources().getString(R.string.quarantine4_heading),
                getResources().getString(R.string.quarantine4_body),
                R.drawable.illness));

        return slideModelList;
    }
    // Map of slides of how to prevent getting COVID-19
    public List<SlideModel> prevention()
    {
        List<SlideModel> slideModelList = new ArrayList<>();

        slideModelList.add(new SlideModel(
                getResources().getString(R.string.prevention1_heading),
                getResources().getString(R.string.prevention1),
                R.drawable.hand));
        slideModelList.add(new SlideModel(getResources().getString(R.string.prevention2_heading),
                getResources().getString(R.string.prevention2),
                R.drawable.shake));
        slideModelList.add(new SlideModel(getResources().getString(R.string.prevention3_heading),
                getResources().getString(R.string.prevention3),
                R.drawable.sanitizer));
        slideModelList.add(new SlideModel(getResources().getString(R.string.prevention4_heading),
                getResources().getString(R.string.prevention4),
                R.drawable.surface));

        return slideModelList;
    }
// Map of Symptom signs that may mean a person has contracted COVID-19
    public List<SlideModel> signs()
    {
        List<SlideModel> slideModelList = new ArrayList<>();
        slideModelList.add(new SlideModel(
                getResources().getString(R.string.symptom1_heading),
                getResources().getString(R.string.symptom1),
                R.drawable.temperature));
        slideModelList.add(new SlideModel(getResources().getString(R.string.symptom2_heading),
                getResources().getString(R.string.symptom2),
                R.drawable.sneeze));
        slideModelList.add(new SlideModel(getResources().getString(R.string.symptom3_heading),
                getResources().getString(R.string.symptom3),
                R.drawable.cough));
        slideModelList.add(new SlideModel(getResources().getString(R.string.symptom4_heading),
                getResources().getString(R.string.symptom4),
                R.drawable.headache));
        slideModelList.add(new SlideModel(getResources().getString(R.string.symptom5_heading),
                getResources().getString(R.string.symptom5),
                R.drawable.breathing));
        return slideModelList;
    }
    // Mapping of ways to reduce the spread, and reduce a persons risk of contracting COVID-19
    public List<SlideModel> reduce()
    {
        List<SlideModel> slideModelList = new ArrayList<>();
        slideModelList.add(new SlideModel(
                getResources().getString(R.string.reduce1_heading),
                getResources().getString(R.string.reduce1),
                R.drawable.sneeze));
        slideModelList.add(new SlideModel(getResources().getString(R.string.reduce2_heading),
                getResources().getString(R.string.reduce2),
                R.drawable.stayhome2));
        slideModelList.add(new SlideModel(getResources().getString(R.string.reduce3_heading),
                getResources().getString(R.string.reduce3),
                R.drawable.sneeze));
        slideModelList.add(new SlideModel(getResources().getString(R.string.reduced4_heading),
                getResources().getString(R.string.reduce4),
                R.drawable.facemask));
        slideModelList.add(new SlideModel(getResources().getString(R.string.reduce5_heading),
                getResources().getString(R.string.reduce5),
                R.drawable.stayhome2));
        return slideModelList;
    }


// Back button pressed, go to prev
    public void onBackPressed() {
        if (mListener != null) {
            mListener.doOnBackPressed();
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ListenerForInfoSlides) {
            mListener = (ListenerForInfoSlides) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


}
