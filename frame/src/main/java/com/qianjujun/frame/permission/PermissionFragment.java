package com.qianjujun.frame.permission;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.List;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/1/19 15:11
 * @describe
 */
public class PermissionFragment extends Fragment {

    private PermissionCallBack permissionCallBack;
    private PermissionBean permissionBean;

    public PermissionFragment setPermissionBean(PermissionBean permissionBean) {
        this.permissionBean = permissionBean;
        return this;
    }

    public PermissionFragment setPermissionCallBack(PermissionCallBack permissionCallBack) {
        this.permissionCallBack = permissionCallBack;
        return this;
    }

    public static PermissionFragment create(){
        return new PermissionFragment();
    }

    private AppCompatActivity appCompatActivity;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        appCompatActivity = (AppCompatActivity) context;
    }

    private void exit(){
        appCompatActivity.getSupportFragmentManager().beginTransaction().remove(this).commitAllowingStateLoss();
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(permissionBean==null){
            onSuccess();
            return;
        }

        List<String> permissions = permissionBean.getPermissions();
        if (permissions == null||permissions.isEmpty()) {
            onSuccess();
            return;
        }

        boolean isRequestPermission = false;
        if (permissions.contains(IPermission.REQUEST_INSTALL_PACKAGES) && !PermissionUtils.isHasInstallPermission(getActivity())) {
            // 跳转到允许安装未知来源设置页面
            Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, Uri.parse("package:" + getActivity().getPackageName()));
            startActivityForResult(intent, 7777);
            isRequestPermission = true;
        }

        if (permissions.contains(IPermission.SYSTEM_ALERT_WINDOW) && !PermissionUtils.isHasOverlaysPermission(getActivity())) {
            // 跳转到悬浮窗设置页面
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getActivity().getPackageName()));
            startActivityForResult(intent, 7777);
            isRequestPermission = true;
        }

        // 当前必须没有跳转到悬浮窗或者安装权限界面
        if (!isRequestPermission) {
            requestPermission(permissions);
        }
    }

    /**
     * 请求权限
     */
    public void requestPermission(List<String> permissions) {
        if(PermissionUtils.isOverMarshmallow()){
            requestPermissions(permissions.toArray(new String[permissions.size()]), 7777);
        }else {
            onSuccess();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 根据请求码取出的对象为空，就直接返回不处理
        if (permissionCallBack == null) {
            exit();
            return;
        }

        for (int i = 0; i < permissions.length; i++) {

            // 重新检查安装权限
            if (IPermission.REQUEST_INSTALL_PACKAGES.equals(permissions[i])) {
                if (PermissionUtils.isHasInstallPermission(getActivity())) {
                    grantResults[i] = PackageManager.PERMISSION_GRANTED;
                } else {
                    grantResults[i] = PackageManager.PERMISSION_DENIED;
                }
            }

            // 重新检查悬浮窗权限
            if (IPermission.SYSTEM_ALERT_WINDOW.equals(permissions[i])) {
                if (PermissionUtils.isHasOverlaysPermission(getActivity())) {
                    grantResults[i] = PackageManager.PERMISSION_GRANTED;
                } else {
                    grantResults[i] = PackageManager.PERMISSION_DENIED;
                }
            }

            // 重新检查8.0的两个新权限
            if (permissions[i].equals(IPermission.ANSWER_PHONE_CALLS) || permissions[i].equals(IPermission.READ_PHONE_NUMBERS)) {
                // 检查当前的安卓版本是否符合要求
                if (!PermissionUtils.isOverOreo()) {
                    grantResults[i] = PackageManager.PERMISSION_GRANTED;
                }
            }
        }

        // 获取授予权限
        List<String> succeedPermissions = PermissionUtils.getSucceedPermissions(permissions, grantResults);
        // 如果请求成功的权限集合大小和请求的数组一样大时证明权限已经全部授予
        if (succeedPermissions.size() == permissions.length) {
            // 代表申请的所有的权限都授予了
            onSuccess();
        } else {

            // 获取拒绝权限
            List<String> failPermissions = PermissionUtils.getFailPermissions(permissions, grantResults);

            // 检查是否开启了继续申请模式，如果是则检查没有授予的权限是否还能继续申请


            // 代表申请的权限中有不同意授予的，如果有某个权限被永久拒绝就返回true给开发人员，让开发者引导用户去设置界面开启权限
            boolean refuseForever = PermissionUtils.checkMorePermissionPermanentDenied(getActivity(), failPermissions);
            onFail(refuseForever);

        }
    }


    private void onSuccess(){
        if(permissionCallBack!=null){
            permissionCallBack.onPermissionGranted();
        }
        exit();
    }

    private void onFail(boolean refuseForever){
        if(permissionCallBack!=null){
            permissionCallBack.onPermissionDenied(refuseForever);
        }
        exit();
    }
}
