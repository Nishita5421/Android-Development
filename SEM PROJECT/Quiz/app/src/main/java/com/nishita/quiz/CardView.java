package com.nishita.quiz;



import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.TextView;

public class CardView implements Parcelable {

    private int mImageResource;
    private String mCourse;
    private String mSchool;

    public CardView(int ImageResource, String Course, String School){
        mImageResource = ImageResource;
        mCourse = Course;
        mSchool = School;
    }

    protected CardView(Parcel in) {
        mCourse = in.readString();
    }

    public static final Creator<CardView> CREATOR = new Creator<CardView>() {
        @Override
        public CardView createFromParcel(Parcel in) {
            return new CardView(in);
        }

        @Override
        public CardView[] newArray(int size) {
            return new CardView[size];
        }
    };

    public int getImageResource(){
        return mImageResource;
    }

    public String getCourse() {
        return mCourse;
    }

    public String getSchool(){
        return mSchool;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mCourse);
    }
}

