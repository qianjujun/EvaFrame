package com.qianjujun;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.Router;
import com.qianjujun.frame.adapter.OnModuleItemClickListener;
import com.qianjujun.frame.adapter.RecyclerViewAdapter;
import com.qianjujun.frame.adapter.ViewModuleItemDecoration;
import com.qianjujun.frame.base.BetterBaseActivity;
import com.qianjujun.frame.base.BetterModuleFragment;
import com.qianjujun.frame.base.BetterSimpleFragment;
import com.qianjujun.frame.utils.ScrrenUtil;
import com.qianjujun.vm.RouterBean;
import com.qianjujun.vm.SimpleStringVm;
import com.qianjujun.vm.SimpleTextVm;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BetterBaseActivity implements RouterPath {
    @Override
    protected Fragment onCreateFragment() {
        return new MainFragment();
    }

    //    Drawable tintDrawable(Drawable drawable, ColorStateList colors) {
//        Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
//        DrawableCompat.setHotspot(wrappedDrawable,10,10);
//        ColorFilter colorFilter = DrawableCompat.getColorFilter(wrappedDrawable);
//        wrappedDrawable = wrappedDrawable.mutate();
//        DrawableCompat.setTintList(wrappedDrawable, colors);
//        DrawableCompat.setTintMode(wrappedDrawable, PorterDuff.Mode.SRC_ATOP);
//        int width = wrappedDrawable.getMinimumWidth();
//        int height = wrappedDrawable.getMinimumHeight();
//
//        return wrappedDrawable;
//    }


    @Override
    protected void onCreateView(@Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
//                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);//
//
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
//            window.setNavigationBarColor(Color.TRANSPARENT);
        }


        super.onCreateView(savedInstanceState);
        ScrrenUtil.setStatusBar(this, false, false);
    }





    public static class MainFragment extends BetterModuleFragment {
        private SimpleStringVm<RouterBean> textVm = new SimpleStringVm();

        @Override
        protected void initModule(RecyclerView recyclerView, View contentView) {
            recyclerView.setBackgroundColor(Color.RED);
            recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
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
            return routers;
        }
    }




}
