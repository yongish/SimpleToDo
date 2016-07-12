package com.codepath.simpletodo;

public class Item {

    public Long _id; // for cupboard
    public String name; // bunny name
    //public Integer cuteValue ; // bunny cuteness


    public Item() {
        this.name = "noName";
        //this.cuteValue = 0;
    }

    public Item(String name) {//, Integer cuteValue) {
        this.name = name;
        //this.cuteValue = cuteValue;
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
}