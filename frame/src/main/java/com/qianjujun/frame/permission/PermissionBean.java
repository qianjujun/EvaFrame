package com.qianjujun.frame.permission;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/1/19 14:52
 * @describe
 */
public class PermissionBean {
    private List<PermissonItem> permissionItems = new ArrayList<>();
    private String requestMessage;



    public String getRequestMessage() {
        return requestMessage;
    }

    public void setRequestMessage(String requestMessage) {
        this.requestMessage = requestMessage;
    }

    public static PermissionBean create(String requestMessage){
        PermissionBean permissionBean = new PermissionBean();
        permissionBean.setRequestMessage(requestMessage);
        return permissionBean;
    }

    public PermissionBean addItem(PermissonItem permissonItem){
        permissionItems.add(permissonItem);
        return this;
    }

    public PermissionBean addItem(String permission){
        permissionItems.add(new PermissonItem(permission));
        return this;
    }

    public List<String> getPermissions(){
        List<String> list = new ArrayList<>();
        for(PermissonItem item:permissionItems){
            list.add(item.getPermissons());
        }
        return list;
    }
}
