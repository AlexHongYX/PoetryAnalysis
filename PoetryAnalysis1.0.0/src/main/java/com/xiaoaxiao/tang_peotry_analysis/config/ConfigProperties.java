package com.xiaoaxiao.tang_peotry_analysis.config;

import lombok.Data;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by xiaoaxiao on 2019/11/26
 * Description: 爬虫配置类
 */
@Data
public class ConfigProperties {

    private String crawlerBase;
    private String crawlerPath;
    private boolean crawlerDetail;

    private String dbUsername;
    private String dbPassword;
    private String dbUrl;
    private String dbDriverClass;

    private boolean enableConsole;

    public ConfigProperties() {
        // 从外部.properties文件去加载
        InputStream inputStream = ConfigProperties.class.getClassLoader().getResourceAsStream("config.properties");
        Properties p = new Properties();
        try {
            p.load(inputStream);
//            System.out.println(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.crawlerBase = String.valueOf(p.get("crawler.base"));
        this.crawlerPath = String.valueOf(p.get("crawler.path"));
        this.crawlerDetail = Boolean.parseBoolean(String.valueOf( p.get("crawler.detail")));
        this.dbUsername = String.valueOf(p.get("db.username"));
        this.dbPassword = String.valueOf(p.get("db.password"));
        this.dbUrl = String.valueOf(p.get("db.url"));
        this.dbDriverClass = String.valueOf(p.get("db.driver_class"));
        this.enableConsole = Boolean.valueOf(
                String.valueOf(p.getProperty("config.enable_console","false")));
    }

    public static void main(String[] args) {
        new ConfigProperties();
    }
}
