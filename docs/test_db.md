# 4.DB処理テスト

UserMapperクラスのメソッドについてテストを行う。
準備してある空のファイルの以下のクラスを開く
`src/test/java/com/example/springboottestdemo/UserMapperTests.java`

```java
public class UserMapperTests {
    
}
```

## 4.1 テスト用のアノテーションを追加する

UserServiceTestsと同様の3行に加えて、`@Sql` アノテーションを利用して、クラス内のテストケース実行前に事前実行するsqlを指定する。

```diff
+@RunWith(SpringRunner.class)
+@SpringBootTest
+@ActiveProfiles("test")
+@Sql("classpath:/delete-test-data.sql") // テーブルのデータを事前削除するスクリプト
 public class UserMapperTests {
 
 }
```

今回は `src/test/resources/delete-test-data.sql` に記述されているとおり、事前にテーブルデータを全部削除するスクリプトになっている。

```sql
DELETE FROM users;
```

## 4.2 テスト用DBにデータ登録&取得を行うテスト実装

`@Mapper` アノテーションを利用しているため、UserMapperインターフェースを実装したクラスのインスタンスは、MyBatisが自動的にDIコンテナへ登録する。

そのため、UserMapperTestsクラスにDIする形で利用する。

```java
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
```

実行して問題なければOK
