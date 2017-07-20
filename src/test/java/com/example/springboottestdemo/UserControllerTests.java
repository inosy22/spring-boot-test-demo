package com.example.springboottestdemo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("classpath:/delete-test-data.sql") // テーブルのデータを事前削除するスクリプト
@ActiveProfiles("test")
public class UserControllerTests {
    @LocalServerPort
    int port;
    WebClient webClient;
    
    @Before
    public void init() throws Exception {
        webClient = new WebClient();
    }
    
    @After
    public void close() throws Exception {
        webClient.close();
    }
    
    @Test
    public void ユーザー登録成功() throws Exception {
        // ユーザー登録ページを取得
        String url = this.getUrl("/");
        HtmlPage top = webClient.getPage(url);
        
        // user-list-areaが表示されていないことを確認
        DomElement domElem = top.getElementById("user-list-area");
        assertThat(domElem).isEqualTo(null);
        
        // formを取得して、テストで値をセット
        HtmlForm form = top.getFormByName("user-form");
        form.getInputByName("name").setAttribute("value", "test");
        
        // formのsubmitボタンをクリックしてページ遷移
        HtmlPage result = form.getInputByName("submit").click();
        
        // submit後に登録されてuser-listが表示されていることを確認
        result.getElementById("user-list-area");
        
        // エラーが出ていないことを確認
        try {
            result.getElementByName("name-error");
            fail();
        } catch (ElementNotFoundException e) {
            // OK
        }
    }
    
    @Test
    public void ユーザー登録失敗() throws Exception {
        // ユーザー登録ページを取得
        String url = this.getUrl("/");
        HtmlPage top = webClient.getPage(url);
        
        // formを取得して、テストで値をセット
        HtmlForm form = top.getFormByName("user-form");
        form.getInputByName("name").setAttribute("value", "あああ");
        
        // formのsubmitボタンをクリックしてページ遷移
        HtmlPage result = form.getInputByName("submit").click();
        
        // user-list-areaが取得できること
        result.getElementById("user-list-area");
        
        // 名前のエラーが表示されること
        HtmlElement elem = result.getElementByName("name-error");
        
        // 名前のエラー文言が正しいこと
        assertThat(elem.getTextContent()).isEqualTo("半角英数字のみで入力してください");
    }
    
    private String getUrl(String path) {
        return "http://localhost:" + port + path;
    }
}
