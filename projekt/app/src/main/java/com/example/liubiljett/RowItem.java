package com.example.liubiljett;

public class RowItem {

    private String headline;
    private String price;
    private int imageID;

    public RowItem(final String headline, final String price, final int imageID){
        this.headline = headline;
        this.price = price;
        this.imageID = imageID;
    }

    String getHeadline() {
        return headline;
    }

    //public void setHeadline(String headline) {this.headline = headline;}

    String getPrice() {
        return price;
    }

    //public void setPrice(String price) {this.price = price;}

    int getImageID() {
        return imageID;
    }

    //public void setImageID(int imageID) {this.imageID = imageID;}
}
