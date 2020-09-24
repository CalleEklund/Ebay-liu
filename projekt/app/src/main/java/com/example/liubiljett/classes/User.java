package com.example.liubiljett.classes;

import androidx.annotation.NonNull;

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
    private List<Integer> user_following;

    public User(String name, String email, String password, int id, boolean hasAccessToken,
                String accessToken, List<Post> created_posts, List<Post> liked_posts,
                List<Integer> user_following) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.id = id;
        this.hasAccessToken = hasAccessToken;
        this.accessToken = accessToken;
        this.created_posts = created_posts;
        this.liked_posts = liked_posts;
        this.user_following = user_following;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
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

    public void addPost(Post p) {
        this.created_posts.add(p);
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
    public boolean isFollowed(String userId){
        if (this.user_following == null) {return false;}
        for (int followingId : this.user_following){
            if (followingId ==  Integer.parseInt(userId)){
                return true;
            }
        }
        return false;
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "name='" + name +
                ", email='" + email +
                ", password='" + password +
                ", id=" + id +
                ", hasAccessToken=" + hasAccessToken +
                ", accessToken='" + accessToken +
                ", created_posts=" + created_posts +
                ", liked_posts=" + liked_posts +
                ", user_followed=" + user_following;
    }
}
