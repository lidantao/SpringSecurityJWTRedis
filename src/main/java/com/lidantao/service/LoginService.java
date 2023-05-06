package com.lidantao.service;

import com.lidantao.entity.User;

import java.util.Map;

public interface LoginService {

    public Map<String, String> login(User user);

}
