package com.xiaoaxiao.tang_peotry_analysis.crawler.pipeline;

import com.xiaoaxiao.tang_peotry_analysis.crawler.common.Page;

import java.util.Map;

/**
 * Created by xiaoaxiao on 2019/11/25
 * Description: 将详情页的信息输出在控制台的管道
 */
public class ConsolePipeline implements Pipeline {
    @Override
    public void pipeline(final Page page) {
        Map<String,Object> data = page.getDataSet().getDatas();
        // 输出到控制台
        System.out.println(data);
    }
}
