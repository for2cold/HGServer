package com.kazyle.hugohelper.server.config.domain.data;

/**
 * Created by Kazyle on 2016/8/23.
 */
public interface Pageable {

    /**
     * 当前页
     *
     * @return
     */
    int getPage();

    /**
     * 返回数据数
     *
     * @return
     */
    int getRows();

    /**
     * 数据偏移量（即到当前页的数据数）
     *
     * @return
     */
    int getOffset();

    /**
     * 数据偏移量（即到计算后页码的数据数）
     * @param index
     * @return
     */
    int getOffset(int index);

    /**
     * 下一页分页信息
     *
     * @return
     */
    Pageable next();

    /**
     * 上一页或第一页
     *
     * @return
     */
    Pageable previousOrFirst();

    /**
     * 第一页
     *
     * @return
     */
    Pageable first();

    /**
     * 是否有上一页
     *
     * @return
     */
    boolean hasPrevious();

    /**
     * 设置每页大小
     */
    void setRows(int rows);

    /**
     * 设置页码
     * @param page
     */
    void setPage(int page);
}
