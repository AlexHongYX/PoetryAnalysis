package com.xiaoaxiao.tang_peotry_analysis.servlet;

import com.xiaoaxiao.tang_peotry_analysis.config.ObjectFactory;
import com.xiaoaxiao.tang_peotry_analysis.crawler.Crawler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by xiaoaxiao on 2019/12/1
 * Description: 爬虫关闭web接口
 */
public class CrawlerStopServlet extends HttpServlet {
    private Crawler crawler = ObjectFactory.getInstance().getObject(Crawler.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        crawler.stop();
        resp.getWriter().println("Crawler stopped");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
