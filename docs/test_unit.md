# 3.単体テスト

UserServiceクラスのメソッドについて、単体テストを行う。
準備してある空のファイルの以下のクラスを開く
`src/test/java/com/example/springboottestdemo/UserServiceTests.java`

```java
public class UserServiceTests {
    
}
```

## テスト用アノテーションを追加する

```diff
+@RunWith(SpringRunner.class)
+@SpringBootTest
+@ActiveProfiles("test")
 public class UserServiceTests {
 
 }
```

> 以下の2行は決まり文句
> ```java
> @RunWith(SpringRunner.class)
> @SpringBootTest
> ```

> 以下の1行はテスト用の設定ファイルを読み込む設定
> ```java
> @ActiveProfiles("test")
> ```
> 
> `src/test/resources/application-test.properties` を読み込むようにしている。
> 
> 今回は用意してあり、本番との差分は以下のとおり。
> 
> ```diff
>  spring.datasource.driver-class-name=org.h2.Driver
> -spring.datasource.url=jdbc:h2:./.data/appdb
> +spring.datasource.url=jdbc:h2:./.data/testdb
>  spring.datasource.username=root
>  spring.datasource.password=
> ```


## グループIDを生成するメソッドのテストを作ってみる

`@Test` アノテーションをつけることで、テストケースメソッドと認識されてテスト実行時に実行されるので、以下のメソッドをクラスに追加する。

```java
@Test
public void 偶数文字数のグループID生成テスト() throws Exception {
    UserService userService = new UserService();
    String name = "testname";
    Integer groupId = 2; // nameの文字数は偶数なので2になるはず
    assertThat(userService.getGroupFromName(name)).isEqualTo(groupId);
}
```


## JUnitTestの実行

プロジェクトを右クリック -> 「Run As」->「JUnit Test」でテスト実行

<img width="500" alt="2017-07-20 19 44 07" src="https://user-images.githubusercontent.com/10849664/28413840-0495f16c-6d84-11e7-9f96-14a3e826c79f.png">

実行して成功するとこんな感じに表記されるはず

<img width="304" alt="2017-07-20 19 43 01" src="https://user-images.githubusercontent.com/10849664/28413849-0d4f7dfa-6d84-11e7-90c3-62a92cb6e1a1.png">
