package com.hello7890.album.loader;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContentResolverCompat;

import com.hello7890.album.MediaOptions;
import com.hello7890.album.bean.Album;
import com.hello7890.album.bean.Media;

import java.util.ArrayList;
import java.util.List;

public class MediaLoader {
    private static final Uri QUERY_URI = MediaStore.Files.getContentUri("external");
    private static final String ORDER_BY = MediaStore.Images.Media.DATE_TAKEN + " DESC";
    private static final String[] PROJECTION = {
            MediaStore.Files.FileColumns._ID,
            MediaStore.MediaColumns.DISPLAY_NAME,
            MediaStore.MediaColumns.MIME_TYPE,
            MediaStore.MediaColumns.SIZE,
            "duration"};


    private Context context;
    private Uri uri;
    private String[] projection;
    private String selection;
    private String[] selectionArgs;
    private String sortOrder;
    public MediaLoader(@NonNull Context context, @Nullable String[] projection, @Nullable String selection) {
        this.context = context;
        this.uri = QUERY_URI;
        this.projection = projection;
        this.selection = selection;
        this.selectionArgs = null;
        this.sortOrder = ORDER_BY;

    }



    public static MediaLoader getInstance(Context context, MediaOptions mediaOptions, Album album){
        String[] projection = PROJECTION;
        StringBuilder selection = new StringBuilder();
        if(mediaOptions.getMediaType()==MediaOptions.TYPE_VIDEO){
            selection.append(MediaStore.Files.FileColumns.MEDIA_TYPE);
            selection.append("=");
            selection.append(MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO);

            //selection.append(" AND mime_type = 'video/mp4'");



        }else if(mediaOptions.getMediaType()==MediaOptions.TYPE_IMAGE){
            selection.append(MediaStore.Files.FileColumns.MEDIA_TYPE);
            selection.append("=");
            selection.append(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE);
        }else {
            selection.append("(");

            selection.append("(");
            selection.append(MediaStore.Files.FileColumns.MEDIA_TYPE);
            selection.append("=");
            selection.append(MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO);
            selection.append(" AND mime_type = 'video/mp4'");
            selection.append(")");

            selection.append(" OR ");
            selection.append(MediaStore.Files.FileColumns.MEDIA_TYPE);
            selection.append("=");
            selection.append(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE);
            selection.append(")");
        }

        if(!TextUtils.equals("-1",album.getId())){
            selection.append(" AND bucket_id =");
            selection.append(album.getId());
        }

        selection.append(" AND ");
        selection.append(MediaStore.MediaColumns.SIZE);
        selection.append(">0");

        return new MediaLoader(context,projection,selection.toString());

    }


    public List<Media> load() throws Exception{
        Cursor cursor = null;
        try {
            cursor = ContentResolverCompat.query(context.getContentResolver(),uri, projection, selection, selectionArgs, sortOrder,null);
            return convert(cursor);
        }finally {
            if(cursor!=null&&!cursor.isClosed()){
                cursor.close();
            }
        }

    }


    protected List<Media> convert(Cursor cursor) {
        List<Media> list = new ArrayList<>();
        Media mediaItem;
        while (cursor.moveToNext()) {
            mediaItem = new Media();
            mediaItem.setId(cursor.getLong(cursor.getColumnIndex(MediaStore.Files.FileColumns._ID)));
            mediaItem.setMimeType(cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.MIME_TYPE)));
            mediaItem.setDuration(cursor.getLong(cursor.getColumnIndex("duration")));
            mediaItem.setSize( cursor.getLong(cursor.getColumnIndex(MediaStore.MediaColumns.SIZE)));
            Uri contentUri;
            if(MimeType.isImage(mediaItem.getMimeType())){
                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            }else if(MimeType.isVideo(mediaItem.getMimeType())){
                contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            }else {
                contentUri = MediaStore.Files.getContentUri("external");
            }
            mediaItem.setUri(ContentUris.withAppendedId(contentUri, mediaItem.getId()));
            list.add(mediaItem);
        }



        return list;
    }

}
