package com.hello7890.mediatools;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "t_func_des")
public class FuncTask {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private String id;
    @NonNull
    @ColumnInfo(name = "cmd" ,defaultValue = "")
    private String cmd;
    @NonNull
    @ColumnInfo(name = "index" ,defaultValue = "0")
    private int index;
    @NonNull
    @ColumnInfo(name = "total" ,defaultValue = "1")
    private int total;
    @NonNull
    @ColumnInfo(name = "func_des_id" ,defaultValue = "")
    private long funcDesId;
    @NonNull
    @ColumnInfo(name = "success" ,defaultValue = "0")
    private int success;
    @NonNull
    @ColumnInfo(name = "target_path" ,defaultValue = "")
    private String targetPath;



    public FuncTask(){
        id = UUID.randomUUID().toString();
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public long getFuncDesId() {
        return funcDesId;
    }

    public void setFuncDesId(long funcDesId) {
        this.funcDesId = funcDesId;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getTargetPath() {
        return targetPath;
    }

    public void setTargetPath(String targetPath) {
        this.targetPath = targetPath;
    }
}
