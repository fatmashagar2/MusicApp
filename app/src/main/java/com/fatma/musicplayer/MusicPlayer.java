package com.naglaa.musicplayer;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MusicPlayer extends AppCompatActivity {
    // Declaration of Views
    SeekBar seekBarTime;
    SeekBar seekBarValue;
    TextView tvStartTime;
    TextView tvTotalTime;
    Button btnPlayer;
    MediaPlayer musicPlayer;
    TextView tvTitle,tvArtist;


    // Handler for updating the SeekBar and TextView
    Handler handler = new Handler();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_music_player);

        // Initialize Views
        seekBarTime = findViewById(R.id.seekbar_Time);
        seekBarValue = findViewById(R.id.seekbar_value);
        tvStartTime = findViewById(R.id.tv_Time);
        tvTotalTime = findViewById(R.id.total_Time);
        btnPlayer = findViewById(R.id.btn_play);
        tvTitle=findViewById(R.id.tv_title);
        tvArtist=findViewById(R.id.tv_artist);


        // Initialize MediaPlayer
        musicPlayer = MediaPlayer.create(this, R.raw.sound);
        musicPlayer.seekTo(0);
        musicPlayer.setVolume(0.5f, 0.5f);
        musicPlayer.setLooping(true);

        // Set total duration of the music
        String duration = miliseconedToString(musicPlayer.getDuration());
        tvTotalTime.setText(duration);

        // Initialize volume SeekBar
        seekBarValue.setProgress(50);
        seekBarValue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float volume = progress / 100f;
                musicPlayer.setVolume(volume, volume);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Handle start of touch event
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Handle stop of touch event
            }
        });

        // Initialize seekBarTime
        seekBarTime.setMax(musicPlayer.getDuration());
        seekBarTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    musicPlayer.seekTo(progress);
                    seekBar.setProgress(progress);

                    // Update the start time TextView
                    String currentTime = miliseconedToString(progress);
                    tvStartTime.setText(currentTime);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Handle start of touch event
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Handle stop of touch event
            }
        });

        // Update SeekBar and TextView while music is playing
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (musicPlayer != null && musicPlayer.isPlaying()) {
                    int currentPosition = musicPlayer.getCurrentPosition();
                    seekBarTime.setProgress(currentPosition);

                    // Update the start time TextView
                    String currentTime = miliseconedToString(currentPosition);
                    tvStartTime.setText(currentTime);
                }
                handler.postDelayed(this, 1000); // Update every second
            }
        });

        // Button play/pause listener
        btnPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.btn_play) {
                    if (musicPlayer.isPlaying()) {
                        musicPlayer.pause();
                        btnPlayer.setBackgroundResource(R.drawable.play_button);
                    } else {
                        musicPlayer.start();
                        btnPlayer.setBackgroundResource(R.drawable.music_layer);
                    }
                }
            }
        });
    }

    public String miliseconedToString(int time) {
        String elapsedTime = "";
        int minutes = time / 1000 / 60;
        int seconds = time / 1000 % 60;
        elapsedTime = minutes + ":";
        if (seconds < 10) {
            elapsedTime += "0";
        }
        elapsedTime += seconds;

        return elapsedTime;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()== android.R.id.home){
            finish();
            if(musicPlayer.isPlaying()){
                musicPlayer.stop();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
