package com.qwinix.mathclock;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 2/9/2015.
 */
public class GlobalVariables {
    private static GlobalVariables instance;

    public static int day = 0, hour = 0, minute = 0;

public static String dayOfWeek = "";

    public static GlobalVariables getInstance() {
        if (instance == null) instance = new GlobalVariables();
        return instance;
    }
    private GlobalVariables(){

    }
    public void deleteInstance()
    {
        instance = null;
    }
}
