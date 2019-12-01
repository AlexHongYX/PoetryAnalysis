package com.xiaoaxiao.tang_peotry_analysis.crawler.parse;

import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.xiaoaxiao.tang_peotry_analysis.crawler.common.Page;

/**
 * Created by xiaoaxiao on 2019/11/25
 * Description: 非详情页面，链接解析
 */
public class DocumentParse implements Parse{
    @Override
    public void parse(final Page page) {
        if (page.isDetail()){
            return;
        }

        HtmlPage htmlPage = page.getHtmlPage();
        htmlPage.getBody().getElementsByAttribute("div","class","typecont")
                .forEach(div -> {
                    DomNodeList<HtmlElement> aNodeList = div.getElementsByTagName("a");
                    aNodeList.forEach(
                            aNode -> {
//                                System.out.println(aNode.asXml());
                                String path = aNode.getAttribute("href");

                                Page subPage = new Page(page.getBase(),path,true);
                                page.getSubPage().add(subPage);
                            }
                    );
                }
        );
    }
}
