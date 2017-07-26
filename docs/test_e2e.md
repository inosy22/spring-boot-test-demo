# 5.E2Eテスト

UserControllerとその結果取得できるHTMLに対して、E2E (EndToEnd) テストを行う。
準備してある空のファイルの以下のクラスを開く

`src/test/java/com/example/springboottestdemo/UserControllerTests.java`

```java
public class UserControllerTests {
    
}
```

E2Eテストを行うにあたって、HtmlUnitというライブラリを利用する。

Htmlベースでテストを行うことができるライブラリである。

## 5.1 テスト用のアノテーションを追加する

E2Eテストで利用するwebサーバーのポート番号の競合が発生しないように空いているランダムなポート番号を指定する。

```java
// ここから
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // 空いてるポートをランダムに指定
@ActiveProfiles("test")
@Sql("classpath:/delete-test-data.sql") // テーブルのデータを事前削除するスクリプト
// ここまでコピペして追加
public class UserControllerTests {

}
```

## 5.2 共通処理系を実装

```java
public class UserControllerTests {
 /* ここから */
    // webサーバーのポートを取得して、プロパティにセット
    @LocalServerPort 
    int port;
    
    // HtmlUnitのWebClientクラス
    WebClient webClient;
    
    @Before // テストケース実行前に実行される
    public void init() throws Exception {
        webClient = new WebClient();
    }
    
    @After // テストケース実行後に実行される
    public void close() throws Exception {
        webClient.close();
    }
    
    // domain,path,portを混合したURLを生成
    private String getUrl(String path) {
        return "http://localhost:" + port + path;
    }
    
    // pathを元に、HtmlPageクラスを取得する
    private HtmlPage getHtmlPage(String path) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
        return webClient.getPage(this.getUrl(path));
    }
/* ここまでコピペして追加 */
}
```


## 5.3 ユーザー登録が成功した時のテスト

以下のメソッドを追加

```java
@Test
@Sql("classpath:/delete-test-data.sql") // テーブルのデータを事前削除するスクリプト
public void ユーザー登録成功() throws Exception {
    // ユーザー登録ページを取得
    HtmlPage top = this.getHtmlPage("/");
    
    // user-list-areaが表示されていないことを確認
    DomElement domElem = top.getElementById("user-list-area");
    assertThat(domElem).isEqualTo(null);
    
    // formを取得して、テストで値をセット
    HtmlForm form = top.getFormByName("user-form");
    form.getInputByName("name").setAttribute("value", "test");
    
    // formのsubmitボタンをクリックしてページ遷移
    HtmlPage result = form.getInputByName("submit").click();
    
    // submit後に登録されてuser-listが表示されていることを確認
    domElem = result.getElementById("user-list-area");
    assertThat(domElem).isNotEqualTo(null);
    
    // エラーが出ていないことを確認
    try {
        result.getElementByName("name-error");
        fail();
    } catch (ElementNotFoundException e) {
        // OK
    }
}
```


## 5.4 ユーザー登録がバリデーションで失敗した時のテスト

以下のメソッドを追加

```java
@Test
@Sql("classpath:/delete-test-data.sql") // テーブルのデータを事前削除するスクリプト
public void ユーザー登録失敗() throws Exception {
    // ユーザー登録ページを取得
    HtmlPage top = this.getHtmlPage("/");
    
    // formを取得して、テストで値をセット
    HtmlForm form = top.getFormByName("user-form");
    form.getInputByName("name").setAttribute("value", "あああ");
    
    // formのsubmitボタンをクリックしてページ遷移
    HtmlPage result = form.getInputByName("submit").click();
    
    // user-list-areaが取得できること
    DomElement domElem = result.getElementById("user-list-area");
    assertThat(domElem).isEqualTo(null);
    
    // 名前のエラーが表示されること (なかったら例外吐く)
    HtmlElement htmlElem = result.getElementByName("name-error");
    
    // 名前のエラー文言が正しいこと
    assertThat(htmlElem.getTextContent()).isEqualTo("半角英数字のみで入力してください");
}
```
