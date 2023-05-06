package com.lidantao.service.impl;

import com.lidantao.config.UserDetailServiceImpl;
import com.lidantao.entity.LoginUser;
import com.lidantao.service.LogoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Cola
 * @Date 2023年05月06日 10:44:00
 */
@Service
public class LogoutServiceImpl implements LogoutService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Map logout() {

        // 删除 SecurityContextHolder 的用户信息
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        SecurityContextHolder.clearContext();

        // 根据 userId 清空 Redis 登录信息
        String username = (String) auth.getPrincipal();

        /**
         * 后面换成数据库
         */
        String userId = UserDetailServiceImpl.userCache.get(username).getUser().getId();
        redisTemplate.delete(userId);

        HashMap<String, String> result = new HashMap<>();
        result.put("退出状态", "退出成功！");

        return result;
    }


}
