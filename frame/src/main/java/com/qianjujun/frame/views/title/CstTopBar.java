package com.qianjujun.frame.views.title;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.qianjujun.frame.R;


/**
 * 介绍：todo
 * 作者：qianjujun
 * 邮箱：qianjujun@163.com
 * 时间: 2017-03-07  14:24
 */

public class CstTopBar extends LinearLayout implements ITitleUpdate {

    private View root;
    private View resetLeftView;
    private View resetRightView;
    private ImageTextView leftView;
    private View rightView;


    private ImageTextView midlleView;
    private ImageTextView rightImageTextView;
    private ImageTextView right2ImageTextView;


    private RelativeLayout middleParentView;

    private View divider;

    public CstTopBar(Context context) {
        super(context);
        init();
    }

    public CstTopBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CstTopBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        setOrientation(VERTICAL);
        LayoutInflater.from(getContext()).inflate(R.layout.common_view_cst_top_bar, this);
        middleParentView = findViewById(R.id.midller_parent_view);
        leftView = findViewById(R.id.left_view);
        rightView = findViewById(R.id.right_View);
        resetLeftView = findViewById(R.id.reset_left_view);
        resetRightView = findViewById(R.id.reset_right_view);
        midlleView = findViewById(R.id.midlle_view);
        rightImageTextView = findViewById(R.id.right_image_text_view);
        right2ImageTextView = findViewById(R.id.right_image_text_view2);
        root = findViewById(R.id.root);
        divider = findViewById(R.id.view_divder);
        TextView midlleText = midlleView.getTextView();
        midlleText.setMaxLines(1);
        midlleText.setTextColor(Color.BLACK);
        midlleText.setMaxLines(1);
        midlleText.setGravity(Gravity.CENTER);

        //divider.setBackgroundColor(Color.RED);

    }

    int leftWidth;
    int rightWidth;
    int resetWidth = 0;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        leftWidth = leftView.getMeasuredWidth();
        rightWidth = rightView.getMeasuredWidth();
        resetWidth = rightWidth - leftWidth;
        resetWidth();

    }

    LayoutParams llLeft;
    LayoutParams llRight;

    private void resetWidth() {
        llLeft = (LayoutParams) resetLeftView.getLayoutParams();
        llRight = (LayoutParams) resetRightView.getLayoutParams();
        if (resetWidth >= 0) {
            llLeft.width = resetWidth;
            llRight.width = 0;
        } else {
            llLeft.width = 0;
            llRight.width = 0 - resetWidth;
        }

        post(new Runnable() {
            @Override
            public void run() {
                resetLeftView.setLayoutParams(llLeft);
                resetRightView.setLayoutParams(llRight);
            }
        });
    }


    public void setTitlConfig(final TitleConfig config) {
        if (config == null) {
            return;
        }
        config.setTitleUpdate(this);
        leftView.setImageOrText(config.getLeftText(), config.getLeftImageRes());

        View coustomView = config.getCenterCoustomView();
        RelativeLayout.LayoutParams centerLp = config.getCoustomCenterLayoutParams();
        if (coustomView != null) {
            midlleView.setVisibility(GONE);

            middleParentView.addView(coustomView, null != centerLp ? centerLp : new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));


        } else {
            midlleView.setVisibility(VISIBLE);

            midlleView.setImageOrText(config.getCenterText(), config.getCenterImageRes());
        }

        rightImageTextView.setImageOrText(config.getRightText(), config.getRightImageRes());

        right2ImageTextView.setImageOrText(config.getRight2Text(), config.getRight2ImageRes());

        if (config.getClick() == null) {
            return;
        }

        leftView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                config.getClick().leftClick();
            }
        });

        midlleView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                config.getClick().centerClick();
            }
        });

        rightImageTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                config.getClick().rightClick();
            }
        });

        right2ImageTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                config.getClick().right2Click();
            }
        });

        setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                config.getClick().topbarClick();
            }
        });


        //divider.setVisibility(config.isDividerVisable()?VISIBLE:INVISIBLE);
    }


    @Override
    public void updateLeftImageRes(int leftImageRes) {
        leftView.setImageOrText(null, leftImageRes);
    }

    @Override
    public void updateLeftText(String leftText) {
        leftView.setImageOrText(leftText, 0);
    }

    @Override
    public void updateLeftText(@StringRes int leftTextRes) {
        leftView.setImageOrText(getStringById(leftTextRes), 0);
    }

    @Override
    public void updateCenterImageRes(int centerImageRes) {
        midlleView.setVisibility(VISIBLE);
        middleParentView.removeAllViews();
        middleParentView.setVisibility(GONE);
        midlleView.setImageOrText(null, centerImageRes);
    }

    @Override
    public void updateCenterText(String centerText) {
        midlleView.setVisibility(VISIBLE);
        middleParentView.removeAllViews();
        middleParentView.setVisibility(GONE);
        midlleView.setImageOrText(centerText, 0);
    }

    @Override
    public void updateCenterText(@StringRes int centerTextRes) {
        midlleView.setVisibility(VISIBLE);
        middleParentView.removeAllViews();
        middleParentView.setVisibility(GONE);
        midlleView.setImageOrText(getStringById(centerTextRes), 0);
    }

    @Override
    public void updateCenterCoustomView(View centerCoustomView) {
        if (centerCoustomView != null) {
            midlleView.setVisibility(GONE);
            middleParentView.setVisibility(VISIBLE);
            middleParentView.addView(centerCoustomView, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }

    @Override
    public void updateRightImageRes(int rightImageRes) {
        rightImageTextView.setImageOrText(null, rightImageRes);
    }

    @Override
    public void updateRightText(String rightText) {
        rightImageTextView.setImageOrText(rightText, 0);
    }

    @Override
    public void updateRightText(@StringRes int rightTextRes) {
        rightImageTextView.setImageOrText(getStringById(rightTextRes), 0);
    }

    @Override
    public void updateRight2ImageRes(int right2ImageRes) {
        right2ImageTextView.setImageOrText(null, right2ImageRes);
    }

    @Override
    public void updateRight2Text(String right2Text) {
        right2ImageTextView.setImageOrText(right2Text, 0);
    }

    @Override
    public void updateRight2Text(@StringRes int right2TextRes) {
        right2ImageTextView.setImageOrText(getStringById(right2TextRes), 0);
    }

    @Override
    public void updateRightImageNum(int imageRes, int num) {
        rightImageTextView.setImageNum(imageRes, num);
    }

    @Override
    public void updateRightNum(int num) {
        rightImageTextView.updateNum(num);
    }

    @Override
    public void updateRight2ImageNum(int imageRes, int num) {
        right2ImageTextView.setImageNum(imageRes, num);
    }

    @Override
    public void updateRight2Num(int num) {
        right2ImageTextView.updateNum(num);
    }

    @Override
    public void updateDivider(boolean visable) {
        if (visable) {
            divider.setVisibility(VISIBLE);
        } else {
            divider.setVisibility(GONE);
        }
    }

    public View getRoot() {
        return root;
    }


    private String getStringById(int res) {
        return getContext().getResources().getString(res);
    }


    public ImageTextView getMidlleView() {
        return midlleView;
    }

    public ImageTextView getRightImageTextView() {
        return rightImageTextView;
    }

    public ImageTextView getRight2ImageTextView() {
        return right2ImageTextView;
    }
}
