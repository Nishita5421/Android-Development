package com.task1.retrofit1st;

public class Marvel {
    private String bio,name,realname,team,firstappearance,createdby,publisher,imageurl;

    public Marvel (String bio,String name,String realname,String team,String firstappearance,String createdby,String publisher,String imageurl) {
        this.name = name;
        this.realname = realname;
        this.team = team;
        this.firstappearance = firstappearance;
        this.createdby = createdby;
        this.publisher = publisher;
        this.imageurl = imageurl;
        this.bio = bio;
    }

    public String getBio() {
        return bio;
    }

    public String getCreatedby() {
        return createdby;
    }

    public String getFirstappearance() {
        return firstappearance;
    }

    public String getImageurl() {
        return imageurl;
    }

    public String getName() {
        return name;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getRealname() {
        return realname;
    }

    public String getTeam() {
        return team;
    }
}
