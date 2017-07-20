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
    private UserService userService; // UserServiceをDIコンテナから利用
    
    // User生成form&一覧表示
    @GetMapping("/")
    public String showUserList(Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("userForm", new UserForm());
        return "user";
    }
    
    // User生成
    @PostMapping("/")
    public String addUser(Model model, @Validated UserForm userForm, BindingResult bindingResult) {
        // バリデーションエラーがあったとき
        if (bindingResult.hasErrors()) {
            model.addAttribute("users", userService.findAll());
            model.addAttribute("userForm", userForm);
            return "user";
        }
        // 問題がなければ登録してリダイレクト
        userService.addUser(userForm.getName());
        return "redirect:/";
    }
    
    // User生成フォームクラス
    static class UserForm {
        // 1文字以上20文字以下の英数字
        @NotNull
        @Size(min = 1, max = 20, message = "1文字以上,20文字以下で入力してください")
        @Pattern(regexp = "[a-zA-Z0-9]*", message = "半角英数字のみで入力してください")
        private String name;
        
        public void setName(String name) {
            this.name = name;
        }
        
        public String getName() {
            return this.name;
        }
    }
}
