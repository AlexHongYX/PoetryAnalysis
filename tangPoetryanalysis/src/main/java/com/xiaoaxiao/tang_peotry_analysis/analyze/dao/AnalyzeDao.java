package com.xiaoaxiao.tang_peotry_analysis.analyze.dao;

import com.xiaoaxiao.tang_peotry_analysis.analyze.entity.PoetryInfo;
import com.xiaoaxiao.tang_peotry_analysis.analyze.model.AuthorCount;

import java.util.List;

/**
 * Created by xiaoaxiao on 2019/11/26
 * Description: 接口定义对数据库进行操作的方法
 */
public interface AnalyzeDao {

    /**
     * 分析唐诗中作者的创作数量
     * @return
     */
    List<AuthorCount> analyzeAuthorCount();

    /**
     * 查询所有的诗文，提供给业务层进行分析
     * @return
     */
    List<PoetryInfo> queryAllPoetryInfo();
}
