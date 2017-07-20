# 3.単体テスト

UserServiceクラスのメソッドについて、単体テストを行う。
準備してある空のファイルの以下のクラスを開く
`src/test/java/com/example/springboottestdemo/UserServiceTests.java`

```java
public class UserServiceTests {
    
}
```

## 3.1 テスト用アノテーションを追加する

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


## 3.2 グループIDを生成するメソッドのテストを作ってみる

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


## 3.3 JUnitTestの実行

プロジェクトを右クリック -> 「Run As」->「JUnit Test」でテスト実行

<img width="500" alt="2017-07-20 19 44 07" src="https://user-images.githubusercontent.com/10849664/28413840-0495f16c-6d84-11e7-9f96-14a3e826c79f.png">

実行して成功するとこんな感じに表記されるはず

<img width="304" alt="2017-07-20 19 43 01" src="https://user-images.githubusercontent.com/10849664/28413849-0d4f7dfa-6d84-11e7-90c3-62a92cb6e1a1.png">



## 3.4 DIコンテナのインスタンスを利用しているメソッドのテスト

userServiceのfindAllメソッドは、userMapperのインスタンスをDIコンテナから利用している。

#### 3.4.1 ダメな例

以下のuserServiceクラスは、独自でインスタンス生成したため、DIコンテナのインスタンスを利用できずエラーとなる。

```
@Test
    public void メソッド単体テストDIあり失敗() throws Exception {
    UserService userService = new UserService();
    List<User> users = userService.findAll();
    assertThat(users.size()).isEqualTo(0);
}
```

実行してみた

<img width="293" alt="2017-07-20 19 57 50" src="https://user-images.githubusercontent.com/10849664/28414520-f8509a8a-6d86-11e7-8e80-d6db4b74a1ee.png">

#### 3.4.2 良い例

`@MockBean` 指定で、Mockクラスを利用してDI解決をしたクラスを生成する。

Mockクラスのメソッドはデフォルトで戻り値に合わせた処理になる。 (詳しくは公式リファレンスで)

```
@MockBean
private UserService mockUserService;

@Test
public void メソッド単体テストMockBean() throws Exception {
    List<User> users = this.mockUserService.findAll(); // 空のリストが返るはず
    assertThat(users.size()).isEqualTo(0);
}
```

実行してみた

<img width="292" alt="2017-07-20 20 10 46" src="https://user-images.githubusercontent.com/10849664/28414634-8e8ddbca-6d87-11e7-8bd8-83c85dbb7f15.png">

> `@MockBean` は、テスト用モッククラス生成ライブラリのMockitoを利用している。
> 今回の場合は、Mockitoをそのまま使った時の以下の意味と同じである。
> ```
> @InjectMocks
> UserService mockUserService; // DIされて利用するオブジェクト
> @Mock
> UserMapper userMapper; // DIされるMockオブジェクト
> ``` 
