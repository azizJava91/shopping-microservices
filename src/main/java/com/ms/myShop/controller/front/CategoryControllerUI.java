package com.ms.myShop.controller.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/category")
public class CategoryControllerUI {

    @GetMapping("/showList")
    public String showCategories(){
        return "categoryList";
    }
@GetMapping("/error")
public String test(){
        return "error";
}
}
