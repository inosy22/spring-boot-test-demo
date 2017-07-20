package com.example.springboottestdemo;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * MyBatisの機能を利用するMapperクラス
 * usersテーブルのレコードをUser構造体にマッピングしたりする
 */
@Mapper
interface UserMapper {
	// ユーザーレコード全取得
    @Select("SELECT id, name, group_id FROM users")
    List<User> findAll();
    
    //　ユーザーレコード新規登録
    @Insert("INSERT INTO users (name, group_id) VALUES (#{name}, #{groupId})")
    long insert(@Param("name") String name, @Param("groupId") Integer groupId);
}
