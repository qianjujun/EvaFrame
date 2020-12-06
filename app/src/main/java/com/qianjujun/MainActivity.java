package com.qianjujun;

import android.graphics.Color;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.Router;
import com.hello7890.adapter.RecyclerViewAdapter;
import com.hello7890.adapter.decoration.ViewModuleItemDecoration;
import com.hello7890.adapter.listener.OnModuleItemClickListener;
import com.hello7890.album.MediaOptions;
import com.hello7890.album.MediaTool;
import com.qianjujun.frame.base.BetterBaseActivity;
import com.qianjujun.frame.base.BetterModuleFragment;
import com.qianjujun.frame.permission.IPermission;
import com.qianjujun.frame.permission.PermissionBean;
import com.qianjujun.frame.permission.PermissionCallBack;
import com.qianjujun.frame.permission.PermissionUtils;
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
            textVm.setOnModuleItemClickListener(new OnModuleItemClickListener<RouterBean>() {
                @Override
                public void onModuleItemClick(RouterBean routerBean, int dataPosition, int adapterPosition) {
                    if(routerBean.getRouter().contains("album")){
                        PermissionUtils.requestPermission(mActivity, PermissionBean.create("").addItem(IPermission.WRITE_EXTERNAL_STORAGE),
                                new PermissionCallBack() {
                                    @Override
                                    public void onPermissionGranted() {
                                        MediaTool.toSelectAlbum(mActivity, MediaOptions.build(1).setMax(1).setMediaType(MediaOptions.TYPE_VIDEO));
                                    }
                                });

                    }else {
                        Router.buildFragment(routerBean.getRouter()).navigation(mActivity);
                    }

                }
            });
        }


        private List<RouterBean> createData() {
            List<RouterBean> routers = new ArrayList<>();
            routers.add(new RouterBean("测试网页",PATH_WEB));
            routers.add(new RouterBean("仿朋友圈",PATH_TEST_FRIEND));
            routers.add(new RouterBean("分割线", PATH_TEST_DIVIDER));
            routers.add(new RouterBean("分组列表", PATH_TEST_MODULE));
            routers.add(new RouterBean("ViewPage2", PATH_TEST_PAGE));
            routers.add(new RouterBean("状态VM",PATH_TEST_STATE_VM));
            routers.add(new RouterBean("多类型Vm，复用",PATH_TEST_MORE_VM));
            routers.add(new RouterBean("wrapVm",PATH_TEST_WRAP_VM));
            routers.add(new RouterBean("相册","/app/album"));
            return routers;
        }
    }




}
