package com.qianjujun.data;

import com.qianjujun.frame.data.IPageData;

import java.util.List;

public class PageData<T> implements IPageData<T> {
    private int total;
    private boolean hasNextPage;
    private List<T> list;
    private int nextPage;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public List<T> getDataList() {
        return getList();
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    @Override
    public String nextPageParam() {
        return String.valueOf(nextPage);
    }

    @Override
    public boolean haveNextPage() {
        return isHasNextPage();
    }
}
