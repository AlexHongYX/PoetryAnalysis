package com.xiaoaxiao.tang_peotry_analysis.crawler.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiaoaxiao on 2019/11/24
 * Description: 存储清洗的数据集合
 *      将HashMap包装了一下，创建这个程序的所有数据的集合
 *
 *      将程序的集合封装起来，不让其他人随意操作
 */
public class DataSet {

    /**
     * data把DOM解析，清洗之后存储的实际数据集合
     *  比如：
     *      标题：xxx
     *      作者：xxx
     *      正文：xxx
     */
    private Map<String,Object> data = new HashMap<>();

    public void putData(String key,Object value){
        this.data.put(key,value);
    }

    public Object getData(String key){
        return this.data.get(key);
    }

    public Map<String,Object> getDatas(){
        // 传一个新的map，防止被破坏
        return new HashMap<>(this.data);
    }

}
