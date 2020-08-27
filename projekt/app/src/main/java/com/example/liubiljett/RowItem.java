package com.example.liubiljett;

public class RowItem {

    private String headline;
    private String price;
    private int imageID;
    private String description;

    public RowItem(final String headline, final String price, final int imageID, final String description){
        this.headline = headline;
        this.price = price;
        this.imageID = imageID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHeadline() {
        return headline;
    }

    //public void setHeadline(String headline) {this.headline = headline;}

    public String getPrice() {
        return price;
    }

    //public void setPrice(String price) {this.price = price;}

    public int getImageID() {
        return imageID;
    }

    //public void setImageID(int imageID) {this.imageID = imageID;}
}
