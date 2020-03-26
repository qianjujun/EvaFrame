package com.qianjujun.fragment;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.hello7890.adapter.BaseViewModule;
import com.hello7890.adapter.decoration.BackgroundDecoration;

public class RoundBackgroundDecoration extends BackgroundDecoration {
    private Paint paint = new Paint();
    public RoundBackgroundDecoration(BaseViewModule viewModule, int outerLr, int innerLr) {
        super(viewModule, outerLr, innerLr);
        paint.setColor(Color.WHITE);
    }

    public RoundBackgroundDecoration(BaseViewModule viewModule, int outerLr, int innerLr, int dividerHeight) {
        super(viewModule, outerLr, innerLr, dividerHeight);
        paint.setColor(Color.WHITE);
    }

    @Override
    protected void onDrawViewModuleBackground(Canvas canvas, RectF rectF, boolean top, boolean bottom) {
        DrawUtils.drawRoundRect(canvas,paint,rectF,20,top,bottom);
    }
}
