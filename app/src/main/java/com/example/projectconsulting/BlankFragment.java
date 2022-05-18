package com.example.projectconsulting;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.projectconsulting.ui.home.HomeFragment;

public class BlankFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        container.removeAllViews();

        HomeFragment hf = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", ((MenuActivity) getActivity()).getUser());

        FragmentManager fmr = ((MenuActivity) getActivity()).getSupportFragmentManager();
        fmr.beginTransaction().replace(R.id.nav_host_fragment_content_main, hf).commit();

        return view;
    }
}
