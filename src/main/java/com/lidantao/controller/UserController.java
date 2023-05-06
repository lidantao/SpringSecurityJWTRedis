package com.lidantao.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Cola
 * @Date 2023年05月04日 17:11:00
 */
@RestController
public class UserController {

    @RequestMapping("/")
    @PreAuthorize("hasAnyAuthority('user')")
    public String index(){
        return "index!";
    }


    @RequestMapping("/admin")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String admin(){
        return "admin!";
    }

    @RequestMapping("/user")
    @PreAuthorize("hasAnyAuthority('user')")
    public String user(){
        return "user!";
    }

}
