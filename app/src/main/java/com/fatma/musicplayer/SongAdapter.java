package com.naglaa.musicplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class SongAdapter extends BaseAdapter {

    private Context context;
    private List<Songs> songsList;

    public SongAdapter(Context context, List<Songs> songsList) {
        this.context = context;
        this.songsList = songsList;
    }

    @Override
    public int getCount() {
        return songsList.size();
    }

    @Override
    public Object getItem(int position) {
        return songsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_song, parent, false);
        }

        Songs song = songsList.get(position);

        TextView songNameTextView = convertView.findViewById(R.id.tv_song_name);
        TextView artistNameTextView = convertView.findViewById(R.id.tv_artist_name);
        TextView pathTextView = convertView.findViewById(R.id.tv_path);

        songNameTextView.setText(song.getSongName());
        artistNameTextView.setText(song.getArtistName());
        pathTextView.setText(song.getPath());

        return convertView;
    }
}
