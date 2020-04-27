package com.nishita.notepad;

public class ExampleItem {
    private String text;
    private int image;

    public ExampleItem(String text,int image)
    {
        this.text=text;
        this.image=image;
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
