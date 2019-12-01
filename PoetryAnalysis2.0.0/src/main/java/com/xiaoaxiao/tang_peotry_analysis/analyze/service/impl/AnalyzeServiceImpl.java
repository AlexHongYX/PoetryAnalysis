package com.xiaoaxiao.tang_peotry_analysis.analyze.service.impl;

import com.alibaba.druid.pool.DruidDataSource;
import com.xiaoaxiao.tang_peotry_analysis.analyze.dao.AnalyzeDao;
import com.xiaoaxiao.tang_peotry_analysis.analyze.dao.impl.AnalyzeDaoImpl;
import com.xiaoaxiao.tang_peotry_analysis.analyze.entity.PoetryInfo;
import com.xiaoaxiao.tang_peotry_analysis.analyze.model.AuthorCount;
import com.xiaoaxiao.tang_peotry_analysis.analyze.model.WordCount;
import com.xiaoaxiao.tang_peotry_analysis.analyze.service.AnalyzeService;
import com.xiaoaxiao.tang_peotry_analysis.config.ConfigProperties;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.NlpAnalysis;

import java.util.*;

/**
 * Created by xiaoaxiao on 2019/11/26
 * Description: 业务层实现类
 */
public class AnalyzeServiceImpl implements AnalyzeService {

    /**
     * Service层依赖Dao层
     */
    private final AnalyzeDao analyzeDao;

    public AnalyzeServiceImpl(AnalyzeDao analyzeDao) {
        this.analyzeDao = analyzeDao;
    }

    @Override
    public List<AuthorCount> analyzeAuthorCount() {
        // 进行排序
        //  1、DAO层sql排序
        //  2、Service层进行数据排序
        List<AuthorCount> authorCountList = this.analyzeDao.analyzeAuthorCount();
        // 将List按照count升序排序
        authorCountList.sort(new Comparator<AuthorCount>() {
            @Override
            public int compare(AuthorCount o1, AuthorCount o2) {
                // 降序，若o1<o2则会返回正数，交换o1和o2，也就是小的放到后面去了
                return o2.getCount() - o1.getCount();
            }
        });
        return authorCountList;
    }

    @Override
    public List<WordCount> analyzeWordCloud() {

        // 存放词云的map
        Map<String,Integer> map = new HashMap<>();
        // 1、查询出所有数据
        // 2、取出title content
        // 3、进行分词，title和count中的词语进行分组
        // 4、统计 k-v，k是词，v是词的出现次数
        List<PoetryInfo> poetryInfoList = this.analyzeDao.queryAllPoetryInfo();

        for (PoetryInfo poetryInfo : poetryInfoList){
            List<Term> terms = new ArrayList<>();
            String title = poetryInfo.getTitle();
            String content = poetryInfo.getContent();

            terms.addAll(NlpAnalysis.parse(title).getTerms());
            terms.addAll(NlpAnalysis.parse(content).getTerms());

            // 将词性/w，null，空，length<2的过滤掉
            Iterator<Term> iterator = terms.iterator();
            while (iterator.hasNext()){
                Term term = iterator.next();
                // 词性的过滤
                if (term.getNatureStr()==null||term.getNatureStr().equals("w")){
                    iterator.remove();
                    // 进行下一次循环，已经移除了当前元素，需要直接退出
                    continue;
                }
                // 词长度的过滤
                if (term.getRealName().length()<2){
                    iterator.remove();
                    continue;
                }
                // 统计
                // 不存在初始化为1，存在value+1
                String realName = term.getRealName();
                if (map.containsKey(realName)){
                    map.put(realName,map.get(realName)+1);
                }else {
                    map.put(realName,1);
                }
            }
        }
        List<WordCount> wordCountList = new ArrayList<>();
        for (Map.Entry<String,Integer> entry : map.entrySet()){
            WordCount wordCount = new WordCount();
            wordCount.setCount(entry.getValue());
            wordCount.setWord(entry.getKey());

            wordCountList.add(wordCount);
        }
        return wordCountList;
    }

    public static void main(String[] args) {
        ConfigProperties configProperties = new ConfigProperties();
        // 准备数据源
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername(configProperties.getDbUsername());
        dataSource.setPassword(configProperties.getDbPassword());
        dataSource.setDriverClassName(configProperties.getDbDriverClass());
        dataSource.setUrl(configProperties.getDbUrl());

        AnalyzeDao analyzeDao = new AnalyzeDaoImpl(dataSource);

        AnalyzeService analyzeService = new AnalyzeServiceImpl(analyzeDao);

//        analyzeService.analyzeAuthorCount().forEach(
//                authorCount -> {
//                    System.out.println(authorCount);
//                }
//        );
        analyzeService.analyzeWordCloud().forEach(
                wordCount -> {
                    System.out.println(wordCount);
                }
        );
    }
//    public static void main(String[] args) {
//        Result result = NlpAnalysis.parse("三日入厨下，洗手作羹汤。\n" +
//                "未谙姑食性，先遣小姑尝。");
//        result.getTerms().forEach(
//                term -> {
//                    System.out.println(term);
//                }
//        );
//    }
}
