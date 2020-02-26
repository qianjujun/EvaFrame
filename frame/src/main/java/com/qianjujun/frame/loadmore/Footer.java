package com.qianjujun.frame.loadmore;

public class Footer {
    public static final int STATE_LODING = 1;
    public static final int STATE_NO_MORE = 2;
    public static final int STATE_FAIL = 3;
    public static final int STATE_NORMAL = 4;
    public static final int STATE_SUCCESS = 5;
    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Footer() {
        this.state = STATE_SUCCESS;
    }

    public String getText(){
        switch (state){
            case STATE_NO_MORE:
                return "";
            case STATE_FAIL:
                return "";
            default:
                return "";
        }
    }

    @Override
    public String toString() {
        return "Footer{" +
                "state=" + state +
                '}';
    }
}
