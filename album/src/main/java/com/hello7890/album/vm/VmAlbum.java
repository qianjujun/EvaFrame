package com.hello7890.album.vm;

import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.hello7890.adapter.BaseViewHolder;
import com.hello7890.adapter.data.ModuleState;
import com.hello7890.adapter.vm.SingleDbViewModule;
import com.hello7890.album.MediaTool;
import com.hello7890.album.R;
import com.hello7890.album.bean.Album;
import com.hello7890.album.databinding.VmAlbumBinding;

public class VmAlbum extends SingleDbViewModule<Album, VmAlbumBinding> {




    @Override
    protected int getLayoutId() {
        return R.layout.vm_album;
    }

    @Override
    protected void onBindData(VmAlbumBinding dataBinding, Album album, int dataPosition, int layoutPosition) {
        //LoadImageUtil.loadImage(dataBinding.ivCover,album.getCoverUri());
        Glide.with(dataBinding.ivCover)
                .load(album.getCoverUri())
                .thumbnail(0.7f)
                .into(dataBinding.ivCover);

        dataBinding.tvName.setText(album.getDisplayName());
        dataBinding.tvNum.setText(album.getCount()+"个视频");
    }


}
