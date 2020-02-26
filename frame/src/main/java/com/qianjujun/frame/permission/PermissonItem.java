package com.qianjujun.frame.permission;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/1/19 14:46
 * @describe
 */
public class PermissonItem {
    private String permissons;
    private String refuseMessage;

    public PermissonItem(String permissons) {
        this.permissons = permissons;
    }

    public PermissonItem(String permissons, String refuseMessage) {
        this.permissons = permissons;
        this.refuseMessage = refuseMessage;
    }

    public String getPermissons() {
        return permissons;
    }

    public void setPermissons(String permissons) {
        this.permissons = permissons;
    }

    public String getRefuseMessage() {
        return refuseMessage;
    }

    public void setRefuseMessage(String refuseMessage) {
        this.refuseMessage = refuseMessage;
    }
}
