package com.example.skraldemonstre;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class GalleriFragment extends Fragment {

    private int cans, cigarettes, facemasks, plasticbottles = 0;
    int[] values = new int[4];

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View frag = inflater.inflate(R.layout.fragment_galleri, container, false);

        MainActivity activity = (MainActivity) getActivity();
        getCurrentMonsters(activity);
        values[0] = cans;
        values[1] = cigarettes;
        values[2] = facemasks;
        values[3] = plasticbottles;

        GridView gridview = frag.findViewById(R.id.medal_grid);
        gridview.setAdapter(new MedalAdapter(this.getContext(), values));
        return frag;
    }

    public void getCurrentMonsters(MainActivity activity) {
        this.cans = activity.getCans();
        this.cigarettes = activity.getCigarettes();
        this.facemasks = activity.getFacemasks();
        this.plasticbottles = activity.getPlasticbottles();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}