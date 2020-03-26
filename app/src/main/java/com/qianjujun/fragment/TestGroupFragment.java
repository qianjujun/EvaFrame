package com.qianjujun.fragment;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hello7890.adapter.RecyclerViewAdapter;
import com.hello7890.adapter.decoration.GroupViewModuleItemDecoration;
import com.qianjujun.R;
import com.qianjujun.frame.base.BetterModuleFragment;
import com.qianjujun.vm.Group;
import com.qianjujun.vm.GroupVm;
import com.qianjujun.vm.ImageVm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.qianjujun.router.RouterPath.PATH_TEST_MODULE;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/1/20 16:18
 * @describe
 */

@Route(path = PATH_TEST_MODULE)
public class TestGroupFragment extends BetterModuleFragment {

    private static int index = 0;


    //分组


    GroupVm groupVm = new GroupVm();
    RecyclerViewAdapter adapter;
    private GroupViewModuleItemDecoration groupViewModuleItemDecoration;

    @Override
    protected void initModule(RecyclerView recyclerView,View contentView) {
        index = 0;


        adapter = new RecyclerViewAdapter(new ImageVm(),groupVm);
        recyclerView.setLayoutManager(new GridLayoutManager(mActivity,2));
        recyclerView.setAdapter(adapter);

        recyclerView.setBackgroundColor(Color.parseColor("#dddddd"));

        //recyclerView.addItemDecoration(new StickyItemDecoration(adapter.getAdapterHelper()));

        recyclerView.addItemDecoration(new GroupViewModuleItemDecoration(groupVm,30,30).setChildColumnNum(2));

        List<Group> list = Group.createTestData();
        groupVm.setExpendable(true);
        groupVm.setList(list);

        final TextView textView = contentView.findViewById(R.id.tv_text);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            int totalDy;
            float maxHeight = getResources().getDimensionPixelOffset(R.dimen.dp240);
            View tempView;

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalDy+=dy;
                Log.d(TAG, "onScrolled() called with: recyclerView = [" + totalDy + "], dx = [" + dx + "], dy = [" + dy + "]");
                float alpha = totalDy/maxHeight;
                if(alpha>1){
                    alpha = 1;
                }
                textView.setAlpha(alpha);



                View view = recyclerView.getChildAt(0);
                if(tempView==view){
                    return;
                }
                tempView = view;
                RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(view);
                int adapterPosition = viewHolder.getAdapterPosition();
                int dataPosition = groupVm.getDataPosition(adapterPosition);
                Group group = groupVm.getItem(dataPosition);
                if(group!=null){
                    textView.setText(group.getText());
                }else {
                    textView.setText("");
                }


//                if(viewHolder instanceof GroupVm.GroupTopHolder){
//                    GroupVm.GroupTopHolder groupTopHolder = (GroupVm.GroupTopHolder) viewHolder;
//                    textView.setText(groupTopHolder.getText());
//                }
            }
        });


        contentView.findViewById(R.id.btn_change_grid).setOnClickListener(this);
        contentView.findViewById(R.id.btn_change_linear).setOnClickListener(this);
        contentView.findViewById(R.id.btn_add_divider).setOnClickListener(this);
        contentView.findViewById(R.id.btn_delete_divider).setOnClickListener(this);


    }


    @Override
    protected int getLayoutId() {
        return R.layout.test_rec;
    }

    List<Integer> createTestData(){
        List<Integer> list = new ArrayList<>();
        for (int i = 0;i<20;i++){
            index++;
            list.add(index);
        }
        return list;
    }

    List<String> createStringTestData(){
        int num = new Random().nextInt(15)+2;
        List<String> list = new ArrayList<>();
        for (int i = 0;i<num;i++){
            index++;
            list.add(index+"");
        }
        return list;
    }

    @Override
    public void onViewClick(View view) {
        switch (view.getId()){
            case R.id.btn_change_linear:
                adapter.getAdapterHelper().changeLayoutManager(mRecyclerView,new LinearLayoutManager(getContext()));
                groupViewModuleItemDecoration.setChildColumnNum(1);
                break;
            case R.id.btn_change_grid:
                groupViewModuleItemDecoration.setChildColumnNum(3);
                adapter.getAdapterHelper().changeLayoutManager(mRecyclerView,new GridLayoutManager(getContext(),3));
                break;
            case R.id.btn_add_divider:
                addItemDecoration();
                break;
            case R.id.btn_delete_divider:
                mRecyclerView.removeItemDecoration(groupViewModuleItemDecoration);
                break;
        }
    }


    private void addItemDecoration(){
        for(int i = 0;i<mRecyclerView.getItemDecorationCount();i++){
            if(mRecyclerView.getItemDecorationAt(i)==groupViewModuleItemDecoration){
                return;
            }
        }
        mRecyclerView.addItemDecoration(groupViewModuleItemDecoration);
    }
}
