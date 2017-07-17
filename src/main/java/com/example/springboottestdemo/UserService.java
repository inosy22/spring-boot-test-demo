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
    
    List<User> findByName(String name) {
        return userMapper.findByName(name);
    }
    
    long insert(String name) {
        return userMapper.insert(name);
    }
}
