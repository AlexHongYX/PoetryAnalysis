package com.xiaoaxiao.tang_peotry_analysis.crawler;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.xiaoaxiao.tang_peotry_analysis.crawler.common.Page;
import com.xiaoaxiao.tang_peotry_analysis.crawler.parse.Parse;
import com.xiaoaxiao.tang_peotry_analysis.crawler.pipeline.Pipeline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by xiaoaxiao on 2019/11/25
 * Description:
 */
public class Crawler {

    /**
     * 通过打印日志，别让程序崩了，错误信息打印到日志中即可
     */
    private final Logger logger = LoggerFactory.getLogger(Crawler.class);

    /**
     * 放置文档页面（超链接）
     * 未被采集和解析的页面
     *  Page的htmlPage和dataSet还没有
     *
     *  通过所有线程共享的队列，不同的Page对象肯定会由不同的线程进行处理的
     */
    private Queue<Page> docQueue = new LinkedList<>();

    /**
     * 放置详情页面
     *  处理完成，数据已经在当前page的dataSet中了
     */
    private Queue<Page> detailQueue = new LinkedBlockingQueue<>();

    /**
     * 采集器
     */
    private final WebClient webClient;

    /**
     * 所以的解析器
     */
    private final List<Parse> parseList = new LinkedList<>();

    /**
     * 所有的清洗器（管道）
     */
    private final List<Pipeline> pipelineList = new LinkedList<>();

    /**
     * 线程调度器
     */
    private final ExecutorService executorService;

    public Crawler() {
        this.webClient = new WebClient();
        this.webClient.getOptions().setJavaScriptEnabled(false);
        this.webClient.getOptions().setCssEnabled(false);

        // 通过threadFactory设置thread线程的不同属性(名字)
        this.executorService = Executors.newFixedThreadPool(8, new ThreadFactory() {

            private final AtomicInteger id = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("Crawler-Thread-" + id.getAndIncrement());
                return thread;
            }
        });
    }

    public void start() {

        this.executorService.submit(
                new Runnable() {
                    @Override
                    public void run() {
                        // 爬取(采集)
                        // 解析
                        parse();
                    }
                }
        );

        this.executorService.submit(
                new Runnable() {
                    @Override
                    public void run() {
                        // 清洗(管道)
                        pipeline();
                    }
                }
        );
    }


    /**
     * 采集+解析
     */
    private void parse() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // {}表示一个占位符
                logger.error("Parse occur exception{}.",e.getMessage());
            }
            // 只有base path detail
            final Page page = this.docQueue.poll();
            if (page == null) {
                continue;
            }

            // 采用多线程进行采集解析
            this.executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        // 采集
                        HtmlPage htmlPage = Crawler.this.webClient.getPage(page.getUrl());
                        page.setHtmlPage(htmlPage);

                        // 在不同解析器里面已经进行过判断了
                        // 文档页面，详情解析就不会解析
                        // 详情页面，文档解析就不会解析
                        for (Parse parse : Crawler.this.parseList){
                            parse.parse(page);
                        }

//                System.out.println(page);

                        // 如果当前页面是详情页，则将该详情页加入到详情队列中等待解析
                        if (page.isDetail()){
                            Crawler.this.detailQueue.offer(page);
                        }else {
//                    // 如果当前页面是文档页面，则将该文档页面的子页面先放入详情页面，再进行判断
                            Iterator<Page> iterator = page.getSubPage().iterator();

//                    for (Page subPage : page.getSubPage()){
//                        this.docQueue.offer(subPage);
//                    }
                            while (iterator.hasNext()){
                                Page subPage = iterator.next();

//                        Thread.sleep(500);
//                        System.out.println(subPage);
                                // 将该子页面加入到文档队列中后，再次进行解析(不用再判断)
                                Crawler.this.docQueue.offer(subPage);
//                        // 将该子页面删除
                                iterator.remove();
                            }
                        }
                    } catch (IOException e) {
                        logger.error("Parse occur exception{}.",e.getMessage());
                    }
                }
            });
        }
    }

    /**
     * 清洗
     */
    private void pipeline() {
        while (true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error("Parse occur exception{}.",e.getMessage());
            }
            final Page page = this.detailQueue.poll();
            if (page==null){
                continue;
            }

            this.executorService.submit(new Runnable() {
                @Override
                public void run() {
                    for (Pipeline pipeline : Crawler.this.pipelineList){
                        pipeline.pipeline(page);
                    }
                }
            });
        }
    }

    /**
     * 停止爬虫：关闭线程池即可
     */
    public void stop() {
        if (this.executorService != null && this.executorService.isShutdown()) {
            this.executorService.shutdown();
        }
        logger.info("Crawler stopped ...");
    }

    public void addParse(Parse parse){
        this.parseList.add(parse);
    }

    public void addPipeline(Pipeline pipeline) {
        this.pipelineList.add(pipeline);
    }

    public void addPage(Page page) {
        this.docQueue.add(page);
    }


//    public static void main(String[] args) throws IOException {
//        final Page page = new Page("https://so.gushiwen.org/", "gushi/tangshi.aspx", false);
////        page.setBase("");
//////        page.setPath("shiwenv_d6640103678f.aspx");
//////        page.setDetail(true);
////
////        page.setPath("gushi/tangshi.aspx");
////        page.setDetail(false);
//
//        WebClient webClient = new WebClient(BrowserVersion.CHROME);
//        webClient.getOptions().setJavaScriptEnabled(false);
//        webClient.getOptions().setCssEnabled(false);
//
//        HtmlPage htmlPage = webClient.getPage(page.getUrl());
//        page.setHtmlPage(htmlPage);
//
////        System.out.println(page.getUrl());
////        System.out.println(page.getHtmlPage().toString());
////        Parse parse = new DataPageParse();
////        parse.parse(page);
//
//        List<Parse> parseList = new LinkedList<>();
//        parseList.add(new DocumentParse());
//        parseList.add(new DataPageParse());
//
//
////        Pipeline pipeline = new ConsolePipeline();
////        pipeline.pipeline(page);
//    }
}
