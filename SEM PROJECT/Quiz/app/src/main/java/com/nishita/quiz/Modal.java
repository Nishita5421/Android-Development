package com.nishita.quiz;

public class Modal {
    private String text;
    private int image;

    public Modal(String text,int image)
    {
        this.text=text;
        this.image=image;
    }
    public void setText()
    {
        this.text=text;
    }
    public void setImageResource()
    {
        this.image= image;
    }

    public String getText()
    {
        return text;
    }
    public int getImageResource()
    {
        return image;
    }
}


