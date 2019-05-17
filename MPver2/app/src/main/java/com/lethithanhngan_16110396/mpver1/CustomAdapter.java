package com.lethithanhngan_16110396.mpver1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lethithanhngan_16110396.mpver1.Model.Song;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends BaseAdapter {
    Context context;
    List<Song> arrSong;



    public CustomAdapter(Context context, List<Song> arrSong) {

        this.context = context;
        this.arrSong = arrSong;
    }


    @Override
    public int getCount() {
        return this.arrSong.size();
    }

    @Override
    public Object getItem(int position) {
        return arrSong.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"WrongViewCast", "ResourceType"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.row_songlist,null);
        ImageButton imgIconSong=(ImageButton) convertView.findViewById(R.id.imgIconSong);
        //TextView txtID=(TextView) convertView.findViewById(R.id.txtID);
        TextView txtName = (TextView) convertView.findViewById(R.id.txtSongName);

        final LinearLayout ln = (LinearLayout)convertView.findViewById(R.id.linear);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) imgIconSong.getLayoutParams();

        Song song=arrSong.get(position);
        //txtID.setText(song.getSong_Id());
//        if(song.getSong_Id()%2==0){
//            imgIconSong.setImageResource(R.raw.iconmusic1);
//        }
//        else {
//            imgIconSong.setImageResource(R.raw.iconmusic2);
//        }

        imgIconSong.setImageResource(R.raw.iconmusic1);
        params.height = 90;
        params.width = 100;
        imgIconSong.setLayoutParams(params);
        imgIconSong.setScaleType(ImageView.ScaleType.FIT_CENTER);
        txtName.setText(song.getSong_Name());

//        LayoutInflater inflater= this.context.getLayoutInflater();
//        View row = inflater.inflate(this.resource,null);
//
//        TextView txtName = (TextView) row.findViewById(R.id.txtSongName);
//
//        ImageView imgbtnIcon = (ImageView) row.findViewById(R.id.imgIconSong);
//        /** Set data to row*/
//        final Song song= this.arrSong.get(position);
//        txtName.setText(song.getSong_Name());
//
//
          return convertView;
    }


}
