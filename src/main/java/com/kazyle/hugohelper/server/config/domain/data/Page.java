package com.kazyle.hugohelper.server.config.domain.data;

import java.awt.print.Pageable;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Kazyle on 2016/8/23.
 */
public interface Page<T> extends Iterable<T> {

    /**
     * 当前页码
     *
     * @return
     */
    int getCurrentPage();

    /**
     * 每页记录数
     *
     * @return
     */
    int getRows();

    /**
     * 总页数
     *
     * @return
     */
    int getTotalPages();

    /**
     * 当前页返回的记录数
     *
     * @return
     */
    int getNumberOfElements();

    /**
     * 总记录数
     *
     * @return
     */
    long getTotalElements();

    /**
     * 是否有上一页
     *
     * @return
     */
    boolean hasPreviousPage();

    /**
     * 是否第一页
     *
     * @return
     */
    boolean isFirstPage();

    /**
     * 是否有下一页
     *
     * @return
     */
    boolean hasNextPage();

    /**
     * 是否最后一页
     *
     * @return
     */
    boolean isLastPage();

    /**
     * 数据遍历器
     *
     * @return
     */
    Iterator<T> iterator();

    /**
     * 分页数据
     *
     * @return
     */
    List<T> getList();

    /**
     * 是否有分页数据
     *
     * @return
     */
    boolean hasContent();

    /**
     * 是否有分页数据
     * @return
     */
    boolean getHasContent();
}
