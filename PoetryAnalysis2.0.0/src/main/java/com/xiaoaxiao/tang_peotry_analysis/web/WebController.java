package com.xiaoaxiao.tang_peotry_analysis.web;//package com.xiaoaxiao.tang_peotry_analysis.web;
//
//import com.google.gson.Gson;
//import com.xiaoaxiao.tang_peotry_analysis.analyze.model.AuthorCount;
//import com.xiaoaxiao.tang_peotry_analysis.analyze.model.WordCount;
//import com.xiaoaxiao.tang_peotry_analysis.analyze.service.AnalyzeService;
//import com.xiaoaxiao.tang_peotry_analysis.config.ObjectFactory;
//import com.xiaoaxiao.tang_peotry_analysis.crawler.Crawler;
//
//
//import java.util.List;
//
///**
// * Created by xiaoaxiao on 2019/11/26
// * Description: Web服务器API
// *      1.SparkJava框架完成WebAPI开发
// *      2.用Servlet技术实现WebAPI开发
// */
//public class WebController {
//
//    private final AnalyzeService analyzeService;
//
//    public WebController(AnalyzeService analyzeService) {
//        this.analyzeService = analyzeService;
//    }
//
//    // -> http://127.0.0.1:4567
//    // -> /analyze/author_count
//    private List<AuthorCount> analyzeAuthorCount(){
//        return this.analyzeService.analyzeAuthorCount();
//    }
//
//    // -> http://127.0.0.1:4567
//    // -> /analyze/word_cloud
//    private List<WordCount> analyzeWordCloud(){
//        return this.analyzeService.analyzeWordCloud();
//    }
//
//    public void launch(){
//
//        ResponseTransformer responseTransformer = new JSONResponseTransformer();
//
//        // src/main/resources/static
//        // 设置前端静态文件的目录
//        Spark.staticFileLocation("/static");
//
//        // 服务端接口
//        Spark.get("/analyze/author_count",((request, response) ->
//            analyzeAuthorCount()),responseTransformer);
//
//        Spark.get("/analyze/word_cloud",((request, response) ->
//                analyzeWordCloud()),responseTransformer);
//
//        // 爬虫停止
//        Spark.get("/crawler/stop",(((request, response) -> {
//            Crawler crawler = ObjectFactory.getInstance().getObject(Crawler.class);
//            crawler.stop();
//            return "Crawler stopped";
//        })));
//    }
//
//    /**
//     * Java对象与字符串的转换器，通过一个内部类实现
//     */
//    public static class JSONResponseTransformer implements ResponseTransformer{
//
//        // Object->String
//        // Java对象转换为字符串
//        private Gson gson = new Gson();
//
//        @Override
//        public String render(Object o) throws Exception {
//            return gson.toJson(o);
//        }
//    }
//}
