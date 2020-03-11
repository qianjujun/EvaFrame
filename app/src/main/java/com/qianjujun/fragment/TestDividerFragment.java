package com.qianjujun.fragment;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.hello7890.adapter.RecyclerViewAdapter;
import com.hello7890.adapter.decoration.ViewModuleItemDecoration;
import com.qianjujun.TestData;
import com.qianjujun.frame.base.BetterModuleFragment;
import com.qianjujun.vm.SimpleTextVm;

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

    @Override
    protected void initModule(RecyclerView recyclerView, View contentView) {

        recyclerView.setBackgroundColor(Color.YELLOW);
        recyclerView.setLayoutManager(new GridLayoutManager(mActivity,4));
        recyclerView.setAdapter(new RecyclerViewAdapter(grayTextVm));

//        recyclerView.addItemDecoration(new ViewModuleItemDecoration(simpleTextVm, 20)
//                .setDividerColor(Color.YELLOW)
//                .setNoneBottomDivider(false));

        recyclerView.addItemDecoration(new ViewModuleItemDecoration(grayTextVm, 0,60){
            Paint paint = new Paint();

            int radio = 20;
            int left,right,top,bottom;
            @Override
            protected boolean onDrawGridBg(Canvas canvas, View child, int dataPosition, int columnNum, int dividerHeight,int size) {
                paint.setColor(Color.WHITE);

                if(dataPosition==0){
                    //DrawUtils.drawRect(canvas,paint,new RectF(0,0,900,700));
                    left = child.getLeft();
                    top = child.getTop();
                }


                if(dataPosition%columnNum==columnNum-1){
                    right = child.getRight();
                }

                if(dataPosition==size-1){
                    bottom = child.getBottom();
                    DrawUtils.drawRoundRect(canvas,paint,new RectF(left,top,right,bottom),radio);
                }
                return false;
            }
        });


        recyclerView.addItemDecoration(new ViewModuleItemDecoration(grayTextVm,30));

        grayTextVm.setList(TestData.createTestStringList(16));
        simpleTextVm.setList(TestData.createTestStringList(25));

    }





}
