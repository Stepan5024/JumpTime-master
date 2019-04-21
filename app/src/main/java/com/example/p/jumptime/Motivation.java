package com.example.p.jumptime;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewSwitcher;

public class Motivation extends Fragment implements ViewSwitcher.ViewFactory {
    private ImageSwitcher mImageSwitcher;

    int position = 0;
    private int[] mImageIds = {R.drawable.ic_1, R.drawable.ic_2,
            R.drawable.ic_3,};
    Button forward;
    Button previos;
    private GestureDetector mGestureDetector;
    private static final int SWIPE_MIN_DISTANCE = 20;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 100;

    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_motivation, container, false);
        forward = view.findViewById(R.id.buttonForward);
        previos = view.findViewById(R.id.buttonPrev);
        mImageSwitcher = (ImageSwitcher) view.findViewById(R.id.imageSwitcher);
        mImageSwitcher.setFactory(this);
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPositionNext();
                mImageSwitcher.setImageResource(mImageIds[position]);
            }
        });
        previos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPositionPrev();
                mImageSwitcher.setImageResource(mImageIds[position]);
            }
        });
        Animation inAnimation = new AlphaAnimation(0, 1);
        inAnimation.setDuration(2000);
        Animation outAnimation = new AlphaAnimation(1, 0);
        outAnimation.setDuration(2000);

        mImageSwitcher.setInAnimation(inAnimation);
        mImageSwitcher.setOutAnimation(outAnimation);

        mImageSwitcher.setImageResource(mImageIds[0]);

        mGestureDetector = new GestureDetector(getContext(), new GestureDetector.OnGestureListener() {
           public boolean onInterceptTouchEvent(MotionEvent e){
               return false;
           }
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                setPositionNext();
                mImageSwitcher.setImageResource(mImageIds[position]);
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                try {
                    onInterceptTouchEvent(e1);
                    if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                        return false;
                    // справа налево
                    if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                            && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                        setPositionNext();
                        mImageSwitcher.setImageResource(mImageIds[position]);
                    } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                            && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                        // слева направо
                        setPositionPrev();
                        mImageSwitcher.setImageResource(mImageIds[position]);
                    }
                } catch (Exception e) {
                    // nothing
                    return true;
                }
                return true;
            }
        });
        return view;
    }



    public void setPositionNext() {
        position++;
        if (position > mImageIds.length - 1) {
            position = 0;
        }
    }

    public void setPositionPrev() {
        position--;
        if (position < 0) {
            position = mImageIds.length - 1;
        }
    }

    @Override
    public View makeView() {
        ImageView imageView = new ImageView(getContext());
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setLayoutParams(new
                ImageSwitcher.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        imageView.setBackgroundColor(0xFF000000);
        return imageView;
    }




}
