package com.example.p.jumptime.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewSwitcher;

import com.example.p.jumptime.R;

/*
 * отивирует пользователя работать над своей целью дальше
 *
 * */
public class Motivation extends Fragment implements ViewSwitcher.ViewFactory {
    private ImageSwitcher mImageSwitcher;

    int position = 0;
    private int[] mImageIds = {R.drawable.ic_1, R.drawable.ic_2,
            R.drawable.ic_3, R.drawable.ic_4, R.drawable.ic_5, R.drawable.ic_6,
            R.drawable.ic_7, R.drawable.ic_8, R.drawable.ic_9, R.drawable.ic_10,};

    Button forward;
    Button previos;


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
