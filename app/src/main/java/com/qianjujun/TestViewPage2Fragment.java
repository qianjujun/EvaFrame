package com.qianjujun;

import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager2.widget.ViewPager2;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hello7890.adapter.RecyclerViewAdapter;
import com.qianjujun.frame.base.BetterSimpleFragment;
import com.qianjujun.router.RouterPath;
import com.qianjujun.vm.PageVm;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/3/4 15:11
 * @describe
 */

@Route(path= RouterPath.PATH_TEST_PAGE)
public class TestViewPage2Fragment extends BetterSimpleFragment {
    private ViewPager2 viewPager2;
    @Override
    protected View getContentView(ViewGroup container) {
        return viewPager2 = new ViewPager2(mActivity);
    }

    @Override
    protected void initView(View contentView) {
        PageVm pageVm = new PageVm();
        viewPager2.setAdapter(new RecyclerViewAdapter(pageVm));
        pageVm.setList(TestData.createTestStringList(7));
    }
}
