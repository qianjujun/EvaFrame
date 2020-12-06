package com.hello7890.album.vm;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.hello7890.adapter.vm.SingleDbViewModule;
import com.hello7890.album.R;
import com.hello7890.album.bean.Media;
import com.hello7890.album.databinding.VmMediaBinding;

import java.text.DecimalFormat;

public class VmMedia extends SingleDbViewModule<Media, VmMediaBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.vm_media;
    }

    @Override
    protected void onBindData(VmMediaBinding dataBinding, Media media, int dataPosition, int layoutPosition) {
        Glide.with(dataBinding.ivCover)
                .load(media.getUri())
                .apply(new RequestOptions()
                        .placeholder(R.mipmap.sdk_icon_error)
                        .override(dataBinding.ivCover.getWidth(), dataBinding.ivCover.getHeight())
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        //.centerCrop()
                        .dontAnimate())
                .transition(DrawableTransitionOptions.withCrossFade())
                .thumbnail(0.5f)
                .into(dataBinding.ivCover);

        dataBinding.tvTime.setText(formatMs(media.getDuration()));
        dataBinding.tvFormat.setText(media.getMimeType());
        dataBinding.tvSize.setText(formatSize(media.getSize()));

    }


    @Override
    public int getSpanCount() {
        return 4;
    }


    public static String formatMs(long time){
        int s = (int) (time/1000);
        int mm = s/60;
        int ss = s%60;

        StringBuilder sb = new StringBuilder();
        sb.append(mm<10?"0"+mm:mm);
        sb.append(":");
        sb.append(ss<10?"0"+ss:ss);
        return sb.toString();
    }


    public static String formatSize(long size){
        //获取到的size为：1705230
        int GB = 1024 * 1024 * 1024;//定义GB的计算常量
        int MB = 1024 * 1024;//定义MB的计算常量
        int KB = 1024;//定义KB的计算常量
        DecimalFormat df = new DecimalFormat("0.00");//格式化小数
        String resultSize = "";
        if (size / GB >= 1) {
            //如果当前Byte的值大于等于1GB
            resultSize = df.format(size / (float) GB) + "GB";
        } else if (size / MB >= 1) {
            //如果当前Byte的值大于等于1MB
            resultSize = df.format(size / (float) MB) + "MB";
        } else if (size / KB >= 1) {
            //如果当前Byte的值大于等于1KB
            resultSize = df.format(size / (float) KB) + "KB";
        } else {
            resultSize = size + "B";
        }
        return resultSize;
    }

}
