package com.hello7890.adapter.decoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HeadDecoration extends RecyclerView.ItemDecoration implements View.OnTouchListener {

    private static final String TAG = "HeadDecoration";
    GestureDetector gd;
    private int parentTop;
    public HeadDecoration(){

    }


    boolean handler;


    RecyclerView recyclerView;
    private View child0;
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if(recyclerView==null){
            recyclerView = parent;
            parentTop = recyclerView.getTop()+recyclerView.getPaddingTop();
            recyclerView.setOnTouchListener(this);
            gd = new GestureDetector(recyclerView.getContext(),listene);



        }

        child0 = null;
        handler = false;
        View child = parent.getChildAt(0);
        int position = recyclerView.getChildAdapterPosition(child);
        if(position>0){
            return;
        }
        child0 = child;
        handler = child.getTop()>=parentTop;

        Log.d(TAG, "onDrawOver() called with: c = [" + child.getTop() + "], parent = [" + parentTop + "], state = [" + "state" + "]");


    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(gd==null){
            return false;
        }
        boolean handler = gd.onTouchEvent(event);
        Log.d(TAG, "onTouch() called with: v = [" + handler + "], event = [" + "event" + "]");
        return handler;
    }


    private GestureDetector.SimpleOnGestureListener listene = new GestureDetector.SimpleOnGestureListener(){
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return super.onSingleTapUp(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            boolean aa = child0!=null&&handler;

            if(aa){

            }

            recyclerView.offsetChildrenVertical(0-(int) distanceY);

            //return super.onScroll(e1, e2, distanceX, distanceY);
            Log.d(TAG, "onScroll() called with: e1 = [" + aa + "], e2 = [" + "e2" + "], distanceX = [" + distanceX + "], distanceY = [" + distanceY + "]");
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public void onShowPress(MotionEvent e) {
            super.onShowPress(e);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            return super.onDoubleTapEvent(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onContextClick(MotionEvent e) {
            return super.onContextClick(e);
        }
    };
}
