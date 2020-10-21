package com.nishita.quiz;



import android.media.Image;
import android.widget.TextView;

public class CardView {

    private int mImageResource;
    private String mCourse;
    private String mSchool;

    public CardView(int ImageResource, String Course, String School){
        mImageResource = ImageResource;
        mCourse = Course;
        mSchool = School;
    }

    public int getImageResource(){
        return mImageResource;
    }

    public String getCourse() {
        return mCourse;
    }

    public String getSchool(){
        return mSchool;
    }
}
