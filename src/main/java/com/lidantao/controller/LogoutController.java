package com.lidantao.controller;

import com.lidantao.service.LoginService;
import com.lidantao.service.LogoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Cola
 * @Date 2023年05月06日 10:43:00
 */
@RestController
@RequestMapping("/user")
public class LogoutController {

    @Autowired
    private LogoutService logoutService;

    @RequestMapping("/logout")
    public Map<String, String> logout(){
        return logoutService.logout();
    }


}
