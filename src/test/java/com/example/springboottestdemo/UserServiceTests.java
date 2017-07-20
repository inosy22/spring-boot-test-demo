package com.example.springboottestdemo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTests {
    
    @Test
    public void メソッド単体テストDIなし成功() throws Exception {
        UserService userService = new UserService();
        String name = "testname";
        Integer groupId = 2;
        assertThat(userService.getGroupFromName(name)).isEqualTo(groupId);
    }
    
    // @Test
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
    
    // SpringBootなら簡単に書ける
    @MockBean
    private UserService mockUserService;
    
    @Test
    public void メソッド単体テストMockBean() throws Exception {
        List<User> users = this.mockUserService.findAll();
        assertThat(users.size()).isEqualTo(0);
    }
}
