package com.example.homework6;

import androidx.annotation.NonNull;

public class Student {
    public int id = -1;
    public String name;
    public String gender;
    public int age;

    public Student() {
    }

    public Student(int id, String name, String gender, int age) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    @NonNull
    @Override
    public String toString() {
        String res = "";
        res += "id:" + id + ", name:" + name + ", gender:" + gender + ", age:" + age;
        return res;
    }
}
