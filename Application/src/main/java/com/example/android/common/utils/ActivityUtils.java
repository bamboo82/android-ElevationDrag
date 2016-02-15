package com.example.android.common.utils;

import android.util.DisplayMetrics;

/**
 * create utils for activity
 * Created by chienyuc on 2016/2/15.
 */
public class ActivityUtils {

    public static void initDisplayMetrics(DisplayMetrics disp) {
        mDisplayMetrics = disp;
    }

    private static DisplayMetrics mDisplayMetrics = null;

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp){
        if (mDisplayMetrics == null)
            throw new RuntimeException("mDisplayMetrics must initalized before use");
        float px = dp * (mDisplayMetrics.densityDpi / 160f);
        return px;
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px) {
        if (mDisplayMetrics == null)
            throw new RuntimeException("mDisplayMetrics must initalized before use");
        float dp = px / (mDisplayMetrics.densityDpi / 160f);
        return dp;
    }
}
