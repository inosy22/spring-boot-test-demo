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

```java
// ここから
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test") // テスト用設定ファイルの読み込みを宣言
@Sql("classpath:/delete-test-data.sql") // テーブルのデータを事前削除するスクリプト
// ここまでコピペ
public class UserMapperTests {

}
```

#### `@ActiveProfiles("test")` について、

> `src/test/resources/application-test.properties` を読み込むようにしている。
> 
> 今回は用意してあり、本番との差分は以下のとおり。
> 
> ```diff
>　spring.datasource.driver-class-name=org.h2.Driver
> -spring.datasource.url=jdbc:h2:./.data/appdb
> +spring.datasource.url=jdbc:h2:./.data/testdb
>　spring.datasource.username=root
>　spring.datasource.password=
>```


#### `@Sql("classpath:/delete-test-data.sql")` について

> `src/test/resources/delete-test-data.sql` に記述されているとおり、事前にテーブルデータを全部削除するスクリプトになっている。
> 
> ```sql
> DELETE FROM users;
> ```
> 
> これで、このクラスのテストが実行される前にusersテーブルにレコードがない状態でテストすることを保証できる。


## 4.2 テスト用DBにデータ登録&取得を行うテスト実装

`@Mapper` アノテーションを利用しているため、UserMapperインターフェースを実装したクラスのインスタンスは、MyBatisが自動的にDIコンテナへ登録する。

そのため、UserMapperTestsクラスにDIする形で利用する。

以下をUserMapperクラス内に記述する。

```java
@Autowired
UserMapper userMapper;

@Test
public void insertUsersCountTest() throws Exception {
    userMapper.insert("hoge", 1);
    userMapper.insert("fuga", 2);
    List<User> users = userMapper.findAll();
    assertThat(users.size()).isEqualTo(2);
}
```

実行して問題なければOK
