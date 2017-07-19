package com.example.springboottestdemo;

/**
 * Usersテーブル用構造体
 * lombok使うと楽に宣言できるが今回は利用しない
 */
public class User {
    private Integer id;
    private String name;
    private Integer groupId;
    
    public User(Integer id, String name, Integer groupId) {
        this.id = id;
        this.name = name;
        this.groupId = groupId;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }
    
    public Integer getGroupId() {
        return this.groupId;
    }
}
