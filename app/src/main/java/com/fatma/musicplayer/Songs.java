package com.naglaa.musicplayer;

public class Songs {
    private String songName;
    private String artistName;
    private String path;

    public Songs(String songName, String artistName, String path) {
        this.songName = songName;
        this.artistName = artistName;
        this.path = path;
    }

    public String getSongName() {
        return songName;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getPath() {
        return path;
    }
}
