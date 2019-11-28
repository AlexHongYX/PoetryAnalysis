package com.xiaoaxiao.tang_peotry;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import spark.Spark;

import java.io.IOException;

import static spark.route.HttpMethod.get;

/**
 * Created by xiaoaxiao on 2019/11/24
 * Description: 测试使用htmlUnit开源库
 */
public class TestHtmlUnit {
//    public static void main(String[] args) {
//        try(WebClient webClient = new WebClient(BrowserVersion.CHROME)){
//
//            // 除去js文件和css文件
//            webClient.getOptions().setJavaScriptEnabled(false);
//            webClient.getOptions().setCssEnabled(false);
//
//            HtmlPage htmlPage = webClient.getPage("https://so.gushiwen.org//shiwenv_d5cea0c3607d.aspx");
//
//            HtmlElement bodyElement = htmlPage.getBody();
//
//            // asText()只取文本，asXml()取结构<标签>+文本
////            String text = bodyElement.asXml();
////
////            System.out.println(text);
//
//            // 通过id取得元素
////            DomElement domElement = htmlPage.getElementById("contson62428f028ad6");
//            // 直接转为HTMlDivision
////            HtmlDivision htmlDivision = (HtmlDivision)htmlPage.getElementById("contson62428f028ad6");
////
////            // 清洗
////            String divText = htmlDivision.asText();
////            System.out.println(divText);
//
////            先使用Object接收，测试这是什么类型
////            Object o = bodyElement.getByXPath(author).get(0);
////            System.out.println(o.getClass().getName());
//            // 标题
//            String titlePath = "//div[@class='cont']/h1/text()";
//            DomText titleDom = (DomText)bodyElement.getByXPath(titlePath).get(0);
//            String title = titleDom.asText();
//
//            // 朝代
//            String dynastyPath = "//div[@class='cont']/p[2]/a[1]";
//            HtmlAnchor dynastyDom = (HtmlAnchor) bodyElement.getByXPath(dynastyPath).get(0);
//            String dynasty = dynastyDom.asText();
//
//            // 作者
//            String authorPath = "//div[@class='cont']/p[2]/a[2]";
//            HtmlAnchor authorDom = (HtmlAnchor)bodyElement.getByXPath(authorPath).get(0);
//            String author = authorDom.asText();
//
//            // 正文
//            // 不能使用id，不同页面的id不同，而不同网页的结构是相同的
//            String contentPath = "//div[@class='cont']/div[@class='contson']";
//            HtmlDivision htmlDivision = (HtmlDivision)bodyElement.getByXPath(contentPath).get(0);
//            String content = htmlDivision.asText();
//
////            PoetryInfo poetryInfo = new PoetryInfo();
////            poetryInfo.setTitle(title);
////            poetryInfo.setDynasty(dynasty);
////            poetryInfo.setAuthor(author);
////            poetryInfo.setContent(content);
////
////            System.out.println(poetryInfo);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    public static void main(String[] args) {
        Spark.get("/hello",(req,resp)->{
            return "Hello World";
        });
    }
}
