package com.zwm.entity;

import org.springframework.stereotype.Component;

@Component
public class Page {
    private int current = 1;//当前页码
    private int limit = 10;//当前页显示上限
    private int rows;//数据总数
    private String path;//查询路径

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        //只有 current > 0 的时候才返回
        if (current > 0) this.current = current;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        //上限规定在 1 - 100 之间
        if (limit >= 1 && limit <= 100) this.limit = limit;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        //总数需 >= 0
        if (rows >= 0) this.rows = rows;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    //根据当前页码获取起始行
    public int getStart() {
        return (current - 1) * limit;
    }

    //获取总页数
    public int getTotal() {
        //如果总条数除得尽每页显示上线则返回 rows / limit
        if(rows % limit ==  0) return rows / limit;
        else return rows / limit + 1;
    }

    //页面不可能一共有多少页就显示多少页，为了美观这里显示当前页码的前两个页码
    public int getFrom() {
        int from = current - 2;
        return from < 1 ? 1 : from;
    }

    //显示当前页码的后两个页码
    public int getTo() {
        int to = current + 2;
        int total = getTotal();
        return to > total ? total : to;
    }
}
