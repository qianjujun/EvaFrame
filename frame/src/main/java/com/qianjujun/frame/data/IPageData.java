package com.qianjujun.frame.data;

import java.util.List;

public interface IPageData<T> {
    List<T> getDataList();
    /**
     * 获取下一页数据的依据参数
     * @return
     */
    String nextPageParam();


    /**
     * 是否还有下一页数据  可通过
     * @return
     */
    boolean haveNextPage();
}
