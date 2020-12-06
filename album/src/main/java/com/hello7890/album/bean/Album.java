package com.hello7890.album.bean;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class Album implements Parcelable {
    private String id;
    private Uri coverUri;
    private String displayName;
    private long count;

    public Album(String id, Uri coverUri, String albumName, long count) {
        this.id = id;
        this.coverUri = coverUri;
        this.displayName = albumName;
        this.count = count;
    }

    public Album() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Uri getCoverUri() {
        return coverUri;
    }

    public void setCoverUri(Uri coverUri) {
        this.coverUri = coverUri;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeParcelable(this.coverUri, flags);
        dest.writeString(this.displayName);
        dest.writeLong(this.count);
    }

    protected Album(Parcel in) {
        this.id = in.readString();
        this.coverUri = in.readParcelable(Uri.class.getClassLoader());
        this.displayName = in.readString();
        this.count = in.readLong();
    }

    public static final Parcelable.Creator<Album> CREATOR = new Parcelable.Creator<Album>() {
        @Override
        public Album createFromParcel(Parcel source) {
            return new Album(source);
        }

        @Override
        public Album[] newArray(int size) {
            return new Album[size];
        }
    };
}
