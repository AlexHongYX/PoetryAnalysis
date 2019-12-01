package com.xiaoaxiao.tang_peotry_analysis.analyze.dao.impl;

import com.xiaoaxiao.tang_peotry_analysis.analyze.dao.AnalyzeDao;
import com.xiaoaxiao.tang_peotry_analysis.analyze.entity.PoetryInfo;
import com.xiaoaxiao.tang_peotry_analysis.analyze.model.AuthorCount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoaxiao on 2019/11/26
 * Description: Dao层实现
 */
public class AnalyzeDaoImpl implements AnalyzeDao {

    /**
     * 异常/输出都通过日志打印即可
     */
    private final Logger logger = LoggerFactory.getLogger(AnalyzeDaoImpl.class);

    /**
     * 创建数据源
     */
    private final DataSource dataSource;

    public AnalyzeDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<AuthorCount> analyzeAuthorCount() {

        // 作者list
        List<AuthorCount> datas = new ArrayList<>();

        // try()自动关闭
        String sql = "select count(*) as count,author from poetry_info group by author;";
        try (Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                AuthorCount authorCount = new AuthorCount();
                authorCount.setAuthor(rs.getString("author"));
                authorCount.setCount(rs.getInt("count"));

                datas.add(authorCount);
            }
        } catch (SQLException e) {
            logger.error("Database query occur exception {}.",e.getMessage());
        }
        return datas;
    }

    @Override
    public List<PoetryInfo> queryAllPoetryInfo() {
        List<PoetryInfo> datas = new ArrayList<>();

        String sql = "select title,dynasty,author,content from poetry_info;";
        try (Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                PoetryInfo poetryInfo = new PoetryInfo();
                poetryInfo.setTitle(rs.getString("title"));
                poetryInfo.setDynasty(rs.getString("dynasty"));
                poetryInfo.setAuthor(rs.getString("author"));
                poetryInfo.setContent(rs.getString("content"));

                datas.add(poetryInfo);
            }
        } catch (SQLException e) {
            logger.error("Database query occur exception {}.",e.getMessage());
        }
        return datas;
    }

    /**
     * 测试
     * @param
     */
//    public static void main(String[] args) {
//        ConfigProperties configProperties = new ConfigProperties();
//        // 准备数据源
//        DruidDataSource dataSource = new DruidDataSource();
//        dataSource.setUsername(configProperties.getDbUsername());
//        dataSource.setPassword(configProperties.getDbPassword());
//        dataSource.setDriverClassName(configProperties.getDbDriverClass());
//        dataSource.setUrl(configProperties.getDbUrl());
//
//        AnalyzeDao analyzeDao = new AnalyzeDaoImpl(dataSource);
//
//        System.out.println("测试一");
//
//        analyzeDao.analyzeAuthorCount().forEach(
//                authorCount -> {
//                    System.out.println(authorCount);
//                }
//        );
//
//        System.out.println("测试二");
//        analyzeDao.queryAllPoetryInfo().forEach(
//                poetryInfo -> {
//                    System.out.println(poetryInfo);
//                }
//        );
//    }
}
