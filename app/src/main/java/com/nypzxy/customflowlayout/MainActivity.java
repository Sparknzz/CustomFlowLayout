package com.nypzxy.customflowlayout;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

import static com.nypzxy.customflowlayout.Data.mTitles;

public class MainActivity extends AppCompatActivity {

    private FlowLayout flowLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        flowLayout = new FlowLayout(this);
        flowLayout.setPadding(15,15,15,15);

        setContentView(flowLayout);


        for (int i = 0; i < Data.mTitles.size(); i++) {

            TextView textView = new TextView(this);
            textView.setText(Data.mTitles.get(i));
            textView.setTextSize(14);
            textView.setTextColor(Color.WHITE);
            textView.setPadding(15,10,15,10);
            textView.setBackgroundColor(ColorUtils.generateColor());

            flowLayout.addView(textView);
        }

    }
}
