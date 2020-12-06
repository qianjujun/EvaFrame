package com.hello7890.mediatools;

import android.text.TextUtils;

public class Cmd {
    private StringBuilder sb = new StringBuilder();

    public Cmd append(String cmd){
        if(TextUtils.isEmpty(cmd)){
            return this;
        }
        sb.append(cmd);
        if(!cmd.endsWith(" ")){
            sb.append(" ");
        }
        return this;
    }

    @Override
    public String toString() {
        if(sb.charAt(sb.length()-1)==' '){
            sb.deleteCharAt(sb.length()-1);
        }
        return sb.toString();
    }
}
