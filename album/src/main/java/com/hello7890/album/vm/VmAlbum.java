package com.hello7890.album.vm;

import com.bumptech.glide.Glide;
import com.hello7890.adapter.DbViewModule;
import com.hello7890.album.R;
import com.hello7890.album.bean.Album;
import com.hello7890.album.databinding.VmAlbumBinding;

public class VmAlbum extends DbViewModule<Album, VmAlbumBinding> {




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
