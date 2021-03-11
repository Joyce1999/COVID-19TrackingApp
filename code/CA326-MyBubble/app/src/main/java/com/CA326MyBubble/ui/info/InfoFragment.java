package com.CA326MyBubble.ui.info;

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
import com.CA326MyBubble.interfaces.FragListener;

import static com.CA326MyBubble.ut.Utilities.SLIDER_INTENT;

public class InfoFragment extends Fragment {

    private com.CA326MyBubble.ui.info.InfoViewModel infoViewModel;
    private FragListener mListener;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        infoViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(com.CA326MyBubble.ui.info.InfoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_info, container, false);

        CardView cardViewSpread = root.findViewById(R.id.cardViewSpread);
        CardView cardViewQuarantine = root.findViewById(R.id.cardViewQuarantine);
        CardView cardViewPrevention = root.findViewById(R.id.cardViewPrevention);
        CardView cardViewSigns = root.findViewById(R.id.cardViewSigns);
        CardView cardViewReduce = root.findViewById(R.id.cardViewReduce);
        cardViewSpread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getActivity(), SliderActivity.class));
                startIntent("spread");
            }
        });
        cardViewQuarantine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startIntent("quarantine");
            }
        });

        cardViewPrevention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startIntent("prevention");
            }
        });
        cardViewSigns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startIntent("signs");
            }
        });

        cardViewReduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startIntent("reduce");
            }
        });

        infoViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //   textView.setText(s);
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
            throw new RuntimeException(context.toString()
                    + " must implement InteractionListener");
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