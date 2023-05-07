package com.lidantao.service;

import com.lidantao.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface LoginService {

    public Map<String, String> login(User user, HttpServletRequest request);

}
