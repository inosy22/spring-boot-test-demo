package com.example.springboottestdemo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Sql("classpath:/delete-test-data.sql") // テーブルのデータを事前削除するスクリプト
public class UserMapperTests {
    @Autowired
    UserMapper userMapper;
    
    @Test
    public void insertUsersCountTest() throws Exception {
        userMapper.insert("hoge", 1);
        userMapper.insert("fuga", 2);
        List<User> users = userMapper.findAll();
        assertThat(users.size()).isEqualTo(2);
    }
}
