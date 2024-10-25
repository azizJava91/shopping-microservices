package com.ms.myShop.controller.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserControllerUI {

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }
}
