package com.qianjujun;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.DrawableCompat;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.Router;


public class MainActivity extends AppCompatActivity implements RouterPath{

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.image);
        /*-+

         */

    }

    public void testDivider(View view) {
        Router.buildFragment(PATH_TEST_DIVIDER).navigation(this);
    }


    public void testJump(View view) {
        Router.build(PATH_DEFAULT_ACTIVITY).navigation();


    }

    public void testJump1(View view) {
        Router.buildFragment(PATH_TEST_PERMISSION).navigation();
    }

    public void testModule(View view) {
        Router.buildFragment(PATH_TEST_MODULE).navigation();
    }


    Drawable tintDrawable(Drawable drawable, ColorStateList colors) {
        Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setHotspot(wrappedDrawable,10,10);
        ColorFilter colorFilter = DrawableCompat.getColorFilter(wrappedDrawable);
        wrappedDrawable = wrappedDrawable.mutate();
        DrawableCompat.setTintList(wrappedDrawable, colors);
        DrawableCompat.setTintMode(wrappedDrawable, PorterDuff.Mode.SRC_ATOP);
        int width = wrappedDrawable.getMinimumWidth();
        int height = wrappedDrawable.getMinimumHeight();

        return wrappedDrawable;
    }

    private void test(){
        imageView.setImageDrawable(tintDrawable(imageView.getDrawable(),ColorStateList.valueOf(Color.RED)));
    }


}
