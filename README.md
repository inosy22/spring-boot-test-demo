# spring-boot-test-demo

SpringBootで作成されたwebアプリケーションに対してのテストのサンプル

 - [SpringBootTest入門](https://www.slideshare.net/techblogyahoo/springboottest)について、実際に実装して試してみる。
 - 45分程度のハンズオン向け資料で、UnitTestを実行できること(3章まで)をゴールとする。
 - 4,5章は余裕がある人の発展的内容。

> ### [0.セットアップ](docs/setup.md)
>  - このプロジェクトセットアップした際のメモなど、見る必要なし

### [1. アプリケーションの準備](docs/ready.md)
 - アプリケーションをローカルで立ち上げるまで

### [2. アプリケーション仕様](docs/system.md)
 - アプリケーションや環境などの説明
 
### [3. 単体テスト](docs/test_unit.md)
 - Serviceクラスや一般的なクラスメソッドの単体テスト
 
### [4. DB処理テスト](docs/test_db.md) (余裕があれば挑戦)
 - マッパー・リポジトリクラスなどのメソッドのテスト
 
### [5. E2E(EndToEnd)テスト](docs/test_e2e.md) (余裕があれば挑戦)
 - コントローラーとHTMLのためのE2E(EndToEnd)テスト


[answerブランチ](https://github.com/inosy22/spring-boot-test-demo/tree/answer/src/test/java/com/example/springboottestdemo)にテストコード記述例があります。
