package com.example.p.jumptime;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Detects left and right swipes across a view.
 */
public class OnSwipeTouchListener implements View.OnTouchListener {

    private Context context;

    public OnSwipeTouchListener(Context context){
        this.context = context;
    }
    private final GestureDetector gestureDetector = new GestureDetector(context, new GestureListener());

    public boolean onTouch(final View v, final MotionEvent event){
        return gestureDetector.onTouchEvent(event);
    }

    protected final class GestureListener extends GestureDetector.SimpleOnGestureListener{

        private static final int SWIPE_THRESHOLD = 200;
        public static final int SWIPE_VELOCITY_THRESHOLD = 200;

        public boolean onDown(MotionEvent e){
            return super.onDown(e);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try { float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            result = (boolean) onSwipeRight();
                        } else {
                            result = onSwipeLeft();
                        }
                    }
                } else {
                    if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            result = onSwipeBottom();
                        } else {
                            result = onSwipeTop();
                        }
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }


    public boolean onSwipeRight() {
        return false;
    }

    public boolean onSwipeLeft() {
        return false;
    }

    public boolean onSwipeTop() {
        return false;
    }

    public boolean onSwipeBottom() {
        return false;
    }

}