package com.example.skraldemonstre;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class GalleriFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View frag = inflater.inflate(R.layout.fragment_galleri, container, false);

        GridView gridview = (GridView) frag.findViewById(R.id.medal_grid);
        gridview.setAdapter(new MedalAdapter(this.getContext()));
        return frag;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
/*
        view.findViewById(R.id.button_galleri_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(GalleriFragment.this)
                        .navigate(R.id.action_GalleriFragment_to_StartFragment);
            }
        });
*/

    }
}
