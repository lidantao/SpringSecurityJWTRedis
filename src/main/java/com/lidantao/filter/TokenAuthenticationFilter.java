package com.lidantao.filter;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lidantao.entity.LoginUser;
import com.lidantao.service.impl.LoginServiceImpl;
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
        DecodedJWT decodedJWT = JWTUtils.parseJWT(token);
        String userId = decodedJWT.getClaim("userId").asString();
        String ipAddress = decodedJWT.getClaim("ipAddress").asString();
        String currIpAddress = LoginServiceImpl.getIpAddress(request);


        // 校验当前持有token的用户ip地址是否等于当初认证的ip地址
        if(!currIpAddress.equals(ipAddress)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Redis 查找是否存在该用户
        LoginUser loginUser = (LoginUser) redisTemplate.opsForValue().get(userId);
        if(loginUser == null) {
            filterChain.doFilter(request, response);
            return;
        }


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
