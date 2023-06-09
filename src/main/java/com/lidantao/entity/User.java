package com.lidantao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Cola
 * @Date 2023年05月04日 17:17:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String id;

    private String username;

    private String password;

    private String ipAddress;

    public User(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

}
