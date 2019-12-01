package com.xiaoaxiao.tang_peotry_analysis.analyze.model;

import lombok.Data;

/**
 * Created by xiaoaxiao on 2019/11/26
 * Description: 作者及其对应的古诗文数量
 */
@Data
public class AuthorCount {

    /**
     * 作者
     */
    private String author;
    /**
     * 对应诗文数量
     */
    private Integer count;
}
