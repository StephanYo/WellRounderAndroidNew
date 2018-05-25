package com.example.admin.wellrounder;

/**
 * Created by admin on 10/18/2017.
 */

public class CommentList {
    public String comment;
    public String username;
    public String post_id;

    public CommentList(String comment, String username, String post_id) {

        this.comment = comment;
        this.username = username;
        this.post_id = post_id;
    }

    public String getComment() {
        return comment;
    }

    public String getUsernameCMT() {
        return username;
    }

    public String getPost_id() {
        return post_id;
    }


}
