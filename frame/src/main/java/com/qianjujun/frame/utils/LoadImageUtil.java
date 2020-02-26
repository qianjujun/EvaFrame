package com.qianjujun.frame.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.IntRange;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.qianjujun.frame.R;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 介绍：todo
 * 作者：qianjujun
 * 邮箱：qianjujun@163.com
 * 时间: 2018-05-15  17:07
 */
public class LoadImageUtil {
    public static List<String> EFFECTIVE_FORMAT = new ArrayList<>();

    static {
        String[] data = new String[]{"mp4", "avi", "mov", "wmv", "m4v", "flv", "f4v"};
        EFFECTIVE_FORMAT.addAll(Arrays.asList(data));
    }

    public static boolean isEffectiveFormat(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return false;
        }
        int startIndex = fileName.lastIndexOf(".");
        if (startIndex == -1) {
            return false;
        }
        startIndex += 1;

        if (startIndex >= fileName.length()) {
            return false;
        }
        String type = fileName.substring(startIndex);
        if (TextUtils.isEmpty(type)) {
            return false;
        }
        type = type.toLowerCase();
        return EFFECTIVE_FORMAT.contains(type);
    }

    public static boolean isGif(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        return url.endsWith(".gif") || url.endsWith(".GIF");
    }

    public static boolean isVideo(String url) {
        return isEffectiveFormat(url);
    }

    public static void loadImage(ImageView view, String url, int placeRes, boolean cache) {
        if (isGif(url)) {
            Glide.with(view.getContext())
                    .asGif()
                    .load(url)
                    .apply(new RequestOptions()
                            .placeholder(placeRes)
                            .diskCacheStrategy(cache ? DiskCacheStrategy.RESOURCE : DiskCacheStrategy.NONE)
                            .fitCenter())
                    .into(view);
        } else if (isVideo(url)) {
            Glide.with(view.getContext())
                    .asBitmap()
                    .load(url)
                    .apply(new RequestOptions()
                            .placeholder(placeRes)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .centerCrop()
                            .dontAnimate())
                    .addListener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
//                            Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(url, MediaStore.Video.Thumbnails.MICRO_KIND);
//                            if(bitmap==null){
//                                return false;
//                            }
//                            view.setImageBitmap(bitmap);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(view);
        } else {
            Glide.with(view.getContext())
                    .asBitmap()
                    .load(url)
                    .apply(new RequestOptions()
                            .placeholder(placeRes)
                            .diskCacheStrategy(cache ? DiskCacheStrategy.ALL : DiskCacheStrategy.NONE)
                            .centerCrop()
                            .dontAnimate())
                    .into(view);
        }
    }


    public static void loadImage(ImageView view, String url) {
        loadImage(view, url, R.drawable.shape_default, true);
    }

    public static void loadImage(ImageView view, String url, int placeRes) {
        loadImage(view, url, placeRes, true);
    }

    public static void loadRoundImg(ImageView img, String url, @IntRange(from = 0) int roundPx, @DrawableRes int placeRes) {
        if (null == img) {
            return;
        }
        Glide.with(img.getContext())
                .load(url)
                .apply(new RequestOptions()
                        .placeholder(placeRes)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .transform(new GlideRoundTransform(img.getContext(), roundPx))
                        .dontAnimate())
                .into(img);
    }

    /**
     * 圆角
     */

    final static class GlideRoundTransform extends BitmapTransformation {

        private static float radius = 0f;

        public GlideRoundTransform(Context context) {
            this(context, 4);
        }

        public GlideRoundTransform(Context context, int roundPx) {
            super();
            this.radius = roundPx;
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            Bitmap bitmap = TransformationUtils.centerCrop(pool, toTransform, outWidth, outHeight);
            return roundCrop(pool, bitmap);
        }

        private static Bitmap roundCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
            canvas.drawRoundRect(rectF, radius, radius, paint);
            return result;
        }

        public String getId() {
            return getClass().getName() + Math.round(radius);
        }

        @Override
        public void updateDiskCacheKey(MessageDigest messageDigest) {

        }

    }




}
