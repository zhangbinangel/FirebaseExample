package com.mushan.firebase.entity;

import java.io.Serializable;

/**
 * Created by ZhangBin on 17/5/31.
 */

public class BlogSimpleEntity implements Serializable{
    public enum Type{
        AD,
        BLOG
    }
    public BlogSimpleEntity(){}
    private String name;
    private Type type;
    private String url;
    private String title;
    private String message;
    private int like;
    private int unlike;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getLike() {
        return like;
    }

    public int getUnlike() {
        return unlike;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public void setUnlike(int unlike) {
        this.unlike = unlike;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
