package com.hello7890.album.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hello7890.adapter.decoration.ViewModuleItemDecoration;
import com.hello7890.adapter.listener.OnModuleItemClickListener;
import com.hello7890.album.MediaOptions;
import com.hello7890.album.R;
import com.hello7890.album.bean.Album;
import com.hello7890.album.bean.LocalData;
import com.hello7890.album.bean.Media;
import com.hello7890.album.loader.MediaLoader;
import com.hello7890.album.vm.VmMedia;
import com.qianjujun.frame.base.BetterListFragment;
import com.qianjujun.frame.data.IData;
import com.qianjujun.frame.utils.PathUtils;
import com.qianjujun.frame.views.title.TitleConfig;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

public class SelectMediaFragment extends BetterListFragment<Media, VmMedia> implements OnModuleItemClickListener<Media> {
    private MediaLoader mMediaLoader;
    @Override
    protected void initArgs(@NonNull Bundle args) {
        super.initArgs(args);
        MediaOptions options = args.getParcelable("options");
        Album album = args.getParcelable("album");
        mMediaLoader = MediaLoader.getInstance(mActivity,options,album);
    }

    @Override
    protected void initModule(RecyclerView recyclerView, View contentView) {
        super.initModule(recyclerView, contentView);
        recyclerView.addItemDecoration(new ViewModuleItemDecoration(mViewModule,2).setDividerColor(0xfff2f2f2));
        mViewModule.setOnModuleItemClickListener(this);

    }

    @Override
    protected TitleConfig getTitleConfig() {
        return TitleConfig.build().leftImage(R.mipmap.icon_back_black).centerText(R.string.select_media).create().setDividerVisable(true);
    }

    @Override
    protected VmMedia createVm() {
        return new VmMedia();
    }

    @Override
    protected Flowable<? extends IData<List<Media>>> createRequest() {
        return Flowable.just(mMediaLoader).map(new Function<MediaLoader, IData<List<Media>>>() {
            @Override
            public IData<List<Media>> apply(MediaLoader mediaLoader) throws Exception {
                List<Media> result = mediaLoader.load();
                return LocalData.success(result);
            }
        });
    }


    @Override
    protected RecyclerView.LayoutManager createLayoutManger() {
        return new GridLayoutManager(mActivity,4);
    }

    @Override
    public void onModuleItemClick(Media media, int dataPosition, int adapterPosition) {
        media.setPath(PathUtils.getPath(mActivity,media.getUri()));
        Intent data = new Intent();
        data.putExtra("data",media);
        mActivity.setResult(Activity.RESULT_OK,data);
        mActivity.finish();
    }
}
