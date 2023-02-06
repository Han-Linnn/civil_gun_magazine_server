package com.jingde.equipment.util;

import com.github.pagehelper.PageInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageUtil {
    /**
     * format page info
     *
     * @param pageInfo
     * @return 格式化后的结果
     */
    public static Map formatPageResult(PageInfo pageInfo) {
        long total = pageInfo.getTotal();
        int page = pageInfo.getPageNum();
        int size = pageInfo.getPageSize();
//        int pages = pageInfo.getPages();
//        int size = pageInfo.getSize();
        List list = pageInfo.getList();
        if (list.size() == 0) {
            page = 1;
        }
        Map result = new HashMap();
        result.put("list", list);
        result.put("total", total);
        result.put("page", page);
        result.put("size", size);
//        result.put("pages", pages);
//        result.put("size", size);
        return result;
    }
}
