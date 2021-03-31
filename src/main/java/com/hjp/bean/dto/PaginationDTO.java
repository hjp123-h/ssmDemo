package com.hjp.bean.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @description: 分页后台实现
 * @author: Hjp
 * @time: 2021/3/30 22:39
 */
@Data
public class PaginationDTO {

    private Integer typeId;//所属分类id
    private boolean showPrevious;//前一页按钮
    private boolean showFirstPage;//首页按钮
    private boolean showNext;//下一页按钮
    private boolean showEndPage;//尾页按钮
    private Integer page;//条数
    private List<Integer> pages = new ArrayList<>();//条数数组 最长为7
    private Integer totalPage;//总分页数
    private Integer totalPages;//总条数

    //分页工具类
    public void setPagination(Integer totalCount, Integer page, Integer size) {
        //获取总条数
        totalPages = totalCount;
        //有余数多加一页
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            //默认一页
            totalPage = totalCount / size + 1;
        }

        //默认一页
        if (page < 1) {
            page = 1;
        }
        //更新page
        if (page > totalPage) {
            page = totalPage;
        }
        this.page = page;

        //1     1 2 3 4
        //4     1 2 3 4 5 6 7
        //5     2 3 4 5 6 7 8

        //左右两边展示3个页码
        pages.add(page);
        for (int i = 1; i <= 3; i++) {
            //page == 2 会将1添加进入
            if (page - i > 0) {
                pages.add(0, page - i);
            }
            //page == 2+1 还小于总条数时 将3加入
            if (page + i <= totalPage) {
                pages.add(page + i);
            }
        }

        //是否展示上一页
        if (page == 1) {
            showPrevious = false;
        } else {
            showPrevious = true;
        }
        //是否展示下一页
        if (page.equals(totalPage)) {
            showNext = false;
        } else {
            showNext = true;
        }

        //集合中包含第一页的情况下不展示返回第一页
        if (pages.contains(1)) {
            showFirstPage = false;
        } else {
            showFirstPage = true;
        }

        //是否展示最后一页
        if (pages.contains(totalPage)) {
            showEndPage = false;
        } else {
            showEndPage = true;
        }

    }

}
