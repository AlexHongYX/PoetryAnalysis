package com.xiaoaxiao.tang_peotry_analysis.analyze.service;

import com.xiaoaxiao.tang_peotry_analysis.analyze.model.AuthorCount;
import com.xiaoaxiao.tang_peotry_analysis.analyze.model.WordCount;

import java.util.List;

/**
 * Created by xiaoaxiao on 2019/11/26
 * Description: 唐诗分析业务层
 */
public interface AnalyzeService {
    /**
     * 分析唐诗中作者的创作数量
     * @return
     */
    List<AuthorCount> analyzeAuthorCount();

    /**
     * 词云分析
     * @return
     */
    List<WordCount> analyzeWordCloud();
}
