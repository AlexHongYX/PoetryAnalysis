package com.xiaoaxiao.tang_peotry_analysis.crawler.pipeline;

import com.xiaoaxiao.tang_peotry_analysis.crawler.common.Page;

/**
 * Created by xiaoaxiao on 2019/11/24
 * Description: 管道处理Page中的数据
 */
public interface Pipeline {

    void pipeline(final Page page);
}
