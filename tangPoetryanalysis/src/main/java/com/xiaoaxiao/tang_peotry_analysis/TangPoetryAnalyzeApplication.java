package com.xiaoaxiao.tang_peotry_analysis;

import com.alibaba.druid.pool.DruidDataSource;
import com.xiaoaxiao.tang_peotry_analysis.analyze.service.AnalyzeService;
import com.xiaoaxiao.tang_peotry_analysis.config.ConfigProperties;
import com.xiaoaxiao.tang_peotry_analysis.config.ObjectFactory;
import com.xiaoaxiao.tang_peotry_analysis.crawler.Crawler;
import com.xiaoaxiao.tang_peotry_analysis.crawler.common.Page;
import com.xiaoaxiao.tang_peotry_analysis.crawler.parse.DataPageParse;
import com.xiaoaxiao.tang_peotry_analysis.crawler.parse.DocumentParse;
import com.xiaoaxiao.tang_peotry_analysis.crawler.pipeline.ConsolePipeline;
import com.xiaoaxiao.tang_peotry_analysis.crawler.pipeline.DataBasePipeline;
import com.xiaoaxiao.tang_peotry_analysis.web.WebController;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.time.LocalDateTime;

import static spark.route.HttpMethod.get;

/**
 * Created by xiaoaxiao on 2019/11/24
 * Description: 唐诗分析程序的主类
 */
public class TangPoetryAnalyzeApplication {
    // 添加日志类
    private static final org.slf4j.Logger LOGGER =
            LoggerFactory.getLogger(TangPoetryAnalyzeApplication.class);

    public static void main(String[] args) {
//        // 引入配置类
//        ConfigProperties configProperties = new ConfigProperties();
//
//        // 创建初始的Page对象
//        final Page page = new Page(configProperties.getCrawlerBase(),
//                configProperties.getCrawlerPath(),configProperties.isCrawlerDetail());
//
//        // 创建爬虫调度器
//        Crawler crawler = new Crawler();
//
//        // 给爬虫调度器添加采集/解析器
//        crawler.addParse(new DataPageParse());
//        crawler.addParse(new DocumentParse());
//
//        // 给爬虫调度器添加管道
//        crawler.addPipeline(new ConsolePipeline());
//        // 准备数据源
//        DruidDataSource dataSource = new DruidDataSource();
//        dataSource.setUsername(configProperties.getDbUsername());
//        dataSource.setPassword(configProperties.getDbPassword());
//        dataSource.setDriverClassName(configProperties.getDbDriverClass());
//        dataSource.setUrl(configProperties.getDbUrl());
//        crawler.addPipeline(new DataBasePipeline(dataSource));
//
//        // 给爬虫调度器添加初始Page对象
//        crawler.addPage(page);
//
//        // 开启爬虫调度器
//        crawler.start();

        // 使用工厂模式
        WebController webController = ObjectFactory.getInstance().getObject(WebController.class);

        // 运行了web服务，提供接口
        LOGGER.info("Web Server launch...");
        webController.launch();

        // 启动爬虫
        // 如果在命令行中添加了参数run-crawler，则才会启动爬虫，否则不会启动爬虫
        if (args.length==1 && args[0].equals("run-crawler")){
            Crawler crawler = ObjectFactory.getInstance().getObject(Crawler.class);
            LOGGER.info("Crawler started...");
            crawler.start();
        }
    }
}
