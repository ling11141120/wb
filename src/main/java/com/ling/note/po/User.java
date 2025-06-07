package com.ling.note.po;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * &#064;Author:  LingWeiBo
 * &#064;Date:  2025/6/7 08:58
 */
@Getter
@Setter
@AllArgsConstructor
public class User {

    private Integer userId;
    private String uname;
    private String upwd;
    private String nick;
    private String head;
    private String mood;

    public User() {
    }
}
