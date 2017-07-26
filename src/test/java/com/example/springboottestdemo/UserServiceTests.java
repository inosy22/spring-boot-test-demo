package com.example.springboottestdemo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTests {
    @Test
    public void テストが動くことをテスト() throws Exception {
        Integer actual = 1 + 1; // 実際の計算値
        Integer expected = 2; // 計算した期待値
        assertThat(actual).isEqualTo(expected); // 実際の計算値と期待値があってるかチェック
    }
    
    @Test
    public void 偶数文字のグループID生成テスト() throws Exception {
        UserService userService = new UserService();
        String name = "testname";
        Integer groupId = 2;
        assertThat(userService.getGroupFromName(name)).isEqualTo(groupId);
    }
    
    //@Test
    public void メソッド単体テストDIあり失敗() throws Exception {
        UserService userService = new UserService();
        List<User> users = userService.findAll();
        assertThat(users.size()).isEqualTo(0);
    }
    
    // Mocitoを使って素直に書くと
    // @InjectMocks
    // UserService mockUserService; // DIされて利用するMackオブジェクト
    // @Mock
    // UserMapper userMapper; // DIされるMockオブジェクト
    
    // SpringBootでの書き方
    @Autowired
    UserService mockUserService;
    
    @MockBean
    UserMapper userMapper;
    
    @Test
    public void メソッド単体テストMockBean() throws Exception {
        List<User> users = this.mockUserService.findAll();
        assertThat(users.size()).isEqualTo(0);
    }
}
