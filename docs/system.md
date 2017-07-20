# 2.アプリケーション仕様

## Databaseについて
テスト用にH2という高速なデータベースを利用する。

#### H2の利用設定
`src/main/resources/application.properties` に設定を記述する。

```
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.url=jdbc:h2:./.data/appdb
spring.datasource.username=root
spring.datasource.password=
```

`.data/appdb` にDBファイルを生成する。

#### テーブルの生成
`src/main/resources/` 内に、`schema.sql` というファイルを作ると起動時に読み込まれる。

```sql
CREATE TABLE IF NOT EXISTS users (
    id INTEGER PRIMARY KEY AUTO_INCREMENT, /** ユニークなID */
    name VARCHAR(20), /** user名 */
    group_id INTEGER NOT NULL DEFAULT 0 /** userのグループ番号 */
); 
```


## クラス説明
`src/main/java/com/exmaple/springboottestdemo`に属するクラスの説明。

#### SpringBootTestDemoApplication.java
SpringBoootアプリケーションのメインクラス。

#### User.java
usersテーブルの1レコード分の構造体。

#### UserController.java
 - トップページにGETでアクセスがきたら -> ユーザー登録フォーム&ユーザー一覧を出す。
 - トップページにPOSTでアクセスがきたら -> ユーザー登録処理して、GETでリダイレクト。

#### UserService.java
user系処理のメインクラス、UserControllerに呼ばれて利用される。

#### UserMapper.java
usersテーブルへのアクセスと、User構造体へのマッピング。UserServiceに利用される。
