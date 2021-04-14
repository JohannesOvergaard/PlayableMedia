package com.example.skraldemonstre;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MedalAdapter extends BaseAdapter
{
    private final Context mContext;
    int[] monsterValues;
    int bronze = 1;
    int silver = 3;
    int gold = 5;

    public MedalAdapter(Context context, int[] values) {
        this.mContext = context;
        this.monsterValues = values;
    }

    @Override
    public int getCount() {
        return monsterValues.length;
    }

    @Override
    public Object getItem(int i) {
        return monsterValues[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public int getMonsterMedal(int monstertype) {
        int j;
        int earned = monsterValues[monstertype];
        if (earned >= gold) {j = 3; }
        else if (earned >= silver) {j = 2;}
        else if (earned >= bronze) {j = 1; }
        else {j = 0;}
        switch (monstertype) {
            case 0:
                return canMonsterMedals[j];
            case 1:
                return cigaretteMonsterMedals[j];
            case 2:
                return facemaskMonsterMedals[j];
            case 3:
                return bottleMonsterMedals[j];
        }
        return 0;
    }

    public int getMedalProgress(int monstertype) {
        int earned = monsterValues[monstertype];
        int progress;
        if (earned >= gold) {progress = 100;}
        else if (earned >= silver) {progress = (int) (((double) earned / (double) gold) * 100);}
        else if (earned >= bronze) {progress = (int) (((double) earned / (double) silver) * 100); }
        else {progress = (int) (((double) earned / (double) bronze) * 100);}
        return progress+1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup)
    {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.medal_view,viewGroup,false);
        }

        final ImageButton imageButton = (ImageButton) convertView.findViewById(R.id.image_medal);
        final ProgressBar progressBar = (ProgressBar) convertView.findViewById(R.id.progress);

        imageButton.setImageResource(getMonsterMedal(position));
        imageButton.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Du har fanget " + monsterValues[position] + " " + monsterTypes[position], Toast.LENGTH_SHORT).show();
            }
        });
        progressBar.setProgress(getMedalProgress(position));

        return convertView;
    }

    public Integer[] bottleMonsterMedals = {
            R.drawable.greybottlemonster,
            R.drawable.bronzebottlemonster,
            R.drawable.silverbottlemonster,
            R.drawable.goldbottlemonster,
    };

    public Integer[] canMonsterMedals = {
            R.drawable.greycanmonster,
            R.drawable.bronzecanmonster,
            R.drawable.silvercanmonster,
            R.drawable.goldcanmonster
    };

    public Integer[] cigaretteMonsterMedals = {
            R.drawable.greycigarettemonster,
            R.drawable.bronzecigarettemonster,
            R.drawable.silvercigarettemonster,
            R.drawable.goldcigarettemonster
    };

    public Integer[] facemaskMonsterMedals = {
            R.drawable.greymaskmonster,
            R.drawable.bronzemaskmonster,
            R.drawable.silvermaskmonster,
            R.drawable.goldmaskmonster
    };

    public String[] monsterTypes = {
            "dåsemonstre",
            "cigaretmonstre",
            "maskemonstre",
            "flaskemonstre"
    };
}