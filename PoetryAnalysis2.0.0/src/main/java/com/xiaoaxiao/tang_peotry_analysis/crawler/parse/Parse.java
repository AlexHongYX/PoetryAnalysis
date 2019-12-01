package com.xiaoaxiao.tang_peotry_analysis.crawler.parse;

import com.xiaoaxiao.tang_peotry_analysis.crawler.common.Page;

/**
 * Created by xiaoaxiao on 2019/11/24
 * Description:
 */
public interface Parse {

    /**
     * 解析页面
     * @param page 自定义page
     */
    void parse(final Page page);
    // 将Page设置为final，防止在方法中修改了原有Page
}
