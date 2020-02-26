package com.qianjujun.frame.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qianjujun.frame.R;


/**
 * 描述 : 说俩句呗
 * 作者 : qianjujun
 * 时间 : 2018/5/921:45
 * 邮箱 : qianjujun@163.com
 */

public class ToastUtils {
    private static Context app;

    public static void init(Context context){
        app = context;
    }



    public static void show(String msg){
        if(TextUtils.isEmpty(msg)){
            return;
        }
        Toast toast = new Toast(app);
        View view = LayoutInflater.from(app).inflate(R.layout.better_view_toast_no_image,null);
        toast.setView(view);
        TextView textView = view.findViewById(R.id.tv_text);
        textView.setText(msg);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }


//    public static void show(){
//        Toast toast = Toast.makeText(app,"",100);
//        toast.setView();
//    }

    public static void showSuccess(String text){
        if(TextUtils.isEmpty(text)){
            return;
        }
        Toast toast = new Toast(app);
        View view = LayoutInflater.from(app).inflate(R.layout.better_view_toast,null);
        toast.setView(view);
        TextView textView = view.findViewById(R.id.tv_text);
        textView.setText(text);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    public static void showSuccess(int textRes){
        Toast toast = new Toast(app);
        View view = LayoutInflater.from(app).inflate(R.layout.better_view_toast,null);
        toast.setView(view);
        TextView textView = view.findViewById(R.id.tv_text);
        textView.setText(textRes);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    public static void showWarning(String text){
        if(TextUtils.isEmpty(text)){
            return;
        }
        Toast toast = new Toast(app);
        View view = LayoutInflater.from(app).inflate(R.layout.better_view_toast,null);
        toast.setView(view);
        TextView textView = view.findViewById(R.id.tv_text);
        textView.setText(text);
        ImageView imageView = view.findViewById(R.id.icon);
        imageView.setImageResource(R.drawable.icon_warning);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    public static void showWarning(int textRes){
        Toast toast = new Toast(app);
        View view = LayoutInflater.from(app).inflate(R.layout.better_view_toast,null);
        toast.setView(view);
        TextView textView = view.findViewById(R.id.tv_text);
        textView.setText(textRes);
        ImageView imageView = view.findViewById(R.id.icon);
        imageView.setImageResource(R.drawable.icon_warning);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }
}
