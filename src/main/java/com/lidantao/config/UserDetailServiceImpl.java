package com.lidantao.config;

import com.lidantao.entity.LoginUser;
import com.lidantao.entity.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author Cola
 * @Date 2023年05月04日 17:13:00
 */
@Configuration
public class UserDetailServiceImpl implements UserDetailsService {

    // 替换成数据库查询即可
    private static HashMap<String, LoginUser> userCache = new HashMap<>();
    static { // {noop}表示不加密，不然会报错（因为默认需要指定密码加密方法）
        //userCache.put("zs", new LoginUser(new User("1", "zs", "{noop}zs")));
        //userCache.put("ls", new LoginUser(new User("2", "ls", "{noop}ls")));
        userCache.put("zs", new LoginUser(new User("1", "zs", "$2a$10$E4r2s/Gm0sTmjx/g41mFVOMYwAq/wG.US0J./77KTOD4wxsiLcera")));
        userCache.put("ls", new LoginUser(new User("2", "ls", "$2a$10$WyDvGDvtuSFk/VRt5R3pGu94YRcYBeutWNJCt6AHVhx0XlaTZdLGu")));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if(!userCache.containsKey(username)) throw new RuntimeException("用户名或密码错误！！！");
        else return userCache.get(username);

    }

}
