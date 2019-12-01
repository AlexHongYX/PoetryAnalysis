package com.xiaoaxiao.tang_peotry_analysis.crawler.pipeline;

import com.xiaoaxiao.tang_peotry_analysis.crawler.common.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by xiaoaxiao on 2019/11/26
 * Description: 将详情页写入数据库的管道
 */
public class DataBasePipeline implements Pipeline {

    private final Logger logger = LoggerFactory.getLogger(DataBasePipeline.class);

    private final DataSource dataSource;

    public DataBasePipeline(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void pipeline(final Page page) {
        // 同样不需要创建这个
//        PoetryInfo poetryInfo = (PoetryInfo) page.getDataSet().getData("poetry");
        String title = (String)page.getDataSet().getData("title");
        String dynasty = (String)page.getDataSet().getData("dynasty");
        String author = (String)page.getDataSet().getData("author");
        String content = (String)page.getDataSet().getData("content");

        // 数据插入
        String sql = "insert into poetry_info (title,dynasty,author,content) values (?,?,?,?)";
//        System.out.println("存储到数据库："+poetryInfo);
        try (Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,title);
            statement.setString(2,dynasty);
            statement.setString(3,author);
            statement.setString(4,content);

            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Database insert occur exception {}.",e.getMessage());
        }
    }
}
