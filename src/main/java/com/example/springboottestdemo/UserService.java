package com.example.springboottestdemo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    
    List<User> findAll() {
        return userMapper.findAll();
    }
    
    void addUser(String name) {
        Integer groupId = this.getGroupFromName(name);
        userMapper.insert(name, groupId);
    }
    
    Integer getGroupFromName(String name) {
        return (name.length() % 2 == 0) ? 2 : 1;
    }
}
