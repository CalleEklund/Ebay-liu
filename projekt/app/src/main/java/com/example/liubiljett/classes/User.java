package com.example.liubiljett.classes;

import androidx.annotation.NonNull;

import java.util.Iterator;
import java.util.List;

/**
 * User class for storing users, the class is used for retrieving and storing users from the database.
 */
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

    /**
     * Getters
     */
    public int getId() {
        return id;
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

    public List<Integer> getUser_following() {
        return user_following;
    }

    /**
     * Setters
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setHasAccessToken(boolean hasAccessToken) {
        this.hasAccessToken = hasAccessToken;
    }

    public void addLikedPosts(Post p){
        this.liked_posts.add(p);
    }

    public void removeLikedPosts(Post p){
        Iterator<Post> it = liked_posts.iterator();
        while (it.hasNext()) {
            Post search = it.next();
            if (search.equals(p)) {
                it.remove();
            }
        }
    }

    public void addFollowedUser(int userId){
        this.user_following.add(userId);
    }

    public void removeFollowedUser(int userId){
        for (int i = 0; i < user_following.size(); i++) {
            if(user_following.get(i) == userId) {
                user_following.remove(i);
            }
        }

    }
    /**
     *
     * @param p (Post class)
     * @return if the post is created by the user
     */
    public boolean isOwnPost(Post p){
        if(this.created_posts == null){return false;}
        for (Post userCreatedPost :  this.created_posts){
            if(userCreatedPost.getId() == p.getId()){
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param p (Post class)
     * @return if the post is already liked by the user
     */
    public boolean isLikedPost(Post p){
        if(this.liked_posts == null){return false;}
        for (Post userLikedPost : this.liked_posts){
            if (userLikedPost.getId() == p.getId()){
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param userId (int) User's id
     * @return if the user is already following the user
     */
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
