package com.lethithanhngan_16110396.mpver1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class NotificationReceiver extends BroadcastReceiver {


    //MediaPlayer myMediaPlayer;
    public  static int flag=0;
    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getIntExtra(PlayerActivity.EXTRA_BUTTON_CLICKED, -1)== R.id.imgbtnPause){
           PlayerActivity.myMediaPlayer.pause();
           flag=1;
        }
        else {
            if(intent.getIntExtra(PlayerActivity.EXTRA_BUTTON_CLICKED, -1)== R.id.imgbtnPlay){
                PlayerActivity.myMediaPlayer.start();
                flag=0;
            }
        }

    }
}
