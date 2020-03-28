package com.cfmoto.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

public class ImageGridLayout extends ViewGroup {
    private TextView textView;

    private int padding = 30;

    public ImageGridLayout(Context context) {
        super(context);
    }

    public ImageGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageGridLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private List<String> imageList = new ArrayList<>();

    private List<ImageView> imageViews = new ArrayList<>();

    public void setData(List<String> list) {
        this.imageList.clear();
        this.imageList.addAll(list);

        if (this.imageList.size() == 0) {
            this.imageViews.clear();
            removeAllViews();
            return;
        }
        makeSureImageViewSize();
        requestLayout();
    }


    private int getImageViewSize() {
        int size = imageList.size();
        if (size == 1 || size == 2) {
            return 1;
        }
        if (size == 3) {
            return 3;
        }
        return size >= 4 ? 4 : 0;
    }


    private ImageView getCacheImageView(int index) {
        if (index < 0 || index >= imageViews.size()) {
            return null;
        }
        return imageViews.get(index);
    }


    private void makeSureImageViewSize() {
        int imageViewSize = getImageViewSize();
        int cha = imageViews.size() - imageViewSize;
        ImageView imageView;
        if (cha > 0) {
            for (int i = 0; i < cha; i++) {
                imageView = imageViews.get(imageViews.size() - 1);
                imageViews.remove(imageView);//移除最后一个
                removeView(imageView);
            }
        }
        for (int i = 0; i < imageViewSize; i++) {
            imageView = getCacheImageView(i);
            if (imageView == null) {
                imageView = new ImageView(getContext());
                imageViews.add(imageView);
                addView(imageView);
            }
        }


        int realSize = this.imageList.size();

        boolean hideText = realSize == 0 || realSize == 1 || realSize == 3 || realSize == 4;
        if (hideText) {
            if (textView != null) {
                textView.setVisibility(GONE);
            }
        } else {
            if (textView == null) {
                textView = new TextView(getContext());
            }
            ViewGroup v = (ViewGroup) textView.getParent();
            if (v != null) {
                v.removeView(textView);
            }

            addView(textView);
            textView.setVisibility(VISIBLE);
            textView.setTextColor(Color.WHITE);
            textView.setText(String.valueOf(realSize));
        }


    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int imageSize = imageViews.size();
        ImageView imageView;
        int width;
        switch (imageSize) {
            case 1:
                imageView = imageViews.get(0);
                imageView.layout(l, t, r, b);
                break;

            case 3:
                imageView = imageViews.get(0);
                width = imageView.getMeasuredWidth();
                imageView.layout(l, t, width, t + width);

                imageView = imageViews.get(1);
                imageView.layout(l + width + padding, t, r, t + width);

                imageView = imageViews.get(2);
                imageView.layout(l, t + width + padding, r, b);
                break;

            case 4:
                imageView = imageViews.get(0);
                width = imageView.getMeasuredWidth();
                imageView.layout(l, t, width, t + width);

                imageView = imageViews.get(1);
                imageView.layout(l + width + padding, t, r, t + width);

                imageView = imageViews.get(2);
                imageView.layout(l, t + width + padding, width, b);

                imageView = imageViews.get(3);
                imageView.layout(l + width + padding, t + width + padding, r, b);
                break;
        }

        if (textView != null) {
            textView.layout(r - 100, b - 100, r, b);
        }


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int imageSize = imageViews.size();
        if (imageSize == 0 || imageList.size() == 0) {
            setMeasuredDimension(width, 0);
            return;
        }
        setMeasuredDimension(width, width);

        if (textView != null) {
            textView.measure(MeasureSpec.makeMeasureSpec(100, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(100, MeasureSpec.EXACTLY));
        }


        int childWidth = 0, childHeight = 0;

        switch (imageSize) {
            case 1:
                childWidth = width;
                childHeight = width;
                break;
            case 3:
                childWidth = (width - padding) / 2;
                childHeight = (width - padding) / 2;
                break;
            case 4:
                childWidth = (width - padding) / 2;
                childHeight = childWidth;
                break;
        }
        int measureSpecWidth = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
        int measureSpecHeight = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);

        ImageView imageView;
        for (int i = 0; i < imageSize; i++) {
            imageView = imageViews.get(i);

            if (imageSize == 3 && i == 2) {//三张图片模式最后一张的情况
                int _measureSpecWidth = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
                int _measureSpecHeight = MeasureSpec.makeMeasureSpec((width - padding) / 2, MeasureSpec.EXACTLY);
                imageView.measure(_measureSpecWidth, _measureSpecHeight);
            } else {
                imageView.measure(measureSpecWidth, measureSpecHeight);
            }
            //GlideUtils.loadCenterCropImage(getContext(), imageList.get(i), imageView);
        }
    }
}