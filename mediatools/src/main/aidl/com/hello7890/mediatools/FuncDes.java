package com.hello7890.mediatools;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "t_func_des")
public class FuncDes implements FuncState,Parcelable {


    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private long id;

    @NonNull
    @ColumnInfo(name = "cmd" ,defaultValue = "")
    private String cmd;

    @NonNull
    @ColumnInfo(name = "inputPath" ,defaultValue = "")
    private String inputPath;
    @NonNull
    @ColumnInfo(name = "outputPath" ,defaultValue = "")
    private String outputPath;


    @NonNull
    @ColumnInfo(name = "total" ,defaultValue = "1")
    private int total;//合并视频总数

    @NonNull
    @ColumnInfo(name = "duration" ,defaultValue = "0")
    private int duration;//总时间

    @NonNull
    @ColumnInfo(name = "state" ,defaultValue = "0")
    private int state;//状态

    /**
     * 优先等级
     */
    @NonNull
    @ColumnInfo(name = "priority" ,defaultValue = "0")
    private int priority;

    @NonNull
    @ColumnInfo(name = "progress" ,defaultValue = "0")
    private int progress;

    @NonNull
    @ColumnInfo(name = "execute_time" ,defaultValue = "0")
    private int executeTime;


    public FuncDes() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getCmd() {
        return cmd;
    }

    public void setCmd(@NonNull String cmd) {
        this.cmd = cmd;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getProgress() {
        return progress;
    }

    @NonNull
    public String getInputPath() {
        return inputPath;
    }

    public void setInputPath(@NonNull String inputPath) {
        this.inputPath = inputPath;
    }

    @NonNull
    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(@NonNull String outputPath) {
        this.outputPath = outputPath;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(int executeTime) {
        this.executeTime = executeTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.cmd);
        dest.writeString(this.inputPath);
        dest.writeString(this.outputPath);
        dest.writeInt(this.total);
        dest.writeInt(this.duration);
        dest.writeInt(this.state);
        dest.writeInt(this.priority);
        dest.writeInt(this.progress);
        dest.writeInt(this.executeTime);
    }

    protected FuncDes(Parcel in) {
        this.id = in.readLong();
        this.cmd = in.readString();
        this.inputPath = in.readString();
        this.outputPath = in.readString();
        this.total = in.readInt();
        this.duration = in.readInt();
        this.state = in.readInt();
        this.priority = in.readInt();
        this.progress = in.readInt();
        this.executeTime = in.readInt();
    }

    public static final Creator<FuncDes> CREATOR = new Creator<FuncDes>() {
        @Override
        public FuncDes createFromParcel(Parcel source) {
            return new FuncDes(source);
        }

        @Override
        public FuncDes[] newArray(int size) {
            return new FuncDes[size];
        }
    };
}
