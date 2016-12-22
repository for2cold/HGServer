package com.kazyle.hugohelper.server.config.domain.data;

/**
 * Created by Kazyle on 2016/8/23.
 */
public class PageRequest implements Pageable {

    private int page = 0;
    private int size = 100;

    public PageRequest() {
        super();
        // TODO Auto-generated constructor stub
    }

    public PageRequest(int page, int size) {
        // TODO Auto-generated constructor stub
        if (page < 0) {
            throw new IllegalArgumentException("页码不能小于0");
        }

        if (size < 0) {
            throw new IllegalArgumentException("分页数据数不能小于0");
        }
        this.page = page;
        this.size = size;
    }

    @Override
    public int getPage() {
        // TODO Auto-generated method stub
        return page;
    }

    @Override
    public void setPage(int page) {
        // TODO Auto-generated method stub
        this.page = page;
    }
    @Override
    public int getRows() {
        // TODO Auto-generated method stub
        return size;
    }

    @Override
    public int getOffset() {
        // TODO Auto-generated method stub
        return page * size;
    }

    @Override
    public int getOffset(int index) {
        // TODO Auto-generated method stub
        return (page + index) * size;
    }

    @Override
    public Pageable next() {
        // TODO Auto-generated method stub
        return new PageRequest(page + 1, size);
    }

    @Override
    public Pageable previousOrFirst() {
        // TODO Auto-generated method stub
        return hasPrevious() ? new PageRequest(page - 1, size) : this;
    }

    @Override
    public Pageable first() {
        // TODO Auto-generated method stub
        return new PageRequest(0, size);
    }

    @Override
    public boolean hasPrevious() {
        // TODO Auto-generated method stub
        return page > 0;
    }

    @Override
    public void setRows(int rows) {
        // TODO Auto-generated method stub
        this.size = rows;
    }
}
