package com.lethithanhngan_16110396.mpver1;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.lethithanhngan_16110396.mpver1.Model.Song;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import com.lethithanhngan_16110396.mpver1.ListBaiHat;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "SQLite";

    // Phiên bản
    private static final int DATABASE_VERSION = 1;

    // Tên cơ sở dữ liệu.
    private static final String DATABASE_NAME = "MPver3";


    // Tên bảng: Note.
    private static final String TABLE_SONG = "Song";

    private static final String COLUMN_SONG_ID ="Song_Id";
    private static final String COLUMN_SONG_NAME ="Song_Name";
    private static final String COLUMN_SONG_FILE = "Song_file";
    public MyDatabaseHelper(Context context)  {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Tạo các bảng.
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "MyDatabaseHelper.onCreate ... ");
        // Script tạo bảng.
        String script = "CREATE TABLE " + TABLE_SONG + "("
                + COLUMN_SONG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_SONG_NAME + " TEXT,"
                + COLUMN_SONG_FILE + " TEXT" + ")";
        // Chạy lệnh tạo bảng.
        db.execSQL(script);
       // addSongFromSD();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.i(TAG, "MyDatabaseHelper.onUpgrade ... ");

        // Hủy (drop) bảng cũ nếu nó đã tồn tại.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SONG);


        // Và tạo lại.
        onCreate(db);
    }

    // Nếu trong bảng Song chưa có dữ liệu,
    // Chèn vào mặc định 2 bản ghi.
    public void createDefaultSongIfNeed()  {
        int count = this.getSongsCount();
        if(count ==0 ) {
            Song sg1 = new Song("a",
                    "a.mp3");
            Song sg2 = new Song("b",
                    "b.mp3");
            this.addSong(sg1);
            this.addSong(sg2);
        }
    }


    public void addSong(Song song) {
        Log.i(TAG, "MyDatabaseHelper.addSong ... " + song.getSong_Name());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_SONG_NAME, song.getSong_Name());
        values.put(COLUMN_SONG_FILE, song.getSong_File());


        // Trèn một dòng dữ liệu vào bảng.
        db.insert(TABLE_SONG, null, values);


        // Đóng kết nối database.
        db.close();
    }


    public Song getSong(int id) {
        Log.i(TAG, "MyDatabaseHelper.getSong ... " + id);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_SONG, new String[] { COLUMN_SONG_ID,
                        COLUMN_SONG_NAME, COLUMN_SONG_FILE }, COLUMN_SONG_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Song song = new Song(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return note
        return song;
    }


    public ArrayList<Song> getAllSong() {
        Log.i(TAG, "MyDatabaseHelper.getAllNotes ... " );

        ArrayList<Song> songList = new ArrayList<Song>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SONG;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        // Duyệt trên con trỏ, và thêm vào danh sách.
        if (cursor.moveToFirst()) {
            do {
                Song song = new Song();
                song.setSong_Id(Integer.parseInt(cursor.getString(0)));
                song.setSong_Name(cursor.getString(1));
                song.setSong_File(cursor.getString(2));

                // Thêm vào danh sách.
                songList.add(song);
            } while (cursor.moveToNext());
        }

        // return note list
        return songList;
    }

    public int getSongsCount() {
        Log.i(TAG, "MyDatabaseHelper.getNotesCount ... " );

        String countQuery = "SELECT  * FROM " + TABLE_SONG;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }

    public ArrayList<File> findSong(File file){

        ArrayList<File> arrayList = new ArrayList<>();

        File[] files = file.listFiles();

        for(File singleFile: files){

            if (singleFile.isDirectory() && !singleFile.isHidden()){
                arrayList.addAll(findSong(singleFile));
            }
            else{
                //chỉ lấy những bài hát trong zing mp3
                if (singleFile.getName().endsWith(".mp3") &&
                        (singleFile.getPath().startsWith("/storage/emulated/0/Zing MP3/") || singleFile.getPath().startsWith("/storage/emulated/0/Download/"))/*&& singleFile.getPath().startsWith("/storage/emulated/0/Zing MP3/")*/){
                    arrayList.add(singleFile);
                }
            }
        }

        return arrayList;
    }
    public void addSongFromSD() {

        final ArrayList<File> mySongs = findSong(Environment.getExternalStorageDirectory());

        for(int i = 0; i < mySongs.size(); i++){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_SONG_NAME, mySongs.get(i).getName().toString().replace(".mp3",""));
            values.put(COLUMN_SONG_FILE, mySongs.get(i).getPath());
            // Trèn một dòng dữ liệu vào bảng.
            db.insert(TABLE_SONG, null, values);
            db.close();
        }

        // Đóng kết nối database.

    }
    /*public int updateSinger(Singer singer) {
        Log.i(TAG, "MyDatabaseHelper.updateNote ... "  + singer.getNameSinger());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_SINGER_NAME, singer.getNameSinger());
        values.put(COLUMN_SINGER_IMG, singer.getImgSinger());

        // updating row
        return db.update(TABLE_SINGER, values, COLUMN_SINGER_ID+ " = ?",
                new String[]{String.valueOf(singer.getIdSinger())});
    }

    public void deleteSinger(Singer singer) {
        Log.i(TAG, "MyDatabaseHelper.updateNote ... " + singer.getNameSinger() );

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SINGER, COLUMN_SINGER_ID+ " = ?",
                new String[] { String.valueOf(singer.getIdSinger()) });
        db.close();
    }*/
}
