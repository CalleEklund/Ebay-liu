package com.example.liubiljett.classes;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * Post class used to store posts, there are two different constructors
 * one for easier handling to VolleyService and one for retrieving posts from the database
 */
public class Post {
    private String title;
    private String price;
    private String desc;
    private List<String> commentedby;
    private List<String> comments;
    private int id;

    public Post(String title, String price, String desc) {

        this.title = title;
        this.price = price;
        this.desc = desc;
    }

    public Post(String title, String price, String desc, List<String> commentedby, List<String> comments) {
        this.title = title;
        this.price = price;
        this.desc = desc;
        this.commentedby = commentedby;
        this.comments = comments;
    }

    /**
     * Getters
     */
    public List<String> getCommentedBy() {
        return commentedby;
    }

    public List<String> getComments() {
        return comments;
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



    @NonNull
    @Override
    public String toString() {
        return "Post{" +
                "title='" + title +
                ", price='" + price +
                ", desc='" + desc +
                ", commentedby=" + commentedby +
                ", comments=" + comments +
                ", id=" + id;
    }
}

