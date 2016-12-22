package com.kazyle.hugohelper.server.config.domain.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Kazyle on 2016/8/23.
 */
public class PageImpl<T> implements Page<T> {

    private final List<T> list = new ArrayList<T>();
    private final Pageable pageable;
    private final long total;

    public PageImpl(List<T> content, Pageable pageable, long total) {
        // TODO Auto-generated constructor stub
        if (null == content) {

            throw new IllegalArgumentException("数据不能为空");
        }
        this.list.addAll(content);
        this.total = total;
        this.pageable = pageable;
    }

    public PageImpl(List<T> content) {
        // TODO Auto-generated constructor stub
        this(content, null, null == content ? 0 : content.size());
    }

    @Override
    public int getCurrentPage() {
        // TODO Auto-generated method stub
        return pageable == null ? 0 : pageable.getPage();
    }

    @Override
    public int getRows() {
        // TODO Auto-generated method stub
        return pageable == null ? 0 : pageable.getRows();
    }

    @Override
    public int getTotalPages() {
        // TODO Auto-generated method stub
        return getRows() == 0 ? 1 : (int)Math.ceil((double) total / (double) getRows());
    }

    @Override
    public int getNumberOfElements() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public long getTotalElements() {
        // TODO Auto-generated method stub
        return total;
    }

    @Override
    public boolean hasPreviousPage() {
        // TODO Auto-generated method stub
        return getCurrentPage() > 0;
    }

    @Override
    public boolean isFirstPage() {
        // TODO Auto-generated method stub
        return !hasPreviousPage();
    }

    @Override
    public boolean hasNextPage() {
        // TODO Auto-generated method stub
        return getCurrentPage() + 1 < getTotalPages();
    }

    @Override
    public boolean isLastPage() {
        // TODO Auto-generated method stub
        return !hasNextPage();
    }

    @Override
    public Iterator<T> iterator() {
        // TODO Auto-generated method stub
        return list.iterator();
    }

    @Override
    public List<T> getList() {
        // TODO Auto-generated method stub
        // Collections.unmodifiableList(content) // 是否需要不可修改的属性
        return list;
    }

    @Override
    public boolean hasContent() {
        // TODO Auto-generated method stub
        return !list.isEmpty();
    }

    @Override
    public boolean getHasContent() {
        return hasContent();
    }
}
