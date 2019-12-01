package com.xiaoaxiao.tang_peotry_analysis.servlet;

import com.google.gson.Gson;
import com.xiaoaxiao.tang_peotry_analysis.analyze.model.AuthorCount;
import com.xiaoaxiao.tang_peotry_analysis.analyze.service.AnalyzeService;
import com.xiaoaxiao.tang_peotry_analysis.config.ObjectFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by xiaoaxiao on 2019/11/30
 * Description: 作者及其作品数量web接口
 */
public class AuthorCountServlet extends HttpServlet {

    // 通过对象工厂加载
    private AnalyzeService analyzeService = ObjectFactory.getInstance().getObject(AnalyzeService.class);

    private List<AuthorCount> analyzeAuthorCount(){
        return this.analyzeService.analyzeAuthorCount();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置返回字符类型为UTF-8
        resp.setCharacterEncoding("UTF-8");
        // 设置返回类型为json
        resp.setContentType("application/json;charset=utf-8");
        // 使用gson将Java对象转换为字符串
        Gson gson = new Gson();
        // 返回gson转换Java对象后的json字符串
        resp.getWriter().println(gson.toJson(analyzeAuthorCount()));
//        PrintWriter out = resp.getWriter();
//        System.out.println("Hello world");
//        out.println(gson.toJson(analyzeAuthorCount()));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
