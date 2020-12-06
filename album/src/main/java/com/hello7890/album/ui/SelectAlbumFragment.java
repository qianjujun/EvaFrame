package com.hello7890.album.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.hello7890.adapter.decoration.ViewModuleItemDecoration;
import com.hello7890.adapter.listener.OnModuleItemClickListener;
import com.hello7890.album.MediaOptions;
import com.hello7890.album.MediaTool;
import com.hello7890.album.R;
import com.hello7890.album.bean.Album;
import com.hello7890.album.bean.LocalData;
import com.hello7890.album.bean.Media;
import com.hello7890.album.loader.AlbumLoader;
import com.hello7890.album.vm.VmAlbum;
import com.qianjujun.frame.base.BetterListFragment;
import com.qianjujun.frame.data.IData;
import com.qianjujun.frame.views.title.TitleConfig;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

public class SelectAlbumFragment extends BetterListFragment<Album, VmAlbum> implements OnModuleItemClickListener<Album> {

    private MediaOptions options;
    @Override
    protected void initArgs(@NonNull Bundle args) {
        super.initArgs(args);
        options = args.getParcelable("options");
    }


    @Override
    protected TitleConfig getTitleConfig() {
        return TitleConfig.build().leftImage(R.mipmap.icon_back_black).centerText(R.string.select_album).create().setDividerVisable(true);
    }

    @Override
    protected void initModule(RecyclerView recyclerView, View contentView) {
        super.initModule(recyclerView, contentView);
        recyclerView.addItemDecoration(new ViewModuleItemDecoration(mViewModule,2).setDividerColor(0xfff2f2f2));
        mViewModule.setOnModuleItemClickListener(this);
    }

    @Override
    protected VmAlbum createVm() {
        return new VmAlbum();
    }

    @Override
    protected Flowable<? extends IData<List<Album>>> createRequest() {
        AlbumLoader loader = AlbumLoader.getInstance(mActivity,options);

        return Flowable.just(loader)
                .map((Function<AlbumLoader, IData<List<Album>>>) albumLoader -> {
                    List<Album> result = albumLoader.load();
                    return LocalData.success(result);
                });
    }


    @Override
    public void onModuleItemClick(Album album, int dataPosition, int adapterPosition) {
        MediaTool.toSelectMedia(mActivity,options,album);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != Activity.RESULT_OK||data==null){
            return;
        }
        mActivity.setResult(Activity.RESULT_OK,data);
        mActivity.finish();
    }
}
