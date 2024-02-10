package com.ictak.blogproject.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Users {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @JsonProperty("name")
    private String name;


    @JsonProperty("phone")
    private String phone;

    @JsonProperty("dob")
    private String dob;

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;


    public Users() {
    }

    public Users(Long id, String name, String phone, String dob, String email, String password) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.dob = dob;
        this.email = email;
        this.password = password;
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getDob() {
        return dob;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
