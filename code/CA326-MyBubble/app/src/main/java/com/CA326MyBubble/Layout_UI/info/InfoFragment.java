package com.CA326MyBubble.Layout_UI.info;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.CA326MyBubble.R;
import com.CA326MyBubble.App_Interfaces.FragListener;

import static com.CA326MyBubble.App_Utilities.Utilities.SLIDER_INTENT;

public class InfoFragment extends Fragment {

    private com.CA326MyBubble.Layout_UI.info.InfoViewModel infoViewModel;
    private FragListener mListener;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        infoViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(com.CA326MyBubble.Layout_UI.info.InfoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_info, container, false);

        CardView cVSpread = root.findViewById(R.id.CVSpread);
        CardView cvQuarantine = root.findViewById(R.id.CVQuarantine);
        CardView cVPrevention = root.findViewById(R.id.CVPrevention);
        CardView cvSigns = root.findViewById(R.id.CVSigns);
        CardView cVReduce = root.findViewById(R.id.CVReduce);

        // Handles the button presses from the user on each slide title
        cVSpread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startIntent("TheSpreda");
            }
        });
        cvQuarantine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startIntent("HowToQuarantine");
            }
        });

        cVPrevention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startIntent("HowToPrevent");
            }
        });
        cvSigns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startIntent("TheSigns");
            }
        });
        cVReduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startIntent("HowToReduce");
            }
        });
        // grabs the text for each individual slide
        infoViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        return root;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragListener) {
            mListener = (FragListener) context;
        } else {
            throw new RuntimeException(context.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    // Now we can fire the event when the user selects something in the fragment
    private void startIntent(String slide) {
        if (mListener != null) {
            mListener.getListIntent(SLIDER_INTENT, slide);
        }
    }

}