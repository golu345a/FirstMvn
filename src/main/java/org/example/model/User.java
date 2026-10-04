package org.example.model;

public class User {
    private long id;
    private String name;
    private String email;
    private String mobile;

    public User() {

    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "User{id=%d, name='%s', email='%s', mobile='%s'}".formatted(id, name, email, mobile);
    }
}
