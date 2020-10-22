package com.nishita.quiz;

public class ClassCard {

    private int mImageResource1;
    private String mClass;
    private String mCode;

    public ClassCard(int ImageResource1, String Class, String Code){
        mImageResource1 = ImageResource1;
        mClass = Class;
        mCode = Code;
    }

    public int getImageResource1(){
        return mImageResource1;
    }

    public String getCourse1() {
        return mClass;
    }

    public String getCode(){
        return mCode;
    }
}
