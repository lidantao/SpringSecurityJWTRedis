package com.lidantao.controller;

import com.lidantao.entity.User;
import com.lidantao.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Cola
 * @Date 2023年05月05日 12:01:00
 */
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping("/user/login")
    public Map<String, String> login(User user, HttpServletRequest request){
        return loginService.login(user, request);
    }
}
