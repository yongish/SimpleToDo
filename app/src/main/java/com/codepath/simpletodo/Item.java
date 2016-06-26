package com.codepath.simpletodo;

public class Item {
    public Long _id;
    public String name;

    public Item(String item) {
        this.name = item;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Long get_id() {
        return _id;
    }
}
