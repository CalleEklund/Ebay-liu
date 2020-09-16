package com.example.liubiljett;

public class Post {
    private String title;
    private String price;
    private String desc;
    private int id;

    public Post(String title, String price, String desc) {
        this.title = title;
        this.price = price;
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getDesc() {
        return desc;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Post{" +
                "title='" + title +
                ", price='" + price +
                ", desc='" + desc +
                ", id=" + id;
    }
}
