package com.example.p.jumptime;

import android.net.Uri;
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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class Motivation extends Fragment implements ViewSwitcher.ViewFactory {
    private ImageSwitcher mImageSwitcher;

    int position = 0;
    private int[] mImageIds = {R.drawable.ic_1, R.drawable.ic_2,
            R.drawable.ic_3,R.drawable.ic_4,R.drawable.ic_5, R.drawable.ic_6,
            R.drawable.ic_7,R.drawable.ic_8,R.drawable.ic_9,};
    //String[] uri = {"gs://organiser4you.appspot.com/1.png", "gs://organiser4you.appspot.com/2.png", "gs://organiser4you.appspot.com/3.png"};
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
               /* int a = 0; //"от"
                int b = 2; //"до"
                int random_number = a + (int) (Math.random() * b);*/
                //потом наладить
                /*FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReferenceFromUrl(uri[random_number1]);
                storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        Picasso.with(getContext()).load(uri).into((Target) mImageSwitcher);

                    }
                });*/
                mImageSwitcher.setImageResource(mImageIds[position]);
            }
        });
        previos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPositionPrev();
                //потом наладить
                 /*FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReferenceFromUrl(uri[random_number1]);
                storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        Picasso.with(getContext()).load(uri).into((Target) mImageSwitcher);

                    }
                });*/
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
