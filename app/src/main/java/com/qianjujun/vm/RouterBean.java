package com.qianjujun.vm;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/3/4 14:57
 * @describe
 */
public class RouterBean implements IString{
    private String title;
    private String router;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRouter() {
        return router;
    }

    public void setRouter(String router) {
        this.router = router;
    }

    public RouterBean(String title, String router) {
        this.title = title;
        this.router = router;
    }

    @Override
    public String getStringText() {
        return title;
    }
}
