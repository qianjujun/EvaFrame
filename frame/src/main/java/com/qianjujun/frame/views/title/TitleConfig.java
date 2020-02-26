package com.qianjujun.frame.views.title;

import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.StringRes;

import com.qianjujun.frame.utils.Itn;


/**
 * 介绍：todo
 * 作者：qianjujun
 * 邮箱：qianjujun@163.com
 * 时间: 2017-03-08  13:05
 */

public class TitleConfig implements ITitleUpdate{
    private ITitleUpdate mTitleUpdate;


    void setTitleUpdate(ITitleUpdate titleUpdate){
        this.mTitleUpdate = titleUpdate;
    }


    private int leftImageRes;
    private String leftText;

    private int centerImageRes;
    private String centerText;
    private View centerCoustomView;
    private RelativeLayout.LayoutParams centerLp;


    private int rightImageRes;
    private String rightText;


    private int right2ImageRes;
    private String right2Text;

    private boolean dividerVisable = true;


    private ITopbarClickListner click;

    private TitleConfig(int leftImageRes, String leftText, int centerImageRes, String centerText, View centerCoustomView, RelativeLayout.LayoutParams centerLp, int rightImageRes, String rightText, int right2ImageRes, String right2Text) {
        this.leftImageRes = leftImageRes;
        this.leftText = leftText;
        this.centerImageRes = centerImageRes;
        this.centerText = centerText;
        this.centerCoustomView = centerCoustomView;
        this.rightImageRes = rightImageRes;
        this.rightText = rightText;
        this.right2ImageRes = right2ImageRes;
        this.right2Text = right2Text;
        this.centerLp = centerLp;
    }





    public static Build build(){
        return new Build();
    }




    @Override
    public void updateLeftImageRes(int leftImageRes) {
        if(mTitleUpdate!=null){
            mTitleUpdate.updateLeftImageRes(leftImageRes);
        }
        this.leftImageRes = leftImageRes;
    }

    @Override
    public void updateLeftText(String leftText) {
        if(mTitleUpdate!=null){
            mTitleUpdate.updateLeftText(leftText);
        }
        this.leftText = leftText;
    }

    @Override
    public void updateLeftText(@StringRes int leftTextRes) {
        if(mTitleUpdate!=null){
            mTitleUpdate.updateLeftText(leftTextRes);
        }
        this.leftText = Itn.getString(leftTextRes);
    }

    @Override
    public void updateCenterImageRes(int centerImageRes) {
        if(mTitleUpdate!=null){
            mTitleUpdate.updateCenterImageRes(centerImageRes);
        }
        this.centerImageRes = centerImageRes;
    }

    @Override
    public void updateCenterText(String centerText) {
        if(mTitleUpdate!=null){
            mTitleUpdate.updateCenterText(centerText);
        }
        this.centerText = centerText;
    }

    @Override
    public void updateCenterText(@StringRes int centerTextRes) {
        if(mTitleUpdate!=null){
            mTitleUpdate.updateCenterText(centerTextRes);
        }
        this.centerText = Itn.getString(centerTextRes);
    }

    @Override
    public void updateCenterCoustomView(View centerCoustomView) {
        if(mTitleUpdate!=null){
            mTitleUpdate.updateCenterCoustomView(centerCoustomView);
        }
        this.centerCoustomView = centerCoustomView;
    }

    @Override
    public void updateRightImageRes(int rightImageRes) {
        if(mTitleUpdate!=null){
            mTitleUpdate.updateRightImageRes(rightImageRes);
        }
        this.rightImageRes = rightImageRes;
    }

    @Override
    public void updateRightText(String rightText) {
        if(mTitleUpdate!=null){
            mTitleUpdate.updateRightText(rightText);
        }
        this.rightText = rightText;
    }

    @Override
    public void updateRightText(@StringRes int rightTextRes) {
        if(mTitleUpdate!=null){
            mTitleUpdate.updateRightText(rightTextRes);
        }
        this.rightText = Itn.getString(rightTextRes);
    }

    @Override
    public void updateRight2ImageRes(int right2ImageRes) {
        if(mTitleUpdate!=null){
            mTitleUpdate.updateRight2ImageRes(right2ImageRes);
        }
        this.right2ImageRes = right2ImageRes;
    }

    @Override
    public void updateRight2Text(String right2Text) {
        if(mTitleUpdate!=null){
            mTitleUpdate.updateRight2Text(right2Text);
        }
        this.right2Text = right2Text;
    }

    @Override
    public void updateRight2Text(@StringRes int right2TextRes) {
        if(mTitleUpdate!=null){
            mTitleUpdate.updateRight2Text(right2TextRes);
        }
        this.right2Text = Itn.getString(right2TextRes);
    }

    @Override
    public void updateRightImageNum(int imageRes, int num) {
        if(mTitleUpdate!=null){
            mTitleUpdate.updateRightImageNum(imageRes,num);
        }
    }

    @Override
    public void updateRightNum(int num) {
        if(mTitleUpdate!=null){
            mTitleUpdate.updateRightNum(num);
        }
    }

    @Override
    public void updateRight2ImageNum(int imageRes, int num) {
        if(mTitleUpdate!=null){
            mTitleUpdate.updateRight2ImageNum(imageRes,num);
        }
    }

    @Override
    public void updateRight2Num(int num) {
        if(mTitleUpdate!=null){
            mTitleUpdate.updateRight2Num(num);
        }
    }

    @Override
    public void updateDivider(boolean visable) {
        if(mTitleUpdate!=null){
            mTitleUpdate.updateDivider(visable);
        }
    }

    public static final class Build{
        public CenterBuild leftImage(int imageRes){
            return new CenterBuild(imageRes,null);
        }

        public CenterBuild leftText(String text){
            return new CenterBuild(0,text);
        }

        public CenterBuild leftText(@StringRes int stringRes){
            return new CenterBuild(0, Itn.getString(stringRes));
        }


        public RightBuild centerImage(int imageRes){
            return new RightBuild(0,null,imageRes,null);
        }


        public RightBuild centerText(String text){
            return new RightBuild(0,null,0,text);
        }

        public RightBuild centerText(@StringRes int stringRes){
            return new RightBuild(0,null,0,Itn.getString(stringRes));
        }

        public Right2Build rightText(String text){
            return new Right2Build(0,null,0,null,null,null,0,text);
        }

        public Right2Build rightImage(int imageRes){
            return new Right2Build(0,null,0,null,null,null,imageRes,null);
        }



        public static final class CenterBuild{
            private int leftImageRes;
            private String leftText;

            public CenterBuild(int leftImageRes, String leftText) {
                this.leftImageRes = leftImageRes;
                this.leftText = leftText;
            }

            public RightBuild centerImage(int imageRes){
                return new RightBuild(leftImageRes,leftText,imageRes,null);
            }


            public RightBuild centerText(String text){
                return new RightBuild(leftImageRes,leftText,0,text);
            }

            public RightBuild centerText(@StringRes int stringRes){
                return new RightBuild(leftImageRes,leftText,0,Itn.getString(stringRes));
            }


            public RightBuild centerCoustomView(View view, RelativeLayout.LayoutParams centerLp){
                return new RightBuild(leftImageRes,leftText,view, centerLp);
            }


            public Right2Build rightText(String text){
                return new Right2Build(leftImageRes,leftText,0,null,null,null,0,text);
            }

            public Right2Build rightText(@StringRes int stringRes){
                return new Right2Build(leftImageRes,leftText,0,null,null,null,0,Itn.getString(stringRes));
            }


            public Right2Build rightImage(int imageRes){
                return new Right2Build(leftImageRes,leftText,0,null,null,null,imageRes,null);
            }


            public TitleConfig create(){
                return new TitleConfig(leftImageRes,leftText,0,null,null,null,0,null,0,null);

            }

        }

        public static final class RightBuild{
            private int leftImageRes;
            private String leftText;

            private int centerImageRes;
            private String centerText;

            private View centerCoustomView;
            private RelativeLayout.LayoutParams centerLp;


            public RightBuild(int leftImageRes, String leftText, int centerImageRes, String centerText) {
                this.leftImageRes = leftImageRes;
                this.leftText = leftText;
                this.centerImageRes = centerImageRes;
                this.centerText = centerText;
            }

            public RightBuild(int leftImageRes, String leftText, View centerCoustomView, RelativeLayout.LayoutParams centerLp) {
                this.leftImageRes = leftImageRes;
                this.leftText = leftText;
                this.centerCoustomView = centerCoustomView;
                this.centerLp = centerLp;
            }


            public Right2Build rightText(String text){
                return new Right2Build(leftImageRes,leftText,centerImageRes,centerText,centerCoustomView, centerLp,0,text);
            }

            public Right2Build rightText(@StringRes int stringRes){
                return new Right2Build(leftImageRes,leftText,centerImageRes,centerText,centerCoustomView, centerLp,0,Itn.getString(stringRes));
            }

            public Right2Build rightImage(int imageRes){
                return new Right2Build(leftImageRes,leftText,centerImageRes,centerText,centerCoustomView, centerLp,imageRes,null);
            }


            public TitleConfig create(){
                return new TitleConfig(leftImageRes,leftText,centerImageRes,centerText,centerCoustomView,centerLp,0,null,0,null);

            }


        }



        public static final class Right2Build{
            private int leftImageRes;
            private String leftText;

            private int centerImageRes;
            private String centerText;
            private View centerCoustomView;
            private RelativeLayout.LayoutParams centerLp;

            private int rightImageRes;
            private String rightText;


            public Right2Build(int leftImageRes, String leftText, int centerImageRes, String centerText, View centerCoustomView, RelativeLayout.LayoutParams centerLp, int rightImageRes, String rightText) {
                this.leftImageRes = leftImageRes;
                this.leftText = leftText;
                this.centerImageRes = centerImageRes;
                this.centerText = centerText;
                this.centerCoustomView = centerCoustomView;
                this.rightImageRes = rightImageRes;
                this.rightText = rightText;
                this.centerLp = centerLp;
            }


            public TitleConfig right2Text(String text){
                return new TitleConfig(leftImageRes,leftText,centerImageRes,centerText,centerCoustomView,centerLp,rightImageRes,rightText,0,text);
            }

            public TitleConfig right2Text(@StringRes int stringRes){
                return new TitleConfig(leftImageRes,leftText,centerImageRes,centerText,centerCoustomView,centerLp,rightImageRes,rightText,0,Itn.getString(stringRes));
            }

            public TitleConfig right2Image(int imageRes){
                return new TitleConfig(leftImageRes,leftText,centerImageRes,centerText,centerCoustomView,centerLp,rightImageRes,rightText,imageRes,null);
            }


            public TitleConfig create(){
                return new TitleConfig(leftImageRes,leftText,centerImageRes,centerText,centerCoustomView,centerLp,rightImageRes,rightText,0,null);
            }
        }

    }


    public int getLeftImageRes() {
        return leftImageRes;
    }

    public String getLeftText() {
        return leftText;
    }

    public int getCenterImageRes() {
        return centerImageRes;
    }

    public String getCenterText() {
        return centerText;
    }

    public View getCenterCoustomView() {
        return centerCoustomView;
    }

    public RelativeLayout.LayoutParams getCoustomCenterLayoutParams(){
        return centerLp;
    }

    public int getRightImageRes() {
        return rightImageRes;
    }

    public String getRightText() {
        return rightText;
    }

    public int getRight2ImageRes() {
        return right2ImageRes;
    }

    public String getRight2Text() {
        return right2Text;
    }

    public ITopbarClickListner getClick() {
        return click;
    }

    public TitleConfig click(ITopbarClickListner click){
        this.click = click;
        return this;
    }

    public boolean isDividerVisable() {
        return dividerVisable;
    }

    public TitleConfig setDividerVisable(boolean dividerVisable) {
        this.dividerVisable = dividerVisable;
        return this;
    }
}
