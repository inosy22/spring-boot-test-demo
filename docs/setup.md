# 0.セットアップ

ユニットテストを試すためのアプリケーションをセットアップしたメモ

**※本リポジトリをcloneしていればやる必要ない手順です**

## 使用するライブラリ群
SpringBootで以下のライブラリなどを利用して作成する。
<table>
<tr><th>依存関係解決ツール</th><td>maven</td></tr>
<tr><th>アプリケーション</th><td>webアプリケーション</td></tr>
<tr><th>HTMLテンプレート</th><td>Thymeleaf</td></tr>
<tr><th>データベース</th><td>H2</td></tr>
<tr><th>ORマッパー</th><td>Mybatis</td></tr>
</table>

## spring-boot-starter-testについて
デフォルトで入っているspring-boot-starter-testには以下のパッケージが内包されている。
<table>
<tr><th>JUnit</th><td>デファクトスタンダードのJavaテストフレームワーク</td></tr>
<tr><th>SpringTest<br>SpringBootTest</th><td>SpringまたはSpringBootで作られたアプリケーション用のテストライブラリ</td></tr>
<tr><th>AssetJ</th><td>Assertionライブラリ(Assertメソッドをより直感的に記述できるので今回はこの記法を使います)</td></tr>
<tr><th>Hamcrest</th><td>matcherオブジェクトライブラリ</td></tr>
<tr><th>Mockito</th><td>Javaのモックフレームワーク</td></tr>
<tr><th>JSONassert</th><td>Json用Assertionライブラリ</td></tr>
<tr><th>JSONPath</th><td>Json用XPathライブラリ</td></tr>
</table>

## Spring Initializrでプロジェクトを作成した
Spring Initializr https://start.spring.io  
<img width="1209" alt="2017-07-18 1 09 26" src="https://user-images.githubusercontent.com/10849664/28406828-f0095660-6d6c-11e7-8ced-b9fad699961b.png">

GenerateProjectしてダウンロードする。

プロジェクトのディレクトリに入る
```bash
$ cd /path/to/project
```

ダウンロードしたプロジェクトのpom.xmlのdevendenciesに今回利用するHtmlUnitを追加する。(E2Eテスト用)
```bash
$ vim pom.xml
```

```diff
<dependencies>
   ...
   ...
+  <dependency>
+    <groupId>net.sourceforge.htmlunit</groupId>
+    <artifactId>htmlunit</artifactId>
+    <scope>test</scope>
+  </dependency>
 </dependencies>
```

mavenでインストールすれば実行可能
```bash
$ ./mvnw install
```
