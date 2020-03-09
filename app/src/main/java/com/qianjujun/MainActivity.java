package com.qianjujun;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.Router;
import com.hello7890.adapter.RecyclerViewAdapter;
import com.hello7890.adapter.decoration.ViewModuleItemDecoration;
import com.qianjujun.frame.base.BetterBaseActivity;
import com.qianjujun.frame.base.BetterModuleFragment;
import com.qianjujun.router.RouterPath;
import com.qianjujun.vm.RouterBean;
import com.qianjujun.vm.SimpleStringVm;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BetterBaseActivity implements RouterPath {
    @Override
    protected Fragment onCreateFragment() {
        return new MainFragment();
    }


    public static class MainFragment extends BetterModuleFragment {
        private SimpleStringVm<RouterBean> textVm = new SimpleStringVm();

        @Override
        protected void initModule(RecyclerView recyclerView, View contentView) {
            recyclerView.setAdapter(new RecyclerViewAdapter(textVm));
            recyclerView.addItemDecoration(new ViewModuleItemDecoration(textVm, 2).setDividerColor(Color.parseColor("#f2f2f2")));
            textVm.setList(createData());
            textVm.setOnModuleItemClickListener((routerBean, dataPosition, layoutPosition) -> Router.buildFragment(routerBean.getRouter()).navigation(mActivity));
        }


        private List<RouterBean> createData() {
            List<RouterBean> routers = new ArrayList<>();
            routers.add(new RouterBean("分割线", PATH_TEST_DIVIDER));
            routers.add(new RouterBean("分组列表", PATH_TEST_MODULE));
            routers.add(new RouterBean("ViewPage2", PATH_TEST_PAGE));
            routers.add(new RouterBean("状态VM",PATH_TEST_STATE_VM));
            routers.add(new RouterBean("多类型Vm，复用",PATH_TEST_MORE_VM));
            routers.add(new RouterBean("wrapVm",PATH_TEST_WRAP_VM));
            return routers;
        }
    }




}
