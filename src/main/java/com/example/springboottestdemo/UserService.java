package com.example.springboottestdemo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * UserControllerで利用されるメインロジックなどのサービスクラス
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper; // UserMapperをDIコンテナから利用
    
    // ユーザー名の文字数からグループIDを決定 (奇数:1, 偶数:2)
    Integer getGroupFromName(String name) {
        return (name.length() % 2 == 0) ? 2 : 1;
    }
    
    // ユーザーを登録
    void addUser(String name) {
        Integer groupId = this.getGroupFromName(name);
        userMapper.insert(name, groupId);
    }
    
    // 全ユーザーを取得
    List<User> findAll() {
        return userMapper.findAll();
    }
}
