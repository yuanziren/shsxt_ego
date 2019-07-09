package com.shsxt.ego.cart.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CartController {

    @RequestMapping("cart/test")
    public String test(){
        System.out.println("购物车测试页面...");
        return "test";
    }
}
