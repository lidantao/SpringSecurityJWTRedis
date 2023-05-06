package com.lidantao.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Cola
 * @Date 2023年05月04日 17:11:00
 */
@RestController
public class UserController {

    @RequestMapping("/")
    public String index(){
        return "index!";
    }

}
