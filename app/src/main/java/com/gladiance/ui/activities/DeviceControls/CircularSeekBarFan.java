package com.gladiance.ui.activities.DeviceControls;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.gladiance.R;

public class CircularSeekBarFan extends View {


    public interface OnProgressChangeListener {
        void onProgressChanged(String progressText);
    }

    private Paint circlePaint;
    private Paint progressPaint;
    private Paint textPaint;

    private Bitmap thumbBitmap;
    private int thumbWidth;
    private int thumbHeight;

    private float progress = 0;
    private int min = 0;
    private int max = 5;
    // Constants
    private static final float ANGLE_PER_SECTION = 60f; // Angle per section (6 sections for 360 degrees)

    // Other existing fields and methods...

    private float previousAngle = 0; // To track previous touch angle
    private CircularSeekBarFan.OnProgressChangeListener progressChangeListener;

    public CircularSeekBarFan(Context context) {
        super(context);
        init();
    }

    public CircularSeekBarFan(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircularSeekBarFan(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setOnProgressChangeListener(OnProgressChangeListener listener) {
        this.progressChangeListener = listener;
    }

    private void init() {
        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setColor(Color.WHITE);
        circlePaint.setStrokeWidth(40);

        progressPaint = new Paint();
        progressPaint.setAntiAlias(true);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setColor(Color.parseColor("#FFA500"));
        progressPaint.setStrokeWidth(40); // Adjust thickness as needed

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(60); // Adjust text size as needed

        // Load the thumb image
        thumbBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.thumb_image);
        thumbWidth = thumbBitmap.getWidth();
        thumbHeight = thumbBitmap.getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float centerX = getWidth() / 2f;
        float centerY = getHeight() / 2f;
        float radius = Math.min(centerX, centerY) - 40; // Leave some margin for text

        // Draw the outer circle
        canvas.drawCircle(centerX, centerY, radius, circlePaint);

        // Draw the progress arc
        float startAngle = -90; // Start from top
        float sweepAngle = 360 * progress / (max - min);
        canvas.drawArc(centerX - radius, centerY - radius, centerX + radius, centerY + radius,
                startAngle, sweepAngle, false, progressPaint);

        // Calculate thumb position
        float thumbAngle = startAngle + sweepAngle;
        float thumbX = (float) (centerX + radius * Math.cos(Math.toRadians(thumbAngle))) - thumbWidth / 2;
        float thumbY = (float) (centerY + radius * Math.sin(Math.toRadians(thumbAngle))) - thumbHeight / 2;

        // Draw thumb image
        canvas.drawBitmap(thumbBitmap, thumbX, thumbY, null);

        // Draw text label
        String text = String.valueOf((int) progress + min);
        //  String text = String.valueOf((int) progress + min);
        if (progressChangeListener != null) {
            progressChangeListener.onProgressChanged(text);
        }

        Log.e(TAG, "onDraw: "+text);
        Rect textBounds = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), textBounds);
        float x = centerX - textBounds.exactCenterX();
        float y = centerY - textBounds.exactCenterY();
        canvas.drawText(text, x, y, textPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();
                float centerX = getWidth() / 2f;
                float centerY = getHeight() / 2f;
                float angle = (float) Math.toDegrees(Math.atan2(y - centerY, x - centerX));
                if (angle < 0) {
                    angle += 360;
                }

                // Calculate the angle relative to the start angle
                float startAngle = -90; // Start from top
                angle -= startAngle;
                if (angle < 0) {
                    angle += 360;
                }

                // Convert angle to progress
                float newProgress = angle / ANGLE_PER_SECTION; // Calculate progress based on angle per section

                // Update progress
                setProgress(newProgress);
                return true;
        }
        return super.onTouchEvent(event);
    }


    public void setProgress(float progress) {
        if (progress < 0) {
            this.progress = 0;
        } else if (progress > max) {
            this.progress = max;
        } else {
            this.progress = progress;
        }

        // Reset previous angle to start position
        previousAngle = -90; // Assuming start angle is -90 degrees

        invalidate(); // Trigger redraw
    }


}