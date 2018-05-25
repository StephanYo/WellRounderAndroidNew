package com.example.admin.wellrounder;

/**
 * Created by admin on 10/4/2017.
 */

public class TestUserUpdateLayout {
    private int id;
    private String username;
    private String email;
    private String phonenumber;
    private String lastname;
    private String firstname;
    private String middlename;

    public TestUserUpdateLayout(int id, String username, String email, String phonenumber, String lastname, String firstname, String middlename) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phonenumber = phonenumber;
        this.lastname = lastname;
        this.firstname = firstname;
        this.middlename = middlename;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getMiddlename() {
        return middlename;
    }
}
