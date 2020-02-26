package com.qianjujun;

import android.Manifest;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.qianjujun.databinding.FragmentTestPermissionBinding;
import com.qianjujun.frame.base.BetterDbFragment;
import com.qianjujun.frame.permission.IPermission;
import com.qianjujun.frame.permission.PermissionBean;
import com.qianjujun.frame.permission.PermissionCallBack;
import com.qianjujun.frame.permission.PermissionUtils;
import com.qianjujun.frame.utils.ToastUtils;


import static com.qianjujun.RouterPath.PATH_TEST_PERMISSION;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/1/19 11:30
 * @describe
 */
@Route(path = PATH_TEST_PERMISSION)
public class TestPermissions extends BetterDbFragment<FragmentTestPermissionBinding> {
    public static final String TAG = "TestPermissions";

    @Override
    protected void initView(FragmentTestPermissionBinding dataBinding) {
        dataBinding.setClick(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test_permission;
    }

    @Override
    public void onViewClick(View view) {
        super.onViewClick(view);
        switch (view.getId()){
            case R.id.btn_apply_camera:
                showApplyCamera();

                break;
        }
    }

    public void showApplyCamera(){
        //ToastUtils.showSuccess("申请相机权限");

        PermissionUtils.requestPermission(mActivity, PermissionBean.create("").addItem(IPermission.CAMERA), new PermissionCallBack() {
            @Override
            public void onPermissionGranted() {
                super.onPermissionGranted();
                Log.d(TAG, "onPermissionGranted: ");
            }

            @Override
            public void onPermissionDenied(boolean refuseForever) {
                super.onPermissionDenied(refuseForever);
                Log.d(TAG, "onPermissionDenied() called with: refuseForever = [" + refuseForever + "]");
            }
        });

    }





}
