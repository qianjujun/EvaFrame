package com.qianjujun.vm;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

import com.qianjujun.R;
import com.qianjujun.databinding.VmChildBinding;
import com.qianjujun.databinding.VmGroupTopBinding;
import com.qianjujun.frame.adapter.GroupViewModule3;
import com.qianjujun.frame.utils.ToastUtils;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/3/2 16:23
 * @describe
 */
public class GroupVm extends GroupViewModule3<Child,Group> {

    public GroupVm(){
        setOnChildItemClickListener((child, group, groupIndex, childIndex, dataPosition) -> ToastUtils.showWarning("], groupIndex = [" + groupIndex + "], childIndex = [" + childIndex + "], dataPosition = [" + dataPosition + "]"+child.getText()));
    }



    @Override
    protected ChildHolder<?> onCreateChildViewHolder(ViewGroup parent, int viewType) {
        return new ChildHolder<VmChildBinding>(R.layout.vm_child,parent) {

            @Override
            protected void onBindData(VmChildBinding dataBing, Group group, Child child, int groupIndex, int childIndex, int dataPosition) {
                dataBing.tvText.setText(child.getText());
            }
        };
    }

    @Override
    protected GroupHolder<?> onCreateGroupTopViewHolder(ViewGroup parent, int viewType) {
        return new GroupHolder<VmGroupTopBinding>(R.layout.vm_group_top,parent) {

            @Override
            protected void onBindData(VmGroupTopBinding dataBing, Group group, int groupIndex, int dataPosition,boolean expend) {
                dataBing.text.setText(group.getText());
                mDataBinding.ivArrow.setImageResource(R.mipmap.icon_arrow_down);
                changeExpendImage(mDataBinding.ivArrow,expend);
            }

            @Override
            protected void onGroupExpendChange(VmGroupTopBinding dataBing, Group group, int groupIndex, boolean expend) {
                changeExpendImageAnim(dataBing.ivArrow,expend);
            }
        };
    }

    @Override
    protected GroupHolder<?> onCreateGroupBottomViewHolder(ViewGroup parent, int viewType) {
        return new GroupHolder<VmGroupTopBinding>(R.layout.vm_group_top,parent) {

            @Override
            protected void onBindData(VmGroupTopBinding dataBing, Group group, int groupIndex, int dataPosition,boolean expend) {
                dataBing.ivArrow.setVisibility(View.GONE);
                dataBing.text.setText("底部");
                dataBing.text.setBackgroundColor(Color.BLACK);
            }
        };
    }

    @Override
    public boolean isStickyItem(int dataPosition) {
        return getDataType(dataPosition)==DATA_TYPE_GROUP_TOP;
    }


    @Override
    protected int getSpanCount(int dataPosition) {
        return getDataType(dataPosition)==DATA_TYPE_CHILD?3:1;
    }
}
