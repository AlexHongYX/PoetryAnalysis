package com.xiaoaxiao.tang_peotry_analysis.crawler.common;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by xiaoaxiao on 2019/11/24
 * Description:
 */
@Data  // 配合lombok实现，getter/setter/toString...
public class Page {

    /**
     * 数据网站的根地址
     *  eg：https://www.gushiwen.org/
     */
    private final String base;

    /**
     * 具体网页的路径
     *  eg：shiwenv_4cb0ae5bbff8.aspx
     */
    private final String path;

    /**
     * 根据网站根地址base+网页路径path==>当前网页的url地址
     * @return
     */
    public String getUrl(){
        return this.base+this.path;
    }

    /**
     * 网页的DOM对象(文档对象模型)
     */
    private HtmlPage htmlPage;

    /**
     * 标识网页是否是详情页
     */
    private final boolean detail;

    /**
     * 子页面对象集合，如果标识网页是详情页，则该集合为空，否则肯定有值
     */
    private Set<Page> subPage = new HashSet<>();

    /**
     * 数据对象
     */
    private DataSet dataSet = new DataSet();

}
