package com.example.springboottestdemo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    
    // User生成form&一覧表示
    @GetMapping("/")
    public String showUserList(Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("userForm", new UserForm());
        return "index";
    }
    
    // User生成
    @PostMapping("/")
    public String addUser(Model model, @Validated UserForm userForm, BindingResult bindingResult) {
        // バリデーションエラーがなければ登録
        if (!bindingResult.hasErrors()) {
            userService.insert(userForm.getName());
        }
        return "redirect:/";
    }
    
    // User生成フォームクラス
    static class UserForm {
        // 1文字以上20文字以下の英数字
        @NotNull
        @Size(min = 1, max = 20)
        @Pattern(regexp = "[a-zA-Z0-9]*")
        private String name;
        
        public void setName(String name) {
            this.name = name;
        }
        
        public String getName() {
            return this.name;
        }
    }
}
