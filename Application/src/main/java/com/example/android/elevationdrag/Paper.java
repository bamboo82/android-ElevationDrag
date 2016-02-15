package com.example.android.elevationdrag;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class Paper extends View {
    public Paper(Context context) {
        super(context);
        init();
    }

    public Paper(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Paper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public Paper(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public ArrayList<View> views = null;

    private void init() {
        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(4f);
        paint.setStyle(Paint.Style.STROKE);
        paint.setPathEffect(new DashPathEffect(new float[]{10, 20}, 0));
        //canvas.drawLine(0, 0, 500, 500, paint);

        //Log.i("paper", "onDraw");

        if (views != null) {
            if (views.size() >= 2) { // at least 2 points are required to draw a line
                boolean firstIteration = true;
                float first_centerX = 0;
                float first_centerY = 0;
                float prev_centerX = 0;
                float prev_centerY = 0;
                float centerX = 0;
                float centerY = 0;

                for (View v : views) {
                     centerX = v.getLeft() + v.getWidth() / 2;
                     centerY = v.getTop() + v.getHeight() / 2;

                    if (!firstIteration) {
                        // draw
                        Path path = new Path();
                        path.moveTo(prev_centerX, prev_centerY);
                        path.lineTo(centerX, centerY);

                        //canvas.drawLine(prev_centerX, prev_centerY, centerX, centerY, paint);
                        canvas.drawPath(path,paint);

                        Log.i("from", prev_centerX + "");
                        Log.i("from", prev_centerY + "");
                        Log.i("to", centerX + "");
                        Log.i("to", centerY + "");
                    } else {
                        firstIteration = false;
                        first_centerX = centerX;
                        first_centerY = centerY;
                    }

                    prev_centerX = centerX;
                    prev_centerY = centerY;
                }



                // connect last to first
                Path path = new Path();
                path.moveTo(centerX, centerY);
                path.lineTo(first_centerX, first_centerY);

                canvas.drawPath(path,paint);

                //canvas.drawLine(centerX, centerY, first_centerX, first_centerY, paint);
            }
        }
    }

}
