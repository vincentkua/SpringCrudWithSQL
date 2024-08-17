package com.example.demo.model;

public class Pokemon {
    private Integer id;
    private String name;
    private Integer hpmax;
    private Integer level;
    
    public Pokemon() {
    }
    

    public Pokemon(Integer id, String name, Integer hpmax, Integer level) {
        this.id = id;
        this.name = name;
        this.hpmax = hpmax;
        this.level = level;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getHpmax() {
        return hpmax;
    }
    public void setHpmax(Integer hpmax) {
        this.hpmax = hpmax;
    }
    public Integer getLevel() {
        return level;
    }
    public void setLevel(Integer level) {
        this.level = level;
    }
    @Override
    public String toString() {
        return "Pokemon [id=" + id + ", name=" + name + ", hpmax=" + hpmax + ", level=" + level + "]";
    } 

    

     
}
