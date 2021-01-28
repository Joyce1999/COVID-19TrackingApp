package com.example.ca326.ui.health;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;

import com.example.ca326.R;

public class HealthFragment extends Fragment {

    private HealthViewModel healthViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        healthViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(HealthViewModel.class);
        View root = inflater.inflate(R.layout.fragment_health, container, false);

        return root;
    }
}