package com.hello7890.mediatools;

public interface FuncState {
    int COMPLETE = -4;
    int FAIL = -3;
    int CANCEL = -2;
    int PAUSE = -1;
    int WAITING = 0;
    int PRIORITY = 1;
    int RESUME = 2;
}
