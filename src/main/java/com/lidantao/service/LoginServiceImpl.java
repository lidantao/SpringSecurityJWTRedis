package com.lidantao.service;

import com.lidantao.config.RedisConfig;
import com.lidantao.entity.LoginUser;
import com.lidantao.entity.User;
import com.lidantao.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Cola
 * @Date 2023年05月05日 12:04:00
 */
@Service
public class LoginServiceImpl implements LoginService{

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Map<String, String> login(User user) {

        // 调用 AuthenticationManager authenticate进行认证
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        // 认证失败
        if(authenticate == null) throw new RuntimeException("用户名或者密码错误");

        // 认证成功生成 token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String token = JWTUtils.createJWT(loginUser.getUser().getId());

        // 将用户信息存入 redis
        redisTemplate.opsForValue().set(loginUser.getUser().getId(), loginUser);

        HashMap<String, String> map = new HashMap<>();
        map.put("登录状态", "成功");
        map.put("token", token);

        return map;
    }

}
