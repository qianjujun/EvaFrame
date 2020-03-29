package com.qianjujun.fragment;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hello7890.adapter.RecyclerViewAdapter;
import com.hello7890.adapter.data.BackgroundBuild;
import com.hello7890.adapter.data.Group2Data;
import com.hello7890.adapter.decoration.BackgroundGroup2Decoration;
import com.hello7890.adapter.vm.Group2ViewModule;
import com.qianjujun.TestData;
import com.qianjujun.data.FriendData;
import com.qianjujun.frame.base.BetterModuleFragment;
import com.qianjujun.frame.utils.ScrrenUtil;
import com.qianjujun.vm2.TestTwoGroupVm;

import static com.qianjujun.router.RouterPath.PATH_TEST_FRIEND;

@Route(path = PATH_TEST_FRIEND)
public class TestFriendFragment extends BetterModuleFragment {
    private Paint paint;
    @Override
    protected void initModule(RecyclerView recyclerView, View contentView) {
        paint = new Paint();
        paint.setColor(Color.parseColor("#f2f2f2"));
        TestTwoGroupVm groupVm = new TestTwoGroupVm();
        recyclerView.setAdapter(new RecyclerViewAdapter(groupVm));
        int left = ScrrenUtil.dp2px(70);
        int right = ScrrenUtil.dp2px(15);
        BackgroundGroup2Decoration decoration = new BackgroundGroup2Decoration(groupVm){
            @Override
            protected void onDrawBackground2(Rect rectF, Rect topRectF, Rect bottomRectF, Canvas canvas) {
                canvas.drawRect(rectF,paint);
                Path path = new Path();
                path.moveTo(topRectF.left+20, topRectF.bottom);// 此点为多边形的起点
                path.lineTo(topRectF.left+60, topRectF.bottom);
                path.lineTo(topRectF.left+40, topRectF.bottom-20);
                path.close(); // 使这些点构成封闭的多边形
                canvas.drawPath(path, paint);
                Log.d("TestFriendFragment", "rectF = [" + rectF + "], topRectF = [" + topRectF + "], bottomRectF = [" + bottomRectF + "], canvas = [" + "canvas" + "]");
            }

            @Override
            protected void handlerTopChild1(@NonNull Rect outRect, int groupPosition) {
                FriendData friendData = groupVm.getGroup(groupPosition);
                if(friendData!=null&&friendData.getLink()==null&& TextUtils.isEmpty(friendData.getContent())){
                    outRect.top -= 60;
                }
            }
        };
        BackgroundBuild build1 = new BackgroundBuild().setDivider(10).setOuterLeft(left).setOuterRight(right);
        BackgroundBuild build2 = new BackgroundBuild().setDivider(10).setOuterLeft(left).setOuterRight(right)
                .setInnerTop(50).setInnerLeftAndRight(10);
        decoration.setBackgroundBuild1(build1).setBackgroundBuild2(build2);





        recyclerView.addItemDecoration(decoration);
        groupVm.setList(TestData.createFriendData());
    }


    @Override
    protected RecyclerView.LayoutManager createLayoutManger() {
        return new GridLayoutManager(mActivity, 3);
    }
}
