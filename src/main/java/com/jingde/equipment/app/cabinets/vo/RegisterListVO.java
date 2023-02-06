package com.jingde.equipment.app.cabinets.vo;

import com.jingde.equipment.app.firearms.vo.TypeTitleVO;

import java.util.List;

/**
 * @Description: com.jingde.equipment.app.system.vo
 * @Title: RegisterListVO
 * @Auther: CzSix
 * @create 2019/3/12 9:56
 */
public class RegisterListVO {
    private List<TypeVO> list;
    private List<TypeTitleVO> title;
    private int total; //总的记录数
    private int page; //页码
    private int size; //记录数

    public List<TypeTitleVO> getTitle() {
        return title;
    }

    public void setTitle(List<TypeTitleVO> title) {
        this.title = title;
    }

    public List<TypeVO> getList() {
        return list;
    }

    public void setList(List<TypeVO> list) {
        this.list = list;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
