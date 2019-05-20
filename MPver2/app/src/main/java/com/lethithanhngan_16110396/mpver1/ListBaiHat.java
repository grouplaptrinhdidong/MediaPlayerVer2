package com.lethithanhngan_16110396.mpver1;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

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

public class ListBaiHat extends AppCompatActivity implements OnclickList {
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

        dbhelper.addSongFromSD();
        display();



    }
    void display(){

        listSongs = dbhelper.getAllSong();

        customAdaper=new CustomAdapter(this,listSongs);
        customAdaper.registerChildItemClick(this);
        myListViewForSongs.setAdapter(customAdaper);
        customAdaper.notifyDataSetChanged();

    }

    @Override
    public void onClickList(String name, int position) {
                startActivity(new Intent(getApplicationContext(), PlayerActivity.class)
                .putExtra("songname",name)
                .putExtra("pos", position));
    }


}
