package com.codepath.simpletodo;

public class Item {

    public Long _id; // for cupboard
    public String name; // task name
    public Priority priority;

    public Item() {
        this.name = "noName";
        this.priority = Priority.LOW;
    }

    public Item(String name, Priority priority) {
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

    public void setPriority(Priority priority) { this.priority = priority; }

    public Priority getPriority() { return priority; }
}