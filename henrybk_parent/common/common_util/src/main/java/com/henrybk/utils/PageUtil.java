package com.henrybk.utils;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

/**
 * @description mp分页工具类
 * @author Henry
 * @since 2023-05-27
 */
public class PageUtil {

    /**
     * 将List转Page
     */
    public static <T> Page<T> listToPage(List<T> list, Integer pageNum, Integer pageSize){
        Page<T> page = new Page<>(pageNum,pageSize);
        page.setTotal(list.size());
        int startIndex = (pageNum - 1) * pageSize;
        if(CollectionUtils.isEmpty(list)){
            page.setRecords(null);
        } else {
            int endIndex = pageNum * pageSize;
            page.setRecords(list.subList(startIndex,endIndex > list.size() ? list.size() : endIndex));
        }
        return page;
    }

    /**
     * 将Map转Page
     */
    public static <T> Page<T> mapToPage(Map<String, Object> map, Class<T> clazz) {
        Page<T> page = new Page<>();
        page.setCurrent(Long.valueOf(map.get("current").toString()));
        page.setSize(Long.valueOf(map.get("size").toString()));
        page.setTotal(Long.valueOf(map.get("total").toString()));
        page.setRecords(JSON.parseArray(map.get("records").toString(), clazz));
        return page;
    }
}
