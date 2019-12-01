package com.xiaoaxiao.tang_peotry_analysis.analyze.model;

import lombok.Data;

/**
 * Created by xiaoaxiao on 2019/11/26
 * Description: 每个词出现的次数
 */
@Data
public class WordCount {
    /**
     * 单词的名字
     */
    private String word;
    /**
     * 单词的数量
     */
    private Integer count;
}
