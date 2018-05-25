package com.example.admin.wellrounder;

/**
 * Created by admin on 12/7/2017.
 */

public class MessageMainList {
    public String id;
    public String chat_id;
    public String id_1;
    public String id_2;
    public String rec_username;
    public String send_username;
    public String last_message;
    public String messages_amt;
    public String time_stamp;

    public MessageMainList(String id, String chat_id, String id_1, String id_2, String rec_username, String send_username, String last_message, String messages_amt, String time_stamp) {
        this.id = id;
        this.chat_id = chat_id;
        this.id_1 = id_1;
        this.id_2 = id_2;
        this.rec_username = rec_username;
        this.send_username = send_username;
        this.last_message = last_message;
        this.messages_amt = messages_amt;
        this.time_stamp = time_stamp;

    }

    public String getLast_message() {
        return last_message;
    }

    public String getRec_username() {
        return rec_username;
    }

    public String getSend_username() {
        return send_username;
    }

    public String getMessages_amt() {
        return messages_amt;
    }

    public String getMessages(){
        return messages_amt;
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


    public String getId_1() {
        return id_1;
    }

    public void setId_1(String id_1) {
        this.id_1 = id_1;
    }

    public String getId_2() {
        return id_2;
    }

    public void setId_2(String id_2) {
        this.id_2 = id_2;
    }

    public void setMessages(String messages) {
        this.messages_amt = messages_amt;
    }

    public String getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(String time_stamp) {
        this.time_stamp = time_stamp;
    }
}
