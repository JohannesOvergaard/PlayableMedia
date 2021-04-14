package com.example.skraldemonstre;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    int plasticbottles = 0;
    int cigarettes = 0;
    int facemasks = 0;
    int cans = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }

    public void incrementValue(String type) {
        switch (type) {
            case "plasticbottle":
                this.plasticbottles++;
                break;
            case "facemask":
                this.facemasks++;
                break;
            case "cigarette":
                this.cigarettes++;
            case "can":
                this.cans++;
        }
    }

    public int getCans() {
        return cans;
    }

    public int getCigarettes() {
        return cigarettes;
    }

    public int getFacemasks() {
        return facemasks;
    }

    public int getPlasticbottles() {
        return plasticbottles;
    }
}