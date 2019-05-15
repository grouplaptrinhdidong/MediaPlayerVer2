package com.lethithanhngan_16110396.mpver1;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RemoteViews;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lethithanhngan_16110396.mpver1.ListBaiHat;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import com.lethithanhngan_16110396.mpver1.Model.Song;
import com.lethithanhngan_16110396.mpver1.ListBaiHat;
public class PlayerActivity extends AppCompatActivity {
    private static final String ACTION_NOTIFICATION_BUTTON_CLICK = "ACTION_NOTIFICATION_BUTTON_CLICK" ;
    public static final String EXTRA_BUTTON_CLICKED ="EXTRA_BUTTON_CLICKED" ;
    private static final String CHANNEL_ID ="CHANNEL_ID" ;
    ImageButton imgbtnPlay, imgbtnNext, imgbtnPrevious, imgbtnPause, imgbtnRepeat, imgbtnShuffle;
    TextView tvSongName, txtStartTime, txtfinalTime;
    private Handler myHandler = new Handler();;
    SeekBar seekbar;
    String snameSong;

    Intent notIntent ;
    Intent intentNofitication;
    Notification.Builder builder ;
    NotificationManager notificationService;
   public static MediaPlayer myMediaPlayer;
    public  static  int position;
    public  static ArrayList<Song> mySongs = ListBaiHat.listSongs;
    Thread updateSeekBar;
    private double startTime = 0;
    private double finalTime = 0;
    public static int oneTimeOnly = 0;
    public boolean isRepeat = true, isShuffle = true;
    private static final int MY_NOTIFICATION_ID = 12345;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        imgbtnPlay = (ImageButton) findViewById(R.id.imgbtnPlay);
        imgbtnNext = (ImageButton) findViewById(R.id.imgbtnNext);
        imgbtnPrevious = (ImageButton) findViewById(R.id.imgbtnPrevious);
        imgbtnRepeat = (ImageButton) findViewById(R.id.imgbtnRepeat);
        imgbtnShuffle = (ImageButton) findViewById(R.id.imgbtnShuffle);
        tvSongName = (TextView) findViewById(R.id.tvSongName);
        txtStartTime = (TextView) findViewById(R.id.startTime);
        txtfinalTime = (TextView) findViewById(R.id.finalTime);
        seekbar = (SeekBar) findViewById(R.id.seekBarSong);
        seekbar.setClickable(false);
        updateSeekBar = new Thread(){

            @Override
            public void run() {

                int totalDuraltion = myMediaPlayer.getDuration();
                int currentPosition = 0;
                while (currentPosition<totalDuraltion){
                    try{
                        sleep(500);
                        currentPosition = myMediaPlayer.getCurrentPosition();
                        seekbar.setProgress(currentPosition);
                    }
                    catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        };



        if (myMediaPlayer != null){
            myMediaPlayer.stop();
            myMediaPlayer.release();
        }

        Intent i = getIntent();
        Bundle bundle = i.getExtras();

        Log.d("Listnek", String.valueOf(mySongs.size()));
        for(int j = 0; j < mySongs.size();j++){
            Log.d("Listnek", String.valueOf(mySongs.get(j).getSong_File()));
        }
        //mySongs = (ArrayList) bundle.getParcelableArrayList("songs");

       // snameSong = mySongs.get(position).getName().toString();
        final String songName = i.getStringExtra("songname");
        tvSongName.setText(songName);
        tvSongName.setSelected(true);

        position = bundle.getInt("pos", 0);
        Uri u = Uri.parse(mySongs.get(position).getSong_File());
        Log.d("Listnek", u.toString());
        myMediaPlayer = MediaPlayer.create(getApplicationContext(), u);

        myMediaPlayer.start();

        showNotification(songName);
       // NotificationMess(songName);
//

        Log.d("Listnek", String.valueOf(myMediaPlayer.isPlaying()));
        finalTime = myMediaPlayer.getDuration();
        startTime = myMediaPlayer.getCurrentPosition();
        seekbar.setMax(myMediaPlayer.getDuration());
        if (oneTimeOnly == 0) {
            seekbar.setMax((int) finalTime);
            oneTimeOnly = 1;
        }
        txtfinalTime.setText(String.format("%d:%d",
                TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                finalTime)))
        );

        txtStartTime.setText(String.format("%d:%d",
                TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                startTime)))
        );

        seekbar.setProgress((int)startTime);
        myHandler.postDelayed(UpdateSongTime,100);

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                myMediaPlayer.seekTo(seekBar.getProgress());
            }
        });

        imgbtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekbar.setMax(myMediaPlayer.getDuration());

                if (myMediaPlayer.isPlaying()){
                    imgbtnPlay.setImageResource(R.drawable.play);
                    myMediaPlayer.pause();
                    //notificationService.cancel(MY_NOTIFICATION_ID);
                }
                else {
                    imgbtnPlay.setImageResource(R.drawable.pause);
                    myMediaPlayer.start();
                    showNotification(songName);
                   // NotificationMess(songName);
                }
            }
        });

        imgbtnNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // check if next song is there or not
                if(position < (mySongs.size() - 1)){
                    myMediaPlayer.stop();
                    Intent i = getIntent();
                    Bundle bundle = i.getExtras();
                    //mySongs = (ArrayList) bundle.getParcelableArrayList("songs");
                    snameSong = mySongs.get(position+1).getSong_Name();
                    tvSongName.setText(snameSong);
                    tvSongName.setSelected(true);
                    Uri u = Uri.parse(mySongs.get(position + 1).getSong_File());
                    myMediaPlayer = MediaPlayer.create(getApplicationContext(), u);
                    myMediaPlayer.start();
                    showNotification(snameSong);
                    //NotificationMess(snameSong);
                    finalTime = myMediaPlayer.getDuration();
                    startTime = myMediaPlayer.getCurrentPosition();
                    txtfinalTime.setText(String.format("%d:%d",
                            TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                            TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                            finalTime)))
                    );
                    position = position + 1;
                }else{
                    // play first song
                    myMediaPlayer.stop();
                    Intent i = getIntent();
                    Bundle bundle = i.getExtras();
                    //mySongs = (ArrayList) bundle.getParcelableArrayList("songs");
                    snameSong = mySongs.get(0).getSong_Name();
                    tvSongName.setText(snameSong);
                    tvSongName.setSelected(true);
                    Uri u = Uri.parse(mySongs.get(0).getSong_File());
                    myMediaPlayer = MediaPlayer.create(getApplicationContext(), u);
                    myMediaPlayer.start();
                    showNotification(snameSong);
                    //NotificationMess(snameSong);
                    finalTime = myMediaPlayer.getDuration();
                    startTime = myMediaPlayer.getCurrentPosition();
                    txtfinalTime.setText(String.format("%d:%d",
                            TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                            TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                            finalTime)))
                    );
                    position = 0;

                }
            }
        });
        imgbtnPrevious.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // check if previous song is there or not
                if(position > 0){
                    myMediaPlayer.stop();
                    Intent i = getIntent();
                    Bundle bundle = i.getExtras();
                   // mySongs = (ArrayList) bundle.getParcelableArrayList("songs");
                    snameSong = mySongs.get(position-1).getSong_Name();
                    tvSongName.setText(snameSong);
                    tvSongName.setSelected(true);
                    Uri u = Uri.parse(mySongs.get(position - 1).getSong_File());
                    myMediaPlayer = MediaPlayer.create(getApplicationContext(), u);
                    myMediaPlayer.start();
                    //NotificationMess(snameSong);
                    showNotification(snameSong);
                    finalTime = myMediaPlayer.getDuration();
                    startTime = myMediaPlayer.getCurrentPosition();
                    txtfinalTime.setText(String.format("%d:%d",
                            TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                            TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                            finalTime)))
                    );
                    position = position - 1;
                }else{
                    myMediaPlayer.stop();
                    // play first song
                    Intent i = getIntent();
                    Bundle bundle = i.getExtras();
                    //mySongs = (ArrayList) bundle.getParcelableArrayList("songs");
                    snameSong = mySongs.get(mySongs.size() - 1).getSong_Name();
                    tvSongName.setText(snameSong);
                    tvSongName.setSelected(true);
                    Uri u = Uri.parse(mySongs.get(mySongs.size() - 1).getSong_File());
                    myMediaPlayer = MediaPlayer.create(getApplicationContext(), u);
                    myMediaPlayer.start();
                    showNotification(snameSong);
                    //NotificationMess(snameSong);
                    finalTime = myMediaPlayer.getDuration();
                    startTime = myMediaPlayer.getCurrentPosition();
                    txtfinalTime.setText(String.format("%d:%d",
                            TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                            TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                            finalTime)))
                    );
                    position = 0;
                }
            }
        });
        imgbtnRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (isRepeat) {
                    isRepeat = false;
                    Toast.makeText(getApplicationContext(), "Repeat is OFF", Toast.LENGTH_SHORT).show();
                    //imgbtnRepeat.setImageResource(R.drawable.repeat);
                } else {
                    Toast.makeText(getApplicationContext(), "Repeat is ON", Toast.LENGTH_SHORT).show();
                    imgbtnRepeat.setImageResource(R.drawable.repeat);
                    //kiểm tra thanh seekbar đã chạy đến cuối chưa, nếu rồi thì mới chạy lại bài đó từ đầu
                    while(txtStartTime.getText().toString() == txtfinalTime.getText().toString()) {
                        // make repeat to true
                        isRepeat = true;

                        // make shuffle to false
                        isShuffle = false;

                        //imgbtnShuffle.setImageResource(R.drawable.shuffle);
                        Uri u = Uri.parse(mySongs.get(position).toString());
                        myMediaPlayer = MediaPlayer.create(getApplicationContext(), u);
                        myMediaPlayer.start();
                        finalTime = myMediaPlayer.getDuration();
                        startTime = myMediaPlayer.getCurrentPosition();
                        txtfinalTime.setText(String.format("%d:%d",
                                TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                                TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                                finalTime)))
                        );
                    }
                }
            }
        });
    }
    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = myMediaPlayer.getCurrentPosition();
            txtStartTime.setText(String.format("%d:%d",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                    toMinutes((long) startTime)))
            );
            seekbar.setProgress((int)startTime);
            myHandler.postDelayed(this, 100);
        }
    };



    private PendingIntent onButtonNotificationClick(@IdRes int id) {
        Intent intent = new Intent(ACTION_NOTIFICATION_BUTTON_CLICK);
        intent.putExtra(EXTRA_BUTTON_CLICKED, id);
        return PendingIntent.getBroadcast(this, id, intent, 0);
    }

    private void showNotification(String songName) {

        RemoteViews notificationLayout =
                new RemoteViews(getPackageName(), R.layout.activity_custom_notification);

        notificationLayout.setOnClickPendingIntent(R.id.imgbtnPause,
                onButtonNotificationClick(R.id.imgbtnPause));
        notificationLayout.setOnClickPendingIntent(R.id.imgbtnPlay,
                onButtonNotificationClick(R.id.imgbtnPlay));
        notificationLayout.setTextViewText(R.id.tvNameSong, songName );
        Notification
                notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setCustomContentView(notificationLayout)
                .build();
        NotificationManager notificationManager =
                (android.app.NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }

    public void ChuyenIcon(){
        if(NotificationReceiver.flag==1){
            imgbtnPlay.setImageResource(R.mipmap.play);
        }
    }


}
