package com.codepath.simpletodo;

public class Item {

    public Long _id; // for cupboard
    public String name; // task name
    public String priority;

    public Item() {
        this.name = "noName";
        this.priority = "Low";
    }

    public Item(String name, String priority) {
        this.name = name;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public void setPriority(String priority) { this.priority = priority; }

    public String getPriority() { return priority; }
}