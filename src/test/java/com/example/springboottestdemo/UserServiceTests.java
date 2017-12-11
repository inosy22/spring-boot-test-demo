package com.example.springboottestdemo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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
    // UserService userService; // DIされて利用するMackオブジェクト
    // @Mock
    // UserMapper userMapper; // DIされるMockオブジェクト
    
    // SpringBootでの書き方
    @Autowired
    UserService userService;
    
    @MockBean
    UserMapper userMapper;
    
    @Test
    public void メソッド単体テストMockBean() throws Exception {
        List<User> users = this.userService.findAll();
        assertThat(users.size()).isEqualTo(0);
    }
    
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
}
