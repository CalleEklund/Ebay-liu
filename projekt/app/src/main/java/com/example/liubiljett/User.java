package com.example.liubiljett;

import java.util.List;

public class User {
    private String user_name;
    private String email;
    private String password;
    private int userId;
    private boolean hasAccessToken;
    private List<Post> created_post;
    private List<Post> liked_post;

    public User(String user_name, String email, String password, int userId, boolean hasAccessToken, List<Post> created_post, List<Post> liked_post) {
        this.user_name = user_name;
        this.email = email;
        this.password = password;
        this.userId = userId;
        this.hasAccessToken = hasAccessToken;
        this.created_post = created_post;
        this.liked_post = liked_post;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return user_name.substring(1, user_name.length() - 1);
    }

    public String getEmail() {
        return email.substring(1, email.length() - 1);
    }

    public String getPassword() {
        return password;
    }

    public boolean isAccessToken() {
        return hasAccessToken;
    }

    public List<Post> getCreated_post() {
        return created_post;
    }

    public List<Post> getLiked_post() {
        return liked_post;
    }

    public void setCreated_post(List<Post> created_post) {
        this.created_post = created_post;
    }

    public void setLiked_post(List<Post> liked_post) {
        this.liked_post = liked_post;
    }

    @Override
    public String toString() {
        return
                "user_name: " + user_name +
                        ", email: " + email +
                        ", password: " + password +
                        ", userId: " + userId +
                        ", hasAccessToken: " + hasAccessToken +
                        ", created_post: " + created_post +
                        ", liked_post: " + liked_post
                ;
    }
}
