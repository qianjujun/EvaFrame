package com.qianjujun.vm2;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.hello7890.adapter.listener.OnModuleItemClickListener;
import com.hello7890.adapter.Group2ViewModule;
import com.qianjujun.R;
import com.qianjujun.data.FriendData;
import com.qianjujun.databinding.VhFraiendCommentBinding;
import com.qianjujun.databinding.VhFriendImage1Binding;
import com.qianjujun.databinding.VhFriendImage3Binding;
import com.qianjujun.databinding.VhFriendTopBinding;
import com.qianjujun.databinding.VhFrinedBottom1Binding;
import com.qianjujun.frame.utils.LoadImageUtil;
import com.qianjujun.frame.utils.ToastUtils;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/3/28 18:22
 * @describe
 */
public class TestTwoGroupVm extends Group2ViewModule<String, FriendData.Comment, FriendData> {

    public TestTwoGroupVm() {
        setOnModuleItemClickListener(new OnModuleItemClickListener<FriendData>() {
            @Override
            public void onModuleItemClick(FriendData friendData, int dataPosition, int adapterPosition) {
                ToastUtils.showSuccess("dataPosition:" + dataPosition + " adapterPosition:" + adapterPosition);
                Log.d("TestTwoGroupVm", "onModuleItemClick() called with: friendData = [" + "friendData" + "], dataPosition = [" + dataPosition + "], adapterPosition = [" + adapterPosition + "]");
            }
        });
    }


    @Override
    protected GroupViewHolder<? extends ViewDataBinding> onCreateGroupTopViewHolder(ViewGroup parent, int viewType) {
        return new GroupViewHolder<VhFriendTopBinding>(R.layout.vh_friend_top, parent) {

            @Override
            protected void onBindData(VhFriendTopBinding dataBing, FriendData group, int groupIndex, int dataPosition, int adapterPosition) {
                dataBing.setData(group);
                LoadImageUtil.loadImage(dataBing.ivAvatar, group.getUser().getAvatar());
                if (group.getLink() != null) {
                    LoadImageUtil.loadImage(dataBing.ivLink, group.getLink().getImage());
                }
            }
        };
    }

    @Override
    protected Child1ViewHolder<? extends ViewDataBinding> onCreateChild1ViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new Child1ViewHolder<VhFriendImage3Binding>(R.layout.vh_friend_image3, parent) {

                @Override
                protected void onBindData(VhFriendImage3Binding dataBing, FriendData group, String child, int groupIndex, int childIndex, int dataPosition, int adapterPosition) {
                    LoadImageUtil.loadImage(dataBing.image, child);
                }
            };
        }
        return new Child1ViewHolder<VhFriendImage1Binding>(R.layout.vh_friend_image1, parent) {


            @Override
            protected void onBindData(VhFriendImage1Binding dataBing, FriendData group, String child, int groupIndex, int childIndex, int dataPosition, int adapterPosition) {
                resetImageSize(dataBing.image, group.width, group.height);
                Glide.with(dataBing.image)
                        .asBitmap()
                        .load(child)
                        .into(new CustomTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                group.width = resource.getWidth();
                                group.height = resource.getHeight();
                                resetImageSize(dataBing.image, resource.getWidth(), resource.getHeight());
                                dataBing.image.setImageBitmap(resource);
                            }

                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {

                            }
                        });
            }
        };
    }


    private void resetImageSize(ImageView imageView, int w, int h) {
        if (w == 0 || h == 0) {
            return;
        }
        ViewGroup.LayoutParams vl = imageView.getLayoutParams();
        if (vl.width == w && vl.height == h) {
            return;
        }
        vl.width = w;
        vl.height = h;
        imageView.setLayoutParams(vl);
    }

    @Override
    protected Child2ViewHolder<? extends ViewDataBinding> onCreateChild2ViewHolder(ViewGroup parent, int viewType) {
        return new Child2ViewHolder<VhFraiendCommentBinding>(R.layout.vh_fraiend_comment, parent) {


            @Override
            protected void onBindData(VhFraiendCommentBinding dataBing, FriendData group, FriendData.Comment child, int groupIndex, int childIndex, int dataPosition, int adapterPosition) {
                dataBing.tvContent.setText(child.getContentStr());
            }
        };
    }


    @Override
    protected GroupViewHolder<? extends ViewDataBinding> onCreateChild1BottomViewHolder(ViewGroup parent, int viewType) {
        return new GroupViewHolder<VhFrinedBottom1Binding>(R.layout.vh_frined_bottom1, parent) {
            @Override
            protected void onBindData(VhFrinedBottom1Binding dataBing, FriendData group, int groupIndex, int dataPosition, int adapterPosition) {

            }
        };
    }

    @Override
    protected int getChild1ViewType(FriendData group, int groupPosition, int child1Position) {
        if (group.getChild1Size() == 1) {
            return 1;
        }
        return 0;
    }


    @Override
    public int getChild1SpanCount(int groupPosition, FriendData group) {
        return group.getChild1Size() > 1 ? 3 : 1;
    }


    @Override
    protected int getChild2ViewType(FriendData group, int groupPosition, int child1Position) {
        return super.getChild2ViewType(group, groupPosition, child1Position);
    }

    @Override
    public int getChild2SpanCount(int groupPosition, FriendData group) {
        return super.getChild2SpanCount(groupPosition, group);
    }
}
