package com.naglaa.musicplayer;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ListMusicPlayer extends AppCompatActivity {
    public static final int REQUEST_PERMISSION = 99;
    ArrayList<Songs> songsArrayList;
    ListView lvSongs;
    private SongAdapter songAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_music_player);

        lvSongs = findViewById(R.id.lv_songs);
        songsArrayList = new ArrayList<>();
        songAdapter = new SongAdapter(this, songsArrayList);
        lvSongs.setAdapter(songAdapter);

/*
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
        } else {
            getSongs();
        }
*/


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
        } else {

            getSongs();
        }


        lvSongs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Songs song = songsArrayList.get(position);
                Intent openMusicPlayer = new Intent(ListMusicPlayer.this, MusicPlayer.class);
                openMusicPlayer.putExtra("title", song.getSongName());
                openMusicPlayer.putExtra("artist", song.getArtistName());
                openMusicPlayer.putExtra("path", song.getPath());
                startActivity(openMusicPlayer);
            }
        });

        // Set edge-to-edge content
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBarsInsets.left, systemBarsInsets.top, systemBarsInsets.right, systemBarsInsets.bottom);
            return insets;
        });
    }



   /* private void getSongs() {
        ContentResolver contentResolver = getContentResolver();
        Uri uriSong = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor = contentResolver.query(uriSong, null, null, null, null);

        if (songCursor != null) {
            int indexTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int indexArtist = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int indexPath = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);

            while (songCursor.moveToNext()) {
                String title = songCursor.getString(indexTitle);
                String artist = songCursor.getString(indexArtist);
                String path = songCursor.getString(indexPath);
                Log.d("Songs", "Title: " + title + ", Artist: " + artist + ", Path: " + path);
                songsArrayList.add(new Songs(title, artist, path));
            }
            songCursor.close();
        }
        songAdapter.notifyDataSetChanged();
    }*/

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("Permission", "Permission granted");
                getSongs();
            } else {
                Log.d("Permission", "Permission denied");
                // Handle permission denial gracefully, perhaps show a message
            }
        }
    }

    private void getSongs() {
        ContentResolver contentResolver = getContentResolver();
        Uri uriSong = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor = contentResolver.query(uriSong, null, null, null, null);

        if (songCursor != null) {
            Log.d("Songs", "Number of songs found: " + songCursor.getCount());
            int indexTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int indexArtist = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int indexPath = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);

            while (songCursor.moveToNext()) {
                String title = songCursor.getString(indexTitle);
                String artist = songCursor.getString(indexArtist);
                String path = songCursor.getString(indexPath);
                Log.d("Songs", "Title: " + title + ", Artist: " + artist + ", Path: " + path);
                songsArrayList.add(new Songs(title, artist, path));
            }
            songCursor.close();
        } else {
            Log.d("Songs", "Cursor is null");
        }
        songAdapter.notifyDataSetChanged();
    }


}
