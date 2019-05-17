package com.lethithanhngan_16110396.mpver1;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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
import java.util.Map;

public class ListBaiHat extends AppCompatActivity {
    ListView myListViewForSongs;
    String[] items;
    MyDatabaseHelper dbhelper = new MyDatabaseHelper(this);
    public static ArrayList<Song> listSongs;

    CustomAdapter customAdaper=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_bai_hat);
        myListViewForSongs = (ListView) findViewById(R.id.mySongListView);

        //dbhelper.createDefaultSongIfNeed();
        dbhelper.addSongFromSD();
        display();



    }
    void display(){


        listSongs = dbhelper.getAllSong();
        customAdaper=new CustomAdapter(this,listSongs);
        myListViewForSongs.setAdapter(customAdaper);
        customAdaper.notifyDataSetChanged();
//        items = new String[listSongs.size()];
//
//        for (int i=0; i<listSongs.size(); i++){
//            items[i] = listSongs.get(i).getSong_Name();
//
//        }

//        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, items);
//        myListViewForSongs.setAdapter(myAdapter);


        myListViewForSongs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String songName =  myListViewForSongs.getItemAtPosition(position).toString();
                startActivity(new Intent(getApplicationContext(), PlayerActivity.class)/*.putExtra("songs", listSongs)*/
                        .putExtra("songname",songName)
                        .putExtra("pos", position));
            }
        });


    }
//    public void arrayWithIcon(){
//
//        myListViewForSongs = (ListView) findViewById(R.id.mySongListView);
//        listSongs = new ArrayList<>();
//        /**
//         * @param MainActivity.this
//         * @param R.layout.item
//         * @param bookList
//         * */
////        customAdaper = new CustomAdapter(ListBaiHat.this,R.layout.row_songlist,listSongs);
////        myListViewForSongs.setAdapter(customAdaper);
//    }
}
