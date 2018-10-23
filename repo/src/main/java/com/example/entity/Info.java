package com.example.entity;

public class Info {

    private Long id;
    private String info;
    private String creationDateTime;

    public Info(){};

    public Info(Long id, String info, String creationDateTime){
        this.id = id;
        this.info = info;
        this.creationDateTime = creationDateTime;
    }

    public Long getId() {
        return id;
    }

    public Info setId(Long id) {
        this.id = id;
        return this;
    }

    public String getInfo() {
        return info;
    }

    public Info setInfo(String info) {
        this.info = info;
        return this;
    }

    public String getCreationDateTime() {
        return creationDateTime;
    }

    public Info setCreationDateTime(String creationDateTime) {
        this.creationDateTime = creationDateTime;
        return this;
    }
}
