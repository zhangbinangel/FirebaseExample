package com.mushan.firebase.entity;

/**
 * Created by ZhangBin on 17/5/31.
 */

public class BlogSimpleEntity {
    public enum Type{
        AD,
        BLOG
    }
    public BlogSimpleEntity(){}
    public BlogSimpleEntity(String url,String ti,String msg,Type type)
    {
        this.type = type;
        this.url = url;
        this.title = ti;
        this.message = msg;
    }
    private Type type;
    private String url;
    private String title;
    private String message;

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
}
