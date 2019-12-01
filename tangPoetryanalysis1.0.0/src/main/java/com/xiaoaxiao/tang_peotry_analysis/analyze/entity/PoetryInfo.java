package com.xiaoaxiao.tang_peotry_analysis.analyze.entity;

import lombok.Data;

/**
 * Created by xiaoaxiao on 2019/11/25
 * Description: 与数据库中poetry表对应的类
 */
@Data
public class PoetryInfo {

    /**
     * 标题
     */
    private String title;
    /**
     * 朝代
     */
    private String dynasty;
    /**
     * 作者
     */
    private String author;
    /**
     * 正文
     */
    private String content;
}
