package com.xiaoaxiao.tang_peotry_analysis.crawler.parse;

import com.gargoylesoftware.htmlunit.html.*;
import com.xiaoaxiao.tang_peotry_analysis.crawler.common.Page;

/**
 * Created by xiaoaxiao on 2019/11/24
 * Description: 详情页面解析
 */
public class DataPageParse implements Parse {
    @Override
    public void parse(final Page page) {
        // 如果不是详情页不能解析直接返回
        if (!page.isDetail()){
            return;
        }

//        HtmlDivision division = (HtmlDivision)page.getHtmlPage().getElementById("contsond6640103678f");
//
//        String content = division.asText();
//
//        page.getDataSet().putData("正文",content);

        // 获取body
        HtmlPage htmlPage = page.getHtmlPage();
        HtmlElement bodyElement = htmlPage.getBody();

        // 标题
        String titlePath = "//div[@class='cont']/h1/text()";
        DomText titleDom = (DomText)bodyElement.getByXPath(titlePath).get(0);
        String title = titleDom.asText();

        // 朝代
        String dynastyPath = "//div[@class='cont']/p/a[1]";
        HtmlAnchor dynastyDom = (HtmlAnchor) bodyElement.getByXPath(dynastyPath).get(0);
        String dynasty = dynastyDom.asText();

        // 作者
        String authorPath = "//div[@class='cont']/p/a[2]";
        HtmlAnchor authorDom = (HtmlAnchor)bodyElement.getByXPath(authorPath).get(0);
        String author = authorDom.asText();

        // 正文
        // 不能使用id，不同页面的id不同，而不同网页的结构是相同的
        String contentPath = "//div[@class='cont']/div[@class='contson']";
        HtmlDivision htmlDivision = (HtmlDivision)bodyElement.getByXPath(contentPath).get(0);
        String content = htmlDivision.asText();

        // 这个PoetryInfo没必要创建，直接将它的几个属性放在Page对象内部集合即可
        // 在清洗pipeline中直接将这几个属性取出
        page.getDataSet().putData("title",title);
        page.getDataSet().putData("dynasty",dynasty);
        page.getDataSet().putData("author",author);
        page.getDataSet().putData("content",content);
//        PoetryInfo poetryInfo = new PoetryInfo();
//        poetryInfo.setTitle(title);
//        poetryInfo.setDynasty(dynasty);
//        poetryInfo.setAuthor(author);
//        poetryInfo.setContent(content);
//
//        // 将数据放入Page对象内部集合
//        page.getDataSet().putData("poetry",poetryInfo);
    }
}
