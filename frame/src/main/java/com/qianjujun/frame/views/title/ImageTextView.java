package com.qianjujun.frame.views.title;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qianjujun.frame.R;


/**
 * 介绍：
 * 作者：qianjujun
 * 邮箱：qianjujun@imcoming.com.cn
 * 时间：2016/4/11
 */
public class ImageTextView extends RelativeLayout {
    private int redPointSize;
    public static final int DRAWABLE_LEFT = 0;
    public static final int DRAWABLE_RIGHT = 1;
    public static final int DRAWABLE_TOP = 2;
    public static final int DRAWABLE_BOTTOM = 3;
    public static final int DRAWABLE_VISABLE = 4;
    public static final int TEXT_VISABLE = 5;

    public static final int DRAWABLE_NUM = 6;

    public static final int NONE = 7;

    public static final int CHILD_GRAVITY_CENTER_HORIZONTAL = 0;
    public static final int CHILD_GRAVITY_LEFT_RIGHT = 1;
    public static final int CHILD_GRAVITY_LEFT_LEFT = 2;
    public static final int CHILD_GRAVITY_RIGHT_RIGHT = 3;

    private ImageButton imageView;
    private TextView textView;
    private int drawablePading;
    private int location;
    private int childGravity = CHILD_GRAVITY_LEFT_LEFT;



    int _imageWidth;
    int _imagehight;


    public ImageTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ImageTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        redPointSize = 21;
        LayoutInflater.from(context).inflate(R.layout.common_view_imagetextview, this, true);
        imageView = (ImageButton) findViewById(R.id.imageView);
        textView = (TextView) findViewById(R.id.textView);


        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ImageTextView);
        location = a.getInt(R.styleable.ImageTextView_viewLocation, NONE);
        childGravity = a.getInt(R.styleable.ImageTextView_childGravity,CHILD_GRAVITY_LEFT_LEFT);
        drawablePading = a.getDimensionPixelSize(R.styleable.ImageTextView_drawablePading, 15);
        _imageWidth = a.getDimensionPixelOffset(R.styleable.ImageTextView_imageWidth, 0);
        _imagehight = a.getDimensionPixelOffset(R.styleable.ImageTextView_imageHight, 0);
        if(_imageWidth==0){
            _imageWidth = LayoutParams.WRAP_CONTENT;
        }
        if(_imagehight==0){
            _imagehight = LayoutParams.WRAP_CONTENT;
        }

        int imageResId = a.getResourceId(R.styleable.ImageTextView_src, 0);
        if (imageResId != 0) {
            imageView.setImageResource(imageResId);
        }

        int textResId = a.getResourceId(R.styleable.ImageTextView_text, 0);
        if (textResId != 0) {
            textView.setText(textResId);
        }



        ColorStateList textColor = a.getColorStateList(R.styleable.ImageTextView_textColor);
        if (textColor != null) {
            textView.setTextColor(textColor);
        }

        int textSize = a.getDimensionPixelSize(R.styleable.ImageTextView_textSize, 0);
        if (textSize > 0) {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        }
        a.recycle();




        resetLocation(location);
    }

    private int textWidth;
    private int imageWidth;
    private int width;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(childGravity==CHILD_GRAVITY_CENTER_HORIZONTAL){
            textWidth = textView.getMeasuredWidth();
            imageWidth = imageView.getMeasuredWidth();
            width = getMeasuredWidth();
            adjustPading();
        }
    }

    private void adjustPading(){
        switch (location){
            case DRAWABLE_LEFT:
            case DRAWABLE_RIGHT:
                int padingTop = getPaddingTop();
                int padingBottom = getPaddingBottom();
                int padingLr = (width-(textWidth+imageWidth+drawablePading))/2;
                setPadding(padingLr,padingTop,padingLr,padingBottom);
                break;
            default:
                break;
        }





    }

    private void resetLocation(int location){
        if(location==NONE){
            textView.setVisibility(GONE);
            imageView.setVisibility(GONE);
            return;
        }
        LayoutParams rlImage = new LayoutParams(_imageWidth, _imagehight);
        LayoutParams rlText = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        textView.setVisibility(VISIBLE);
        imageView.setVisibility(VISIBLE);
        switch (location) {
            case DRAWABLE_NUM:
                int padding = 30;
                imageView.setPadding(0,padding,padding,padding);
                textView.setBackgroundResource(R.drawable.shape_red_point);
                textView.setTextColor(Color.WHITE);

                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,8);
                rlImage.addRule(CENTER_IN_PARENT);
                rlText.addRule(ALIGN_RIGHT,R.id.imageView);
                rlText.addRule(ALIGN_TOP,R.id.imageView);
                break;

            case DRAWABLE_BOTTOM:
                rlImage.addRule(CENTER_HORIZONTAL);
                rlText.addRule(CENTER_HORIZONTAL);
                rlImage.addRule(BELOW, R.id.textView);
                rlImage.setMargins(0, drawablePading, 0, 0);
                break;
            case DRAWABLE_TOP:
                rlImage.addRule(CENTER_HORIZONTAL);
                rlText.addRule(CENTER_HORIZONTAL);
                rlText.addRule(BELOW, R.id.imageView);
                rlImage.setMargins(0, 0, 0, drawablePading);
                break;
            case DRAWABLE_VISABLE:
                rlImage.addRule(CENTER_VERTICAL);
                if(childGravity==CHILD_GRAVITY_LEFT_LEFT){
                    rlImage.addRule(ALIGN_PARENT_LEFT);
                }else if(childGravity==CHILD_GRAVITY_RIGHT_RIGHT){
                    rlImage.addRule(ALIGN_PARENT_RIGHT);
                }else{
                    rlImage.addRule(CENTER_HORIZONTAL);
                }
                textView.setVisibility(View.GONE);
                break;
            case TEXT_VISABLE:
                rlText.addRule(CENTER_VERTICAL);
                imageView.setVisibility(View.GONE);
                if(childGravity==CHILD_GRAVITY_LEFT_LEFT){
                    rlText.addRule(ALIGN_PARENT_LEFT);
                }else if(childGravity==CHILD_GRAVITY_RIGHT_RIGHT){
                    rlText.addRule(ALIGN_PARENT_RIGHT);
                }else{
                    rlText.addRule(CENTER_HORIZONTAL);
                }


                break;
            case DRAWABLE_LEFT:
                rlImage.addRule(CENTER_VERTICAL);
                rlText.addRule(CENTER_VERTICAL);
                if(childGravity==CHILD_GRAVITY_LEFT_RIGHT){
                    rlText.addRule(ALIGN_PARENT_RIGHT);
                }else if(childGravity==CHILD_GRAVITY_RIGHT_RIGHT){
                    rlText.addRule(ALIGN_PARENT_RIGHT);
                    rlText.setMargins(drawablePading, 0, 0, 0);
                    rlImage.addRule(LEFT_OF, R.id.textView);
                }else{
                    rlText.addRule(RIGHT_OF, R.id.imageView);
                    rlText.setMargins(drawablePading, 0, 0, 0);
                }

                break;
            case DRAWABLE_RIGHT:
            default:
                rlText.addRule(CENTER_VERTICAL);
                rlImage.addRule(CENTER_VERTICAL);
                if(childGravity==CHILD_GRAVITY_LEFT_RIGHT){
                    rlImage.addRule(ALIGN_PARENT_RIGHT);
                }else if(childGravity==CHILD_GRAVITY_RIGHT_RIGHT){
                    rlImage.addRule(ALIGN_PARENT_RIGHT);
                    rlImage.setMargins(drawablePading, 0, 0, 0);
                    rlText.addRule(LEFT_OF,R.id.imageView);
                }else{
                    rlImage.addRule(RIGHT_OF, R.id.textView);
                    rlImage.setMargins(drawablePading, 0, 0, 0);
                }
                break;
        }
        imageView.setLayoutParams(rlImage);
        textView.setLayoutParams(rlText);

    }

    public ImageView getImageView() {
        return imageView;
    }



    public TextView getTextView() {
        return textView;
    }



    public void setText(String text){

        textView.setText(text);
        resetLocation(TEXT_VISABLE);
    }

    public void setImageRes(int imageRes){

        imageView.setImageResource(imageRes);
        resetLocation(DRAWABLE_VISABLE);
    }

    public void setImageAndText(String text, int imageRes, int location){
        if(TextUtils.isEmpty(text)&&imageRes==0){
            setVisibility(GONE);
            return;
        }
        setVisibility(VISIBLE);
        if(TextUtils.isEmpty(text)){
            setImageRes(imageRes);
        }else if(imageRes==0){
            setText(text);
        }else {
            textView.setText(text);
            imageView.setImageResource(imageRes);
            resetLocation(location);
        }

    }

    public void setImageOrText(String text, int imageRes){
        if(TextUtils.isEmpty(text)&&imageRes==0){
            setVisibility(GONE);
            return;
        }
        setVisibility(VISIBLE);
        if(imageRes==0){
            setText(text);
        }else {
            setImageRes(imageRes);
        }

    }


    public void setImageNum(int imageRes,int num){
        if(num==0){
            textView.setVisibility(GONE);
        }else{
            textView.setVisibility(VISIBLE);
        }
        String text = num>99?"99+":""+num;
        textView.setText(text);
        imageView.setImageResource(imageRes);
        resetLocation(DRAWABLE_NUM);
    }


    public void updateNum(int num){
        if(location==TEXT_VISABLE||location==NONE){
            return;
        }
        String text = num>99?"99+":""+num;
        textView.setText(text);
        if(location!=DRAWABLE_NUM){
            resetLocation(DRAWABLE_NUM);
        }
        if(num==0){
            textView.setVisibility(GONE);
        }else{
            textView.setVisibility(VISIBLE);


            if(num<0){
                textView.setText("");
                ViewGroup.LayoutParams layoutParams = textView.getLayoutParams();
                layoutParams.width = redPointSize;
                layoutParams.height = redPointSize;
                textView.setLayoutParams(layoutParams);
            }
        }
    }


}
