package com.qianjujun.fragment;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/3/11 17:55
 * @describe
 */
public class DrawUtils {
    public static final int LOCATION_NONE = 0;
    public static final int LOCATION_LEFT_TOP = 1<<0;
    public static final int LOCATION_RIGHT_TOP = 1<<1;
    public static final int LOCATION_LEFT_BOTTOM = 1<<2;
    public static final int LOCATION_RIGHT_BOTTOM = 1<<3;


    public static void drawRect(Canvas canvas, Paint paint, RectF rectF){
        canvas.drawRect(rectF,paint);
    }


    public static void drawRoundRect(Canvas canvas, Paint paint,RectF rectF, int radio){
        drawRoundRect(canvas,paint,LOCATION_LEFT_TOP|LOCATION_RIGHT_TOP|LOCATION_LEFT_BOTTOM|LOCATION_RIGHT_BOTTOM,rectF,radio);
    }

    public static void drawTopRoundRect(Canvas canvas, Paint paint,RectF rectF, int radio){
        drawRoundRect(canvas,paint,LOCATION_LEFT_TOP|LOCATION_RIGHT_TOP,rectF,radio);
    }

    public static void drawBottomRoundRect(Canvas canvas, Paint paint,RectF rectF, int radio){
        drawRoundRect(canvas,paint,LOCATION_LEFT_BOTTOM|LOCATION_RIGHT_BOTTOM,rectF,radio);
    }




    public static void drawRoundRect(Canvas canvas, Paint paint,int location, RectF rectF, int radio){


        if(location==LOCATION_NONE){
            drawRect(canvas,paint,rectF);
            return;
        }

        canvas.drawRoundRect(rectF,radio,radio,paint);
        RectF temp = new RectF();

        int resize = (int) (rectF.bottom-rectF.top);
        if(radio>resize){
            radio = resize;
        }



        if((location&LOCATION_LEFT_TOP)!=LOCATION_LEFT_TOP){//消除左上角圆角
            temp.left = rectF.left;
            temp.top = rectF.top;
            temp.right = temp.left+radio;
            temp.bottom = temp.top+radio;
            draw1(canvas,temp,paint,rectF, radio);

        }
        if((location&LOCATION_RIGHT_TOP)!=LOCATION_RIGHT_TOP){//消除右上角圆角
            temp.top = rectF.top;
            temp.right = rectF.right;
            temp.bottom = temp.top+radio;
            temp.left = temp.right-radio;
            if(rectF.top<rectF.bottom){
                draw(canvas,temp,paint);
            }
        }

        if((location&LOCATION_LEFT_BOTTOM)!=LOCATION_LEFT_BOTTOM){//消除左上角圆角
            temp.left = rectF.left;
            temp.bottom = rectF.bottom;
            temp.right = temp.left+radio;
            temp.top = temp.bottom-radio;
            draw(canvas,temp,paint);
        }

        if((location&LOCATION_RIGHT_BOTTOM)!=LOCATION_RIGHT_BOTTOM){//消除左上角圆角
            temp.right = rectF.right;
            temp.bottom = rectF.bottom;
            temp.left = temp.right-radio;
            temp.top = temp.bottom-radio;
            draw(canvas,temp,paint);
        }


    }

    private static void draw(Canvas canvas,RectF temp,Paint paint){
        canvas.drawRect(temp,paint);
    }

    private static void draw1(Canvas canvas,RectF temp,Paint paint,RectF rectF, int radio){



        canvas.drawRect(temp,paint);
        Log.d("DrawUtils", "[" + temp + "], rectF = [" + rectF + "]");
    }
}
