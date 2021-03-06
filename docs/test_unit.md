# 3.単体テスト

UserServiceクラスのメソッドについて、単体テストを行う。
準備してある空の以下のクラスを開く 。(importと空のクラスだけ準備してあります)

`src/test/java/com/example/springboottestdemo/UserServiceTests.java`

<img width="302" alt="2017-07-25 22 02 44" src="https://user-images.githubusercontent.com/10849664/28573271-1f2ff4fa-7185-11e7-91dc-e81639c0ea5e.png">

```java
public class UserServiceTests {
    
}
```


## 3.1 テスト用アノテーションを追加する

SpringBootでテストを行うために必要なアノテーションを追加

```java
// ここから
@RunWith(SpringRunner.class)
@SpringBootTest
// ここまでを追加
public class UserServiceTests {

}
```


## 3.2 簡単なテストを実装する

クラス内に簡単なテストメソッドを実装してチェックする。

```java
@Test
public void テストが動くことをテスト() throws Exception {
    Integer actual = 1 + 1; // 実際の計算値
    Integer expected = 2; // 計算した期待値
    assertThat(actual).isEqualTo(expected); // 実際の計算値と期待値があってるかチェック
}
```


## 3.3 JUnitTestの実行

プロジェクトを右クリック -> 「Run As」->「JUnit Test」でテスト実行

<img width="500" alt="2017-07-20 19 44 07" src="https://user-images.githubusercontent.com/10849664/28413840-0495f16c-6d84-11e7-9f96-14a3e826c79f.png">

実行して成功するとこんな感じに表記されるはず

<img width="407" alt="2017-07-25 22 28 57" src="https://user-images.githubusercontent.com/10849664/28574368-bb4e7fde-7188-11e7-9751-a8df6f40f691.png">


## 3.4 グループIDを生成するメソッドのテストを作ってみる

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

実行して成功するとこんな感じに表記されるはず

<img width="304" alt="2017-07-20 19 43 01" src="https://user-images.githubusercontent.com/10849664/28413849-0d4f7dfa-6d84-11e7-90c3-62a92cb6e1a1.png">


余裕がある人は以下も試してみてください
 - わざとテストに失敗してみる
 - 奇数文字数のグループID生成テスト


## 3.5 DIコンテナのインスタンスを利用しているメソッドのテスト

> DIコンテナとは、DIコンテナとは、アプリケーションにDI(Dependency Injection: 依存性注入)機能を提供する機能。
> 
> `@Autowired` というアノテーションでプロパティを宣言すると、SpringBootのDIコンテナ機能に身を任せてインスタンス生成することで自動でDIされる。
> 
> しかし、手動でnew宣言して生成したものは自動でDIされない。

今回、userServiceのfindAllメソッドは、userMapperのインスタンスをDIコンテナから利用している。

### 3.5.1 ダメな例

以下のuserServiceクラスは、独自でインスタンス生成したため、DIコンテナのインスタンスを利用できずエラーとなる。

```java
@Test
public void メソッド単体テストDIあり失敗() throws Exception {
    UserService userService = new UserService();
    List<User> users = userService.findAll();
    assertThat(users.size()).isEqualTo(0);
}
```

実行してみた

<img width="293" alt="2017-07-20 19 57 50" src="https://user-images.githubusercontent.com/10849664/28414520-f8509a8a-6d86-11e7-8e80-d6db4b74a1ee.png">


### 3.5.2 良い例

#### 3.5.2-1 インスタンスのMock化

`@Autowired` でDIコンテナを利用して、UserServiceクラスを生成する。

`@MockBean` 指定で、DIコンテナの指定のクラスをMock化する。

Mockクラスのメソッドはデフォルトで戻り値に合わせた空のような処理になる。 (詳しくは公式リファレンスで)

今回の `List<User>` は空のList(0件)が返って来るような処理になる。

(Mock化しないと、実際のDB接続処理が入ってしまうため面倒なことになる)

```java
@Autowired
UserService userService;

@MockBean
UserMapper userMapper; // UserMapperをMockにする

@Test
public void メソッド単体テストMockBean() throws Exception {
    List<User> users = this.userService.findAll(); // 空のリストが返るはず
    assertThat(users.size()).isEqualTo(0);
}
```

実行してみた

<img width="292" alt="2017-07-20 20 10 46" src="https://user-images.githubusercontent.com/10849664/28414634-8e8ddbca-6d87-11e7-8bd8-83c85dbb7f15.png">

> `@MockBean` は、SpringBootの機能で利用できるアノテーションですが、テスト用モッククラス生成ライブラリのMockitoを利用している。
> 今回の場合は、Mockitoをそのまま使った時の以下の意味と同じである。
> 
> ```java
> @InjectMocks
> UserService userService; // DIされて利用するオブジェクト
> @Mock
> UserMapper userMapper; // DIされるMockオブジェクト
> ``` 
> 
> SpringBootだと簡単にモッククラスの宣言ができるようになってることがわかる。


#### 3.5.2-2 Mock化されたインスタンスメソッドの振る舞いを変更

次にMock化されたインスタンスのメソッドの振る舞いを偽装してみる。

`when(偽装したいインスタンスメソッド)`と`thenReturn(その戻り値)`でメソッドの振る舞いを変更することができる。

```java
// when(モックのメソッド).thenReturn(戻り値)でモックオブジェクトの振る舞いを追加する 
@Test
public void モックインスタンスメソッドの偽装() throws Exception {
    // 戻り値のデータを作成
    List<User> mockUsers = new ArrayList<User>();
    mockUsers.add(new User(1,"aaa",1));
    mockUsers.add(new User(2,"bbbb",2));
    mockUsers.add(new User(3,"ccccc",1));

    // userMapperのfindAllメソッドが呼ばれたら、mockUsersを返すようにする
    when(this.userMapper.findAll()).thenReturn(mockUsers);
    
    // 偽装されたメソッドが呼ばれているかテスト
    List<User> users = this.userService.findAll();
    assertThat(users.size()).isEqualTo(mockUsers.size());
}
```

実行して見た

<img width="288" alt="2017-12-11 12 37 17" src="https://user-images.githubusercontent.com/10849664/33814799-21037f9c-de70-11e7-8c31-e99cc78a96d6.png">

`@Mock`によるインスタンスのモック化のシンプルな例を紹介したが、  
メソッドの引数によって振る舞いを変更したり、`@Spy`による部分Mock化などMockitoには便利な機能が用意されている。  
詳細な使い方はコチラ → [Mockitoチュートリアル](http://www.w3mc.com/ja/mockito/default.html)
