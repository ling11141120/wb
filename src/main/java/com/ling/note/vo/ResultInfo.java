package com.ling.note.vo;

/**
 * &#064;Author:  LingWeiBo
 * &#064;Date:  2025/6/7 09:09
 */

import lombok.Getter;
import lombok.Setter;

/**
 * */
@Getter
@Setter
public class ResultInfo<T>{

    private Integer  code;
    private String  msg;
    private T result;
}
