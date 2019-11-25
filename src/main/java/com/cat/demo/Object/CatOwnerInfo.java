package com.cat.demo.Object;

import java.util.ArrayList;
import java.util.List;

public class CatOwnerInfo {
    private String name;
    private String gender;
    private String age;
    private List<CatInfo> pets = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public List<CatInfo> getPets() {
        return pets;
    }

    public void setPets(List<CatInfo> pets) {
        this.pets = pets;
    }
}
