package com.xiaoaxiao.tang_peotry_analysis.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.xiaoaxiao.tang_peotry_analysis.analyze.dao.AnalyzeDao;
import com.xiaoaxiao.tang_peotry_analysis.analyze.dao.impl.AnalyzeDaoImpl;
import com.xiaoaxiao.tang_peotry_analysis.analyze.service.AnalyzeService;
import com.xiaoaxiao.tang_peotry_analysis.analyze.service.impl.AnalyzeServiceImpl;
import com.xiaoaxiao.tang_peotry_analysis.crawler.Crawler;
import com.xiaoaxiao.tang_peotry_analysis.crawler.common.Page;
import com.xiaoaxiao.tang_peotry_analysis.crawler.parse.DataPageParse;
import com.xiaoaxiao.tang_peotry_analysis.crawler.parse.DocumentParse;
import com.xiaoaxiao.tang_peotry_analysis.crawler.pipeline.ConsolePipeline;
import com.xiaoaxiao.tang_peotry_analysis.crawler.pipeline.DataBasePipeline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiaoaxiao on 2019/11/26
 * Description: 对象工厂，只关心对象
 */
public class ObjectFactory {
    /**
     * 打印到控制台都换成日志
     */
    private static Logger logger = LoggerFactory.getLogger(ObjectFactory.class);

    private static final ObjectFactory instance = new ObjectFactory();

    /**
     * 存放所有的对象
     */
    private final Map<Class,Object> objectMap = new HashMap<>();

    private ObjectFactory(){
        // 1.初始化配置对象
        initConfigProperties();
        // 2.初始化数据源对象
        initDataSource();
        // 3.初始化爬虫对象
        initCrawler();
        // 4.Web对象
//        initWebController();
        // 5.对象清单打印输出
        printObjectList();
        initDao();
        initService();
    }

    private void initDao() {
        DruidDataSource dataSource = getObject(DruidDataSource.class);
        AnalyzeDao analyzeDao = new AnalyzeDaoImpl(dataSource);
        objectMap.put(AnalyzeDao.class,analyzeDao);
    }

    private void initService() {
        AnalyzeDao analyzeDao = getObject(AnalyzeDao.class);
        AnalyzeService analyzeService = new AnalyzeServiceImpl(analyzeDao);
        objectMap.put(AnalyzeService.class,analyzeService);
    }

//    private void initWebController() {
//        DruidDataSource dataSource = getObject(DruidDataSource.class);
//        AnalyzeDao analyzeDao = new AnalyzeDaoImpl(dataSource);
//        AnalyzeService analyzeService = new AnalyzeServiceImpl(analyzeDao);
//
//        WebController webController = new WebController(analyzeService);
//
//        objectMap.put(WebController.class,webController);
//    }

    private void initCrawler() {

        ConfigProperties configProperties = getObject(ConfigProperties.class);
        // 创建初始的Page对象
        final Page page = new Page(configProperties.getCrawlerBase(),
                configProperties.getCrawlerPath(),configProperties.isCrawlerDetail());
        // 创建爬虫调度器
        Crawler crawler = new Crawler();

        // 给爬虫调度器添加采集/解析器
        crawler.addParse(new DataPageParse());
        crawler.addParse(new DocumentParse());

        if (configProperties.isEnableConsole()){
            crawler.addPipeline(new ConsolePipeline());
        }


        DruidDataSource druidDataSource = getObject(DruidDataSource.class);
        crawler.addPipeline(new DataBasePipeline(druidDataSource));

        crawler.addPage(page);

        objectMap.put(Crawler.class,crawler);
    }

    private void initDataSource() {
        ConfigProperties configProperties = getObject(ConfigProperties.class);
        // 准备数据源
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername(configProperties.getDbUsername());
        dataSource.setPassword(configProperties.getDbPassword());
        dataSource.setDriverClassName(configProperties.getDbDriverClass());
        dataSource.setUrl(configProperties.getDbUrl());

        objectMap.put(DruidDataSource.class,dataSource);
    }

    private void initConfigProperties() {
        // 引入配置类
        ConfigProperties configProperties = new ConfigProperties();
        objectMap.put(ConfigProperties.class,configProperties);
        logger.info("ConfigProperties info:\n{}",configProperties.toString());
    }

    public <T> T getObject(Class classz){
        if (!objectMap.containsKey(classz)){
            throw new IllegalArgumentException("Class "+classz.getName()+" not found Object");

        }
        return (T)objectMap.get(classz);
    }

    public static ObjectFactory getInstance(){
        return instance;
    }

    private void printObjectList(){
        logger.info("============ObjectFactory List========");
        for (Map.Entry<Class,Object> entry:objectMap.entrySet()){
            logger.info(entry.getKey().getCanonicalName()
                    +"==>"+entry.getValue().getClass().getCanonicalName());
        }
        logger.info("============ObjectFactory end====");
    }
}
