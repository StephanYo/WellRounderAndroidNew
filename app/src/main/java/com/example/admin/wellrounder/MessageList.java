package com.example.admin.wellrounder;

/**
 * Created by admin on 12/7/2017.
 */

public class MessageList {
    public String id;
    public String chat_id;
    public String reciver_id;
    public String sender_id;
    public String message;
    public String time_stamp;

    public MessageList(String id, String chat_id, String reciver_id, String sender_id, String message, String time_stamp) {
        this.id = id;
        this.chat_id = chat_id;
        this.reciver_id = reciver_id;
        this.sender_id = sender_id;
        this.message = message;
        this.time_stamp = time_stamp;
    }

    public String getMessage(){
        return message;
    }

    public String getMessageId() {
        return id;
    }

    public void setMessageId(String id) {
        this.id = id;
    }

    public String getChat_id() {
        return chat_id;
    }

    public void setChat_id(String chat_id) {
        this.chat_id = chat_id;
    }

    public String getReciver_id() {
        return reciver_id;
    }

    public void setReciver_id(String reciver_id) {
        this.reciver_id = reciver_id;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(String time_stamp) {
        this.time_stamp = time_stamp;
    }
}
