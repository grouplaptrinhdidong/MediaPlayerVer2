package com.lethithanhngan_16110396.mpver1;

import android.annotation.SuppressLint;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;


import com.lethithanhngan_16110396.mpver1.Model.Song;


import java.util.List;



public class CustomAdapter extends BaseAdapter implements ListAdapter {
    Context context;
    List<Song> arrSong;
    private OnclickList onclick;


    public void registerChildItemClick(OnclickList onclick) {
        this.onclick = onclick;
    }

    public void unRegisterChildItemClick() {
        this.onclick = null;
    }


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
    public View getView(final int position, View convertView, ViewGroup parent) {


        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.row_songlist,null);
        ImageButton imgIconSong=(ImageButton) convertView.findViewById(R.id.imgIconSong);

        TextView txtName = (TextView) convertView.findViewById(R.id.txtSongName);

        final LinearLayout ln = (LinearLayout)convertView.findViewById(R.id.linear);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) imgIconSong.getLayoutParams();

        final Song song=arrSong.get(position);


        imgIconSong.setImageResource(R.raw.iconmusic1);
        params.height = 90;
        params.width = 100;
        imgIconSong.setLayoutParams(params);
        imgIconSong.setScaleType(ImageView.ScaleType.FIT_CENTER);
        txtName.setText(song.getSong_Name());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclick.onClickList(song.getSong_Name(),song.getSong_Id()-1);
            }
        });

          return convertView;
    }


}
