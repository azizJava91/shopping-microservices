package com.ms.myShop.controller.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class ProductControllerUI {

    @GetMapping("/create")
    public String showProductForm() {
        return "productCreate";
    }

    @GetMapping("/update")
    public String showUpdateForm(){
        return"productUpdate";
    }
    @GetMapping("/page")
    public String showPage(){
        return "productPage";
    }

}
