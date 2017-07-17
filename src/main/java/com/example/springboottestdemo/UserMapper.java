package com.example.springboottestdemo;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
interface UserMapper {
    @Select("SELECT id, name FROM users")
    List<User> findAll();
    
    @Select("SELECT id, name FROM users WHERE name = #{name}")
    List<User> findByName(@Param("name") String name);
    
    @Insert("INSERT INTO users (name) VALUES (#{name})")
    long insert(@Param("name") String name);
}
