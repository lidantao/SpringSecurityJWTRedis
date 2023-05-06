package com.lidantao.config;

import com.lidantao.entity.LoginUser;
import com.lidantao.entity.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author Cola
 * @Date 2023年05月04日 17:13:00
 */
@Configuration
public class UserDetailServiceImpl implements UserDetailsService {


    // TODO ： DB

    // 替换成数据库查询即可
    public static HashMap<String, LoginUser> userCache = new HashMap<>();
    static { // {noop}表示不加密，不然会报错（因为默认需要指定密码加密方法）
        //userCache.put("zs", new LoginUser(new User("1", "zs", "{noop}zs")));
        //userCache.put("ls", new LoginUser(new User("2", "ls", "{noop}ls")));

        // TODO ：后面改成 DB
        HashSet<String> admin = new HashSet<>();
        HashSet<String> user = new HashSet<>();
        admin.add("admin");
        admin.add("user");
        user.add("user");

        userCache.put("admin", new LoginUser(new User("1", "admin", "$2a$10$CTR9tTCjzITWgkAJ3Ll8weGy36Kvw8QZqp7mZmUlFHH4CLJo4cPIK"), admin));
        userCache.put("user", new LoginUser(new User("2", "user", "$2a$10$ZZabm1j29eUR7YkK/9FoX.lS4q.M9Qk6xijb94oFcB4/tSanCeE2e"), user));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if(!userCache.containsKey(username)) throw new RuntimeException("用户名或密码错误！！！");
        else return userCache.get(username);

    }

}
