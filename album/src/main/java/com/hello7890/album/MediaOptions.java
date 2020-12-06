package com.hello7890.album;

import android.os.Parcel;
import android.os.Parcelable;

public class MediaOptions implements Parcelable{
    public static final int TYPE_VIDEO = 1;
    public static final int TYPE_IMAGE = 2;
    public static final int TYPE_ALL = 3;

    private int max = 1;
    private int requestCode;
    private int mediaType = TYPE_ALL;

    private long minSize = 0;
    private long maxSize = -1;

    public int getMax() {
        return max;
    }

    public MediaOptions setMax(int max) {
        this.max = max;
        return this;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public MediaOptions setRequestCode(int requestCode) {
        this.requestCode = requestCode;
        return this;
    }

    public long getMinSize() {
        return minSize;
    }

    public MediaOptions setMinSize(long minSize) {
        this.minSize = minSize;
        return this;
    }

    public long getMaxSize() {
        return maxSize;
    }

    public MediaOptions setMaxSize(long maxSize) {
        this.maxSize = maxSize;
        return this;
    }

    public int getMediaType() {
        return mediaType;
    }

    public MediaOptions setMediaType(int mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    private MediaOptions(){

    }

    public static MediaOptions build(int requestCode){
        MediaOptions options = new MediaOptions();
        options.requestCode = requestCode;
        return options;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.max);
        dest.writeInt(this.requestCode);
        dest.writeInt(this.mediaType);
        dest.writeLong(this.minSize);
        dest.writeLong(this.maxSize);
    }

    protected MediaOptions(Parcel in) {
        this.max = in.readInt();
        this.requestCode = in.readInt();
        this.mediaType = in.readInt();
        this.minSize = in.readLong();
        this.maxSize = in.readLong();
    }

    public static final Parcelable.Creator<MediaOptions> CREATOR = new Parcelable.Creator<MediaOptions>() {
        @Override
        public MediaOptions createFromParcel(Parcel source) {
            return new MediaOptions(source);
        }

        @Override
        public MediaOptions[] newArray(int size) {
            return new MediaOptions[size];
        }
    };



    public MediaOptions copy(){
        Parcel parcel = null;
        try {
            parcel = Parcel.obtain();
            parcel.writeParcelable(this, 0);

            parcel.setDataPosition(0);
            return parcel.readParcelable(this.getClass().getClassLoader());
        } catch (Exception e){
            return new MediaOptions();
        }finally {
            if(parcel!=null){
                parcel.recycle();
            }
        }
    }

}
