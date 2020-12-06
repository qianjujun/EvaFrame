package com.hello7890.album.loader;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContentResolverCompat;

import com.hello7890.album.MediaOptions;
import com.hello7890.album.bean.Album;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AlbumLoader {
    private static final String COLUMN_BUCKET_ID = "bucket_id";
    private static final String COLUMN_BUCKET_DISPLAY_NAME = "bucket_display_name";
    public static final String COLUMN_COUNT = "count";
    private static final Uri QUERY_URI = MediaStore.Files.getContentUri("external");


    private static final String[] PROJECTION = {
            MediaStore.Files.FileColumns._ID,
            COLUMN_BUCKET_ID,
            COLUMN_BUCKET_DISPLAY_NAME,
            MediaStore.MediaColumns.MIME_TYPE,
            "COUNT(*) AS " + COLUMN_COUNT};

    private static final String[] PROJECTION_29 = {
            MediaStore.Files.FileColumns._ID,
            COLUMN_BUCKET_ID,
            COLUMN_BUCKET_DISPLAY_NAME,
            MediaStore.MediaColumns.MIME_TYPE};

    private static final String BUCKET_ORDER_BY = "datetaken DESC";



    private Context context;
    private Uri uri;
    private String[] projection;
    private String selection;
    private String[] selectionArgs;
    private String sortOrder;

    private AlbumLoader(@NonNull Context context, @Nullable String[] projection, @Nullable String selection) {
        this.context = context;
        this.uri = QUERY_URI;
        this.projection = projection;
        this.selection = selection;
        this.selectionArgs = null;
        this.sortOrder = BUCKET_ORDER_BY;
    }



    public static AlbumLoader getInstance(Context context, MediaOptions options){
        String[] projection;
        if(beforeAndroidTen()){
            projection = PROJECTION;
        }else {
            projection = PROJECTION_29;
        }
        StringBuilder selection = new StringBuilder();
        if(options.getMediaType()==MediaOptions.TYPE_IMAGE){
            selection.append(MediaStore.Files.FileColumns.MEDIA_TYPE);
            selection.append("=");
            selection.append(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE);

        }else if(options.getMediaType()==MediaOptions.TYPE_VIDEO){
            selection.append(MediaStore.Files.FileColumns.MEDIA_TYPE);
            selection.append("=");
            selection.append(MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO);

            //selection.append(" AND mime_type = 'video/mp4'");
        }else {
            selection.append("(");
            selection.append(MediaStore.Files.FileColumns.MEDIA_TYPE);
            selection.append("=");
            selection.append(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE);
            selection.append(" OR ");

            selection.append("(");
            selection.append(MediaStore.Files.FileColumns.MEDIA_TYPE);
            selection.append("=");
            selection.append(MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO);
            selection.append(" AND mime_type = 'video/mp4'");
            selection.append(")");


            selection.append(")");
        }
        selection.append(" AND ");
        selection.append(MediaStore.MediaColumns.SIZE);
        selection.append(">");
        selection.append(options.getMinSize());

        if(options.getMaxSize()>0){
            selection.append(" AND ");
            selection.append(MediaStore.MediaColumns.SIZE);
            selection.append("<");
            selection.append(options.getMaxSize());
        }

        if(beforeAndroidTen()){
            selection.append(") GROUP BY (bucket_id");
        }
        return new AlbumLoader(context,projection,selection.toString());
    }




    public List<Album> load() throws Exception{
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





    protected List<Album> convert(Cursor albums) {
        List<Album> list = new ArrayList<>();
        int totalCount = 0;
        Uri allAlbumCoverUri = null;
        if(beforeAndroidTen()){
            if (albums != null) {
                while (albums.moveToNext()) {
                    long fileId = albums.getLong(
                            albums.getColumnIndex(MediaStore.Files.FileColumns._ID));
                    long bucketId = albums.getLong(
                            albums.getColumnIndex(COLUMN_BUCKET_ID));
                    String bucketDisplayName = albums.getString(
                            albums.getColumnIndex(COLUMN_BUCKET_DISPLAY_NAME));
                    String mimeType = albums.getString(
                            albums.getColumnIndex(MediaStore.MediaColumns.MIME_TYPE));
                    Uri uri = getUri(albums);
                    int count = albums.getInt(albums.getColumnIndex(COLUMN_COUNT));

                    list.add(new Album(String.valueOf(bucketId),uri,bucketDisplayName,count));
                    totalCount += count;
                }
                if (albums.moveToFirst()) {
                    allAlbumCoverUri = getUri(albums);
                }
            }
            list.add(0,new Album("-1",allAlbumCoverUri,"ALL",totalCount));
        }else {

            Map<Long, Long> countMap = new HashMap<>();
            if (albums != null) {
                while (albums.moveToNext()) {
                    long bucketId = albums.getLong(albums.getColumnIndex(COLUMN_BUCKET_ID));
                    Long count = countMap.get(bucketId);
                    if (count == null) {
                        count = 1L;
                    } else {
                        count++;
                    }
                    countMap.put(bucketId, count);
                }
            }
            if (albums != null) {
                if (albums.moveToFirst()) {
                    allAlbumCoverUri = getUri(albums);

                    Set<Long> done = new HashSet<>();

                    do {
                        long bucketId = albums.getLong(albums.getColumnIndex(COLUMN_BUCKET_ID));

                        if (done.contains(bucketId)) {
                            continue;
                        }

                        long fileId = albums.getLong(
                                albums.getColumnIndex(MediaStore.Files.FileColumns._ID));
                        String bucketDisplayName = albums.getString(
                                albums.getColumnIndex(COLUMN_BUCKET_DISPLAY_NAME));
                        String mimeType = albums.getString(
                                albums.getColumnIndex(MediaStore.MediaColumns.MIME_TYPE));
                        Uri uri = getUri(albums);
                        long count = countMap.get(bucketId);

                        list.add(new Album(String.valueOf(bucketId),uri,bucketDisplayName,count));


                        done.add(bucketId);

                        totalCount += count;
                    } while (albums.moveToNext());
                }
            }
            list.add(0,new Album("-1",allAlbumCoverUri,"ALL",totalCount));
        }
        return list;
    }











    private static Uri getUri(Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Files.FileColumns._ID));
        String mimeType = cursor.getString(
                cursor.getColumnIndex(MediaStore.MediaColumns.MIME_TYPE));
        Uri contentUri;

        if (MimeType.isImage(mimeType)) {
            contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        } else if (MimeType.isVideo(mimeType)) {
            contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        } else {
            // ?
            contentUri = MediaStore.Files.getContentUri("external");
        }

        Uri uri = ContentUris.withAppendedId(contentUri, id);
        return uri;
    }

    private static boolean beforeAndroidTen() {
        return android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.Q;
    }

}
