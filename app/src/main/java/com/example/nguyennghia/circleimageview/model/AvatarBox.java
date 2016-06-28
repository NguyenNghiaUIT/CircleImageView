package com.example.nguyennghia.circleimageview.model;

/**
 * Created by nguyennghia on 27/06/2016.
 */
public class AvatarBox {
    private String mName;
    private Picture mPictures;

    public AvatarBox() {

    }

    public AvatarBox(String name, Picture picture) {
        this.mName = name;
        this.mPictures = picture;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public Picture getPictures() {
        return mPictures;
    }
}
