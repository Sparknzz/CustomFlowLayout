package com.nypzxy.customflowlayout;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by Rita on 3/25/2017.
 */

public class ColorUtils {

    public static int generateColor() {

        Random random = new Random();
        int red = random.nextInt(150);
        int green = random.nextInt(150);
        int blue = random.nextInt(150);

        return Color.rgb(red, green, blue);
    }
}
