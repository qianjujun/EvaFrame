package com.hello7890.adapter.data;

public enum ViewModuleState {

    LOADING(IState.STATE_LOADING), FAIL(IState.STATE_FAIL), EMPTY(IState.STATE_EMPTY), SUCCESS(IState.STATE_SUCCESS);

    private int value;

    ViewModuleState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
