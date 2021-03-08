package com.example.skraldemonstre;
import android.content.Context;
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
    private Context mContext;
    private int btn_id;
    private int total_btns = 5;

    public MedalAdapter(Context context) {
        this.mContext = context;
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

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup)
    {
        /*Button btn;

        if (view == null) {
            btn = new Button(mContext);
            btn.setText("Button " + (++btn_id));
        } else {
            btn = (Button) view;
        }

        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(v.getContext(), "Button #" + (i + 1), Toast.LENGTH_SHORT).show();
            }
        });

        return btn;*/

        ImageButton imageButton;
        if (view == null) {
            imageButton = new ImageButton(mContext);
            imageButton.setLayoutParams(new GridView.LayoutParams(250, 250));
            imageButton.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageButton.setPadding(8,8,8,8);
        } else {
            imageButton = (ImageButton) view;
        }

        imageButton.setImageResource(mMedalIds[0]);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Button #" + (i+1), Toast.LENGTH_SHORT).show();
            }
        });
        return imageButton;
    }

    public Integer[] mMedalIds = {
            R.mipmap.bottle_gold
    };
}