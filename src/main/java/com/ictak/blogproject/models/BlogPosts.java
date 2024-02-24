package com.ictak.blogproject.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BlogPosts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @JsonProperty("UserId")
    private Long UserId;


    @JsonProperty("message")
    private String message;

    public BlogPosts() {
    }

    public BlogPosts(Long id, Long userId, String message) {
        this.id = id;
        UserId = userId;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return UserId;
    }

    public String getMessage() {
        return message;
    }
}
