package com.kang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Kangshitao
 * @date 2021年8月7日 下午8:28
 */
@Controller
public class BashController {
    @GetMapping("/ms")
    public String miaosha(){
        return "index";
    }
}
