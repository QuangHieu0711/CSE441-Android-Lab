package com.example.phonelistviewapp;

public class Phone {
    private String namephone;
    private int imagephone;

    public Phone(String namephone, int imagephone) {
        this.namephone = namephone;
        this.imagephone = imagephone;
    }

    public String getNamephone() {
        return namephone;
    }

    public int getImagephone() {
        return imagephone;
    }
}
