package com.example.liubiljett;

public class RowItem {

    private String title;
    private String price;
    private int imageID;
    private String desc;

    public RowItem(final String title, final String price, final String desc){
        this.title = title;
        this.price = price;
        this.imageID = imageID;
        this.desc = desc;
    }

    public String getDescription() {
        return desc;
    }

    public void setDescription(String desc) {
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    //public void setHeadline(String title) {this.title = title;}

    public String getPrice() {
        return price;
    }

    //public void setPrice(String price) {this.price = price;}

    public int getImageID() {
        return imageID;
    }

    //public void setImageID(int imageID) {this.imageID = imageID;}

    @Override
    public String toString() {
        return "RowItem{" +
                "title='" + title + '\'' +
                ", price='" + price + '\'' +
                ", imageID=" + imageID +
                ", desc='" + desc + '\'' +
                '}';
    }
}
