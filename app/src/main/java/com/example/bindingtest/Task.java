package com.example.bindingtest;

public class Task {
    private String title;
    private String description;
    private int _id;
    public Task(String title, String description) {

        this.title = title;
        this.description = description;
    }
    //getters and setters


    public Task(String title, String description, int _id) {
        this.title = title;
        this.description = description;
        this._id = _id;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
