package com.example.admin.wellrounder;

/**
 * Created by admin on 9/26/2017.
 * This class is used with the list_item layout in order to display all the posts on the news feed.
 */

public class ListItem {

    private String post;
    private String username;
    private String time;
    private String date;
    private String id;


    //delete this to fix
    public ListItem(String id, String post, String username, String time, String date) {
        this.id = id;
        this.post = post;
        this.username = username;
        this.time = time;
        this.date = date;
       // this.post_id = post_id; //delete this to fix
    }

    public String getPost() {
        return post;
    }

    public String getUserameWhoPosted() {
        return username;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public String getPostID(){
        return id;
    }
}

