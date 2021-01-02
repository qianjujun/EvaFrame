package com.qianjujun.vm;

import android.view.ViewGroup;

import com.hello7890.adapter.GroupViewModule;
import com.qianjujun.ColorUtils;
import com.qianjujun.R;
import com.qianjujun.databinding.VmChildBinding;
import com.qianjujun.databinding.VmGroupBottomBinding;
import com.qianjujun.databinding.VmGroupTopBinding;
import com.qianjujun.frame.utils.ToastUtils;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/3/2 16:23
 * @describe
 */
public class GroupVm extends GroupViewModule<Child,Group> {
    private static final String TAG = "GroupVm";

    public GroupVm(){
        setOnChildItemClickListener((child, group, groupIndex, childIndex, dataPosition) -> ToastUtils.showWarning("], groupIndex = [" + groupIndex + "], childIndex = [" + childIndex + "], dataPosition = [" + dataPosition + "]"+child.getText()));
    }



    @Override
    protected ChildHolder<?> onCreateChildViewHolder(ViewGroup parent, int viewType) {
        return new ChildHolder<VmChildBinding>(R.layout.vm_child,parent) {

            @Override
            protected void onBindData(VmChildBinding dataBing, Group group, Child child, int groupIndex, int childIndex, int dataPosition,int adapterPosition) {
                dataBing.tvText.setText(child.getText());
                dataBing.tvText.setBackgroundColor(ColorUtils.getColor(dataPosition));
            }
        };
    }

    @Override
    protected GroupHolder<?> onCreateGroupTopViewHolder(ViewGroup parent, int viewType) {
        return new GroupTopHolder(parent);
    }

    @Override
    protected GroupHolder<?> onCreateGroupBottomViewHolder(ViewGroup parent, int viewType) {

        if(viewType==0){
            return super.onCreateGroupBottomViewHolder(parent,viewType);
        }

        return new GroupHolder<VmGroupBottomBinding>(R.layout.vm_group_bottom,parent) {

            @Override
            protected void onBindData(VmGroupBottomBinding dataBing, Group group, int groupIndex, int dataPosition, int adapterPosition, boolean expend) {
                mDataBinding.view.setAnimation("cart.json");
            }



            @Override
            public void onViewAttachedToWindow() {
                super.onViewAttachedToWindow();
//                ObjectAnimator animator = ObjectAnimator.ofFloat(mDataBinding.text,"scaleX",1f,0.5f,1f);
//                animator.setDuration(800);
//                animator.start();
                if(!mDataBinding.view.isAnimating()){
                    mDataBinding.view.playAnimation();
                }

            }

            public void onViewDetachedFromWindow() {
                super.onViewDetachedFromWindow();
                mDataBinding.view.pauseAnimation();
            }
        };
    }

    @Override
    public boolean isStickyItem(int dataPosition) {
        return getDataType(dataPosition)==DATA_TYPE_GROUP_TOP;
    }





    @Override
    public int getChildSpanCount() {
        return 2;
    }

    @Override
    protected int getBottomViewType(int groupPosition) {
        Group group = getGroup(groupPosition);
        if(group.getChildSize()<10){//没有加载更多
            return 0;
        }else {
            return 1;
        }

    }

    public class GroupTopHolder extends GroupHolder<VmGroupTopBinding>{

        public GroupTopHolder( ViewGroup container) {
            super(R.layout.vm_group_top, container);
        }

        private Group group;

        @Override
        protected void onBindData(VmGroupTopBinding dataBing, Group group, int groupIndex, int dataPosition, int adapterPosition, boolean expend) {
            dataBing.text.setText(group.getText()+"   adapterPosition:"+adapterPosition);
            mDataBinding.ivArrow.setImageResource(R.mipmap.icon_arrow_down);
            changeExpendImage(mDataBinding.ivArrow,expend);
            this.group = group;
        }

        @Override
        protected void onGroupExpendChange(VmGroupTopBinding dataBing, Group group, int groupIndex, int adapterPosition,boolean expend) {
            changeExpendImageAnim(dataBing.ivArrow,expend);
        }

        public String getText(){
            return group!=null?group.getText():"";
        }
    }

}
