package com.lidantao.service.impl;

import com.lidantao.config.RedisConfig;
import com.lidantao.config.UserDetailServiceImpl;
import com.lidantao.entity.LoginUser;
import com.lidantao.entity.User;
import com.lidantao.service.LoginService;
import com.lidantao.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Cola
 * @Date 2023年05月05日 12:04:00
 */
@Service
public class LoginServiceImpl implements LoginService  {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Map<String, String> login(User user, HttpServletRequest request) {


        LoginUser loginUser = UserDetailServiceImpl.userCache.get(user.getUsername());

        // 客户端IP地址
        String ipAddress = getIpAddress(request);
        user.setIpAddress(ipAddress);
        user.setId(loginUser.getUser().getId());


        // 调用 AuthenticationManager authenticate进行认证，认证通过后会存入到 SecurityContextHolder
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );

        // 认证失败
        if(authenticate == null) throw new RuntimeException("用户名或者密码错误");

        // 认证成功生成 token
        String token = JWTUtils.createJWT(user);

        // 将用户信息存入 redis
        // TODO 更换成DB
        redisTemplate.opsForValue().set(loginUser.getUser().getId(), loginUser);

        HashMap<String, String> map = new HashMap<>();
        map.put("登录状态", "成功");
        map.put("token", token);


        return map;
    }


    /**
     * 获取 -- 客户端IP地址
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
