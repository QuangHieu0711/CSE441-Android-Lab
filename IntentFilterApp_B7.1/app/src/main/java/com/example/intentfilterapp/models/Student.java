package com.example.intentfilterapp.models;

public class Student {
    private String name;
    private String studentClass;
    private int avatarResId;

    public Student(String name, String studentClass, int avatarResId) {
        this.name = name;
        this.studentClass = studentClass;
        this.avatarResId = avatarResId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public int getAvatarResId() {
        return avatarResId;
    }

    public void setAvatarResId(int avatarResId) {
        this.avatarResId = avatarResId;
    }
}
