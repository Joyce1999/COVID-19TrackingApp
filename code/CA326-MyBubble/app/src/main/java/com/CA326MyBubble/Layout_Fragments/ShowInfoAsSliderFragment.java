package com.CA326MyBubble.Layout_Fragments;

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

import com.CA326MyBubble.App_Adapters.SliderAdapter;
import com.CA326MyBubble.R;
import com.CA326MyBubble.App_Interfaces.ListenerForInfoSlides;
import com.CA326MyBubble.Layout_Models.SlideModel;


public class ShowInfoAsSliderFragment extends Fragment {

    private static final String parameter = "parameter";

    private String sliderRequest;

    private ViewPager viewSlide;
    private LinearLayout dots;

    private SliderAdapter sliderAdapter;

    private Button Next;
    private Button Cancel;

    private int count;

    private ListenerForInfoSlides ListenForSlides;

    public ShowInfoAsSliderFragment() {
        // Required empty public constructor
    }

    // Initialise the fragment
    public static ShowInfoAsSliderFragment newInstance(String param1) {
        ShowInfoAsSliderFragment fragment = new ShowInfoAsSliderFragment();
        Bundle args = new Bundle();
        args.putString(parameter, param1);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            sliderRequest = getArguments().getString(parameter);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_slider, container, false);
        viewSlide =  root.findViewById(R.id.sViewPager);
        dots = root.findViewById(R.id.layoutDots);
        Next =  root.findViewById(R.id.btnNext);
        Cancel = root.findViewById(R.id.btnCancel);

        List<SlideModel> slideModelList = new ArrayList<>();

        // Handles selecting which info area a user wants to explore
        if (sliderRequest != null) {
            switch (sliderRequest)
            {
                case "TheSpread":
                    slideModelList = howItSpread();
                    break;
                case "HowToQuarantine":
                    slideModelList = HowToQuarantine();
                    break;
                case "HowToPrevent":
                    slideModelList = HowToPrevent();
                    break;
                case "TheSigns":
                    slideModelList = TheSigns();
                    break;
                case "HowToReduce":
                    slideModelList = HowToReduce();
                    break;
            }
        }

        sliderAdapter = new SliderAdapter(getActivity(), slideModelList);
        count = sliderAdapter.getCount();

        // Adapter in ViewPager
        viewSlide.setAdapter(sliderAdapter);

        // PageChangeListener flicking through slides
        viewSlide.addOnPageChangeListener(viewPagerPageChangeListener);

        addThePageDots(0);

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextClick(v);

            }
        });
        return root;
    }
    private int getSlide(int i) {
        return viewSlide.getCurrentItem() + i;
    }

    //btnNextClick
    public void NextClick(View v) {
        // checking for last page
        // if last page info screen will be launched again
        int current = getSlide(1);
        if (current < sliderAdapter.getCount()) {
            // move to next screen
            viewSlide.setCurrentItem(current);
        } else {
            onBackPressed();
        }
    }
    // viewPagerPage ChangeListener according to the what page its on via the dots
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addThePageDots(position);

            if (position == count - 1) {
                Next.setText("Finish");
                Cancel.setVisibility(View.GONE);
            } else {
                Next.setText("Next");
                Cancel.setVisibility(View.VISIBLE);
                Cancel.setText("Cancel");
            }
        }
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }
        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    };
    // The set of Dot points for each slide
    private void addThePageDots(int currentPage) {
        TextView[] dots = new TextView[count];
        this.dots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(getActivity());
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(36);
            dots[i].setTextColor(getResources().getColor(R.color.textPrimary));  // dot is inactive colour
            this.dots.addView(dots[i]);
        }
        if (dots.length > 0)
            dots[currentPage].setTextColor(getResources().getColor(R.color.colorAccent)); // dot is active colour
    }
    // Map of slides on how COVID-19 spreads
    public List<SlideModel> howItSpread()
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
    public List<SlideModel> HowToQuarantine()
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
    public List<SlideModel> HowToPrevent()
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
    public List<SlideModel> TheSigns()
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
    public List<SlideModel> HowToReduce()
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
        if (ListenForSlides != null) {
            ListenForSlides.doOnBackPressed();
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ListenerForInfoSlides) {
            ListenForSlides = (ListenerForInfoSlides) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ListenForSlides = null;
    }


}
