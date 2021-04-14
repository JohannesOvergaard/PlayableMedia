package com.example.skraldemonstre;
import android.content.Context;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class MedalAdapter extends BaseAdapter
{
    private final Context mContext;
    private final int total_btns = 4;
    int[] monstervalues;
    DataTransfer df;
    int bronze = 1;
    int silver = 3;
    int gold = 5;

    public MedalAdapter(Context context, DataTransfer df) {
        this.mContext = context;
        this.df = df;
        findMonsters();
    }

    public void findMonsters() {
        monstervalues = df.SendData();
    }

    @Override
    public int getCount() {
        return total_btns;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public int getMonsterCount(int monstertype) {
        int j = 0;
        int earned = monstervalues[monstertype];
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

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup)
    {
        ImageButton imageButton;
        if (view == null) {
            imageButton = new ImageButton(mContext);
            imageButton.setLayoutParams(new GridView.LayoutParams(300, 350));
            imageButton.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imageButton.setPadding(0,0,0,0);
            imageButton.setBackgroundColor(Color.WHITE);
        } else {
            imageButton = (ImageButton) view;
        }

        imageButton.setImageResource(getMonsterCount(i));

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Du har fanget " + monstervalues[i] + " " + monsterTypes[i], Toast.LENGTH_SHORT).show();
            }
        });
        return imageButton;
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

