package com.qianjujun.vm;

import com.hello7890.adapter.vm.SingleDbViewModule;
import com.qianjujun.R;
import com.qianjujun.databinding.VmImageBinding;
import com.qianjujun.frame.utils.LoadImageUtil;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/3/26 11:37
 * @describe
 */
public class ImageVm extends SingleDbViewModule<Integer, VmImageBinding> {

    public ImageVm(){
        setData(R.mipmap.android_test);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.vm_image;
    }

    @Override
    protected void onBindData(VmImageBinding dataBinding, Integer s, int dataPosition, int layoutPosition) {
        dataBinding.image.setImageResource(s);
    }
}
