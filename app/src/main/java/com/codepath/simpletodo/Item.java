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
}