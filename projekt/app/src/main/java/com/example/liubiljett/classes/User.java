package com.example.liubiljett.classes;

import java.util.List;

public class User {
    private String name;
    private String email;
    private String password;
    private int id;
    private boolean hasAccessToken;
    private String accessToken;
    private List<Post> created_posts;
    private List<Post> liked_posts;

    public User(String name, String email, String password, int id, boolean hasAccessToken, List<Post> created_posts, List<Post> liked_posts) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.id = id;
        this.hasAccessToken = hasAccessToken;
        this.accessToken = "";
        this.created_posts = created_posts;
        this.liked_posts = liked_posts;

    }

    public User() {

    }

    public int getUserId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAccessToken() {
        return hasAccessToken;
    }

    public List<Post> getCreated_post() {
        return created_posts;
    }

    public List<Post> getLiked_post() {
        return liked_posts;
    }

    public void setCreated_post(List<Post> created_post) {
        this.created_posts = created_post;
    }

    public void addPost(Post p) {
        this.created_posts.add(p);
    }

    public void setLiked_post(List<Post> liked_post) {
        this.liked_posts = liked_post;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setHasAccessToken(boolean hasAccessToken) {
        this.hasAccessToken = hasAccessToken;
    }

    public boolean isOwnPost(Post p){
        if(this.created_posts == null){return false;}
        for (Post userCreatedPost :  this.created_posts){
            if(userCreatedPost.getId() == p.getId()){
                return true;
            }
        }
        return false;
    }
    public boolean isLikedPost(Post p){
        if(this.liked_posts == null){return false;}
        for (Post userLikedPost : this.liked_posts){
            if (userLikedPost.getId() == p.getId()){
                return true;
            }
        }
        return false;
    }
    @Override
    public String toString() {
        return
                "name: " + name +
                        ", email: " + email +
                        ", password: " + password +
                        ", userId: " + id +
                        ", hasAccessToken: " + hasAccessToken +
                        ", userAccessToken:" + accessToken +
                        ", created_post: " + created_posts +
                        ", liked_post: " + liked_posts
                ;
    }
}
