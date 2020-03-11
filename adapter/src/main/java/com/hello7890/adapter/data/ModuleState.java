package com.hello7890.adapter.data;

public class ModuleState implements IState{


    private int state;
    private String message;


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
