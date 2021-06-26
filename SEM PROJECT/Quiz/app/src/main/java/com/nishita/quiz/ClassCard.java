package com.nishita.quiz;

import android.os.Parcel;
import android.os.Parcelable;

public class ClassCard implements Parcelable {

    private int mImageResource1;
    private String mClass;
    private String mCode;

    public ClassCard(int ImageResource1, String Class, String Code){
        mImageResource1 = ImageResource1;
        mClass = Class;
        mCode = Code;
    }
    protected ClassCard(Parcel in) {
        mClass = in.readString();
    }
    public static final Parcelable.Creator<ClassCard> CREATOR = new Parcelable.Creator<ClassCard>() {
        @Override
        public ClassCard createFromParcel(Parcel in) {
            return new ClassCard(in);
        }

        @Override
        public ClassCard[] newArray(int size) {
            return new ClassCard[size];
        }
    };
    public int getImageResource1(){
        return mImageResource1;
    }

    public String getCourse1() {
        return mClass;
    }

    public String getCode(){
        return mCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mClass);
    }
}
