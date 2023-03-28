package com.example.springapi.api.model;

public class User { private int _id;
    private String password;

    private String name;


    private String lastname;

    private String email;

    public User(int _id, String password, String name, String lastname, String email) {
        this._id = _id;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
    }

    public User() {}

    public int get_id() {
        return _id;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }
    public void set_id(int _id) {
        this._id = _id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "_id=" + _id +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }



}
