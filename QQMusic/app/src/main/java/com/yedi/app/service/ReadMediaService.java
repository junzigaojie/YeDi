package com.yedi.app.service;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;

import com.yedi.app.bean.MusicInfo;
import com.yedi.app.db.MusicDBHelper;
import com.yedi.app.tools.PlayerFinal;

public class ReadMediaService extends IntentService {
    public static final String ACTION_READ_MEDIA="com.yedi.app.action_read_media";

    private MusicDBHelper localDbHelper;

    public ReadMediaService()
    {
        super("ReadMediaService");
    }

    /**
     * 该方法在主线程中执行，原则上，由于是主线程，可进行UI操作，但是好的编程风格，service不处理activity的内容。
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("ReadMediaService", "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent!=null){
            String action=intent.getAction();
            if(action.equals(ACTION_READ_MEDIA)){
                Log.i("gaojie","duquwenjian");
                ContentResolver conRes = getContentResolver();
                Cursor cur = conRes.query(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,
                        MediaStore.Audio.Media.SIZE + ">?",
                        new String[] { "1000" }, MediaStore.Audio.Media.TITLE
                                + " asc");
                Log.e(PlayerFinal.TAG, "查询到多媒体文件一共" + cur.getCount());
                int titleIndex = cur.getColumnIndex(MediaStore.Audio.Media.TITLE);
                int artistIndex = cur.getColumnIndex(MediaStore.Audio.Media.ARTIST);
                int albumIndex = cur.getColumnIndex(MediaStore.Audio.Media.ALBUM);
                int pathIndex = cur.getColumnIndex(MediaStore.Audio.Media.DATA);
                int durationIndex = cur
                        .getColumnIndex(MediaStore.Audio.Media.DURATION);
                int sizeIndex = cur.getColumnIndex(MediaStore.Audio.Media.SIZE);
                int fileNameIndex = cur
                        .getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME);
                localDbHelper = new MusicDBHelper(getApplicationContext());
                localDbHelper.clearLocal();
                if (cur.getCount() != 0) {
                    cur.moveToFirst();
                    do {
                        MusicInfo music = new MusicInfo();
                        music.setTitle(cur.getString(titleIndex));
                        music.setArtist(cur.getString(artistIndex));
                        music.setAlbum(cur.getString(albumIndex));
                        music.setPath(cur.getString(pathIndex));
                        music.setDuration(cur.getLong(durationIndex));
                        music.setSize(cur.getLong(sizeIndex));
                        String fileName = cur.getString(fileNameIndex);
                        String lrcName = fileName.replace(".mp3", ".lrc");
                        music.setLyric_file_name(lrcName);
                        Long i = localDbHelper.insertLocal(music);
                        Log.i(PlayerFinal.TAG, "insert---->" + i);
                        localDbHelper.insertArtist(music);
                        localDbHelper.insertAlbum(music);
                        cur.moveToNext();
                    } while (!cur.isAfterLast());
                    /*
                    curLocal = localDbHelper.queryLocalByID();
                    Log.e(PlayerFinal.TAG, "curLocal" + curLocal.getCount());
                    handler.sendMessage(handler.obtainMessage(1, curLocal));
                    */

                } else {
                    /*
                    Log.e(PlayerFinal.TAG, "本地没有多媒体文件" + cur.getCount());
                    handler.sendMessage(handler.obtainMessage(3));*/
                }
            }
        }
    }

    public static final void startReadMediaFile(Context context){
        Intent intent=new Intent(context,ReadMediaService.class);
        intent.setAction(ACTION_READ_MEDIA);
        context.startService(intent);


    }
}
