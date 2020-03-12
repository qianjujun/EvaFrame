package com.hello7890.adapter.data;

public class ModuleState implements IState{


    private int state;
    private String message;
    private int errorCode;


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

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }


    @Override
    public String toString() {
        return "ModuleState{" +
                "state=" + state +
                ", message='" + message + '\'' +
                ", errorCode=" + errorCode +
                '}';
    }
}
