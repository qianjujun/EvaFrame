package com.qianjujun.frame.utils;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.qianjujun.frame.R;


/**
 * 描述 : 说俩句呗
 * 作者 : qianjujun
 * 时间 : 2018/5/1914:44
 * 邮箱 : qianjujun@163.com
 */

public class DataBingAdapter {



    /**
     * 重写src
     */
    @BindingAdapter("android:src")
    public static void loadDefaultImgWithSrc(ImageView img, String url){
        LoadImageUtil.loadImage(img,url, R.drawable.bg_image);
    }

    @BindingAdapter("android:src")
    public static void loadDefaultImgWithSrc(ImageView img, int icon){
        img.setImageResource(icon);
    }



    @BindingAdapter("select")
    public static void setSelecte(View view, boolean select){
        if(view!=null){
            view.setSelected(select);
        }
    }


    @BindingAdapter("android:visibility")
    public static void setVisible(View view, boolean visible){
        view.setVisibility(visible? View.VISIBLE: View.GONE);
    }


    /**
     * textview h line
     * @param tv
     * @param strickout
     */
    @BindingAdapter("hl")
    public static void strickout(TextView tv, boolean strickout){
        if(strickout){
            tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }


    @BindingAdapter(value = {"startColor","endColor"})
    public static void setTextGradientColor(TextView tv, String startColor, String endColor){
        LinearGradient mLinearGradient =new LinearGradient(0, 0, 0, tv.getPaint().getTextSize(), Color.parseColor(startColor), Color.parseColor(endColor), Shader.TileMode.CLAMP);
        tv.getPaint().setShader(mLinearGradient);
        tv.invalidate();
    }










}
