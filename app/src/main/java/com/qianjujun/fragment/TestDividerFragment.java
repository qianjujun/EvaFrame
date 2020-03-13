package com.qianjujun.fragment;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.hello7890.adapter.RecyclerViewAdapter;
import com.hello7890.adapter.decoration.BackgroundDecoration;
import com.hello7890.adapter.decoration.ViewModuleItemDecoration;
import com.qianjujun.TestData;
import com.qianjujun.frame.base.BetterModuleFragment;
import com.qianjujun.vm.SimpleTextVm;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

import static com.qianjujun.router.RouterPath.PATH_TEST_DIVIDER;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/3/3 13:54
 * @describe
 */
@Route(path = PATH_TEST_DIVIDER)
public class TestDividerFragment extends BetterModuleFragment {
    private SimpleTextVm simpleTextVm = new SimpleTextVm(Color.parseColor("#336699"), Color.BLACK).setColumnNum(1);
    private SimpleTextVm grayTextVm = new SimpleTextVm(Color.parseColor("#f2f2f2"), Color.BLACK).setColumnNum(4);
    private Paint paint,paintGray;

    @Override
    protected void initModule(RecyclerView recyclerView, View contentView) {
        paint = new Paint();
        paint.setColor(Color.WHITE);

        paintGray = new Paint();
        paintGray.setColor(Color.parseColor("#f2f2f2"));

        recyclerView.setBackgroundColor(Color.parseColor("#336699"));

        recyclerView.setLayoutManager(new GridLayoutManager(mActivity,4));

        //设置adapter 动画
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(grayTextVm,simpleTextVm);
        ScaleInAnimationAdapter adapter1 = new ScaleInAnimationAdapter(adapter);
        adapter1.setFirstOnly(false);
        recyclerView.setAdapter(adapter1);

        //设置缓存池
        RecyclerView.RecycledViewPool pool = new RecyclerView.RecycledViewPool();
        pool.setMaxRecycledViews(1000,21);
        recyclerView.setRecycledViewPool(pool);




        recyclerView.addItemDecoration(new BackgroundDecoration(grayTextVm,30,50,2){
            @Override
            protected void onDrawViewModuleBackground(Canvas canvas, RectF rectF,boolean top,boolean bottom) {
                DrawUtils.drawRoundRect(canvas,paint,rectF,20,top,bottom);
            }
        });


        recyclerView.addItemDecoration(new BackgroundDecoration(simpleTextVm,30,50,2){
            @Override
            protected void onDrawViewModuleBackground(Canvas canvas, RectF rectF,boolean top,boolean bottom) {

                DrawUtils.drawRoundRect(canvas,paintGray,rectF,20,top,bottom);
            }
        });



        grayTextVm.setList(TestData.createTestStringList(16));
        simpleTextVm.setList(TestData.createTestStringList(25));

    }





}
