package com.hello7890.adapter;

/**
 * 描述 : 说俩句呗
 * 作者 : qianjujun
 * 时间 : 2018/5/1913:57
 * 邮箱 : qianjujun@163.com
 */

public interface ViewType {
    /**
     * viewModule 隔离值
     */
    int FLAG_VIEW_TYPE = 1000;





    int EMPTY_VIEW_TYPE = 999;

    int FAIL_VIEW_TYPE = 998;

    int LOADING_VIEW_TYPE = 997;

    int SPACE_VIEW_TYPE = 996;

    /**
     * 数据view的type类型最小值
     */
    int MIN_NORMAL_VIEW_TYPE = 0;
    /**
     * 数据view的type类型最大值
     */
    int MAX_NORMAL_VIEW_TYPE = 900;

    /**
     * group数据view的type类型最大值
     */
    int MAX_GROUP_VIEW_TYPE = 299;


    //全局空数据item类型
    int GLOBE_NULL_DATA_VIEW_TYPE = -1;
}
