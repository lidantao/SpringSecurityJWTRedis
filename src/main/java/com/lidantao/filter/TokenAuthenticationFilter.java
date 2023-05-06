package com.lidantao.filter;

import com.auth0.jwt.interfaces.Claim;
import com.lidantao.entity.LoginUser;
import com.lidantao.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * @author Cola
 * @Date 2023年05月05日 12:33:00
 */
@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取 token
        String token = request.getHeader("token");

        // 没有 token，直接放行给其他过滤器（后面会验证）
        if(token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // 验证 token
        Claim claim = JWTUtils.parseJWT(token);
        String userId = claim.asString();


        // Redis 查找用户信息
        LoginUser loginUser = (LoginUser) redisTemplate.opsForValue().get(userId);

        // TODO 后面改成 DB
        // 存入 SecurityContextHolder，后面授权过滤器会去这里拿用户授权信息
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(),
                        loginUser.getPassword(),
                        loginUser.getAuthorities()
                ));

        // 验证通过放行
        filterChain.doFilter(request, response);

    }

}
