package com.lethithanhngan_16110396.mpver1.Model;

import java.io.Serializable;

public class Song implements Serializable {
    public int Song_Id;
    public String Song_Name;
    public String Song_File;
    public Song() {
    }
    public Song(int song_Id) {
        Song_Id = song_Id;
    }

    public Song(int song_Id, String song_Name, String song_File) {
        Song_Id = song_Id;
        Song_Name = song_Name;
        Song_File = song_File;
    }

    public Song(String song_Name, String song_File) {
        Song_Name = song_Name;
        Song_File = song_File;
    }

    public int getSong_Id() {
        return Song_Id;
    }

    public void setSong_Id(int song_Id) {
        Song_Id = song_Id;
    }

    public String getSong_Name() {
        return Song_Name;
    }

    public void setSong_Name(String song_Name) {
        Song_Name = song_Name;
    }

    public String getSong_File() {
        return Song_File;
    }

    public void setSong_File(String song_File) {
        Song_File = song_File;
    }
}
