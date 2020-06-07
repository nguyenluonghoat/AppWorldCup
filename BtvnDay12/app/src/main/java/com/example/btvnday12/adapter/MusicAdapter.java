package com.example.btvnday12.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.btvnday12.R;
import com.example.btvnday12.listener.OnRecycClickListener;
import com.example.btvnday12.model.Music;

import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder> {
    private Context mContext;
    private OnRecycClickListener mListener;
    private List<Music> mMusics;

    public MusicAdapter(Context mContext, OnRecycClickListener mListener, List<Music> mMusics) {
        this.mContext = mContext;
        this.mListener = mListener;
        this.mMusics = mMusics;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.layout_music, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ((ViewHolder) holder).onBind(position);
    }

    @Override
    public int getItemCount() {
        return mMusics.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivMusic;
        TextView tvName, tvAlbum;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMusic = itemView.findViewById(R.id.iv_music);
            tvName = itemView.findViewById(R.id.tv_name);
            tvAlbum = itemView.findViewById(R.id.tv_album);
        }

        public void onBind(int position) {
            Music music = mMusics.get(position);
            tvName.setText(music.getTitle());
            tvAlbum.setText(music.getAlbum());
            String url = "https://storage.googleapis.com/automotive-media/" + music.getImage();
            Glide.with(mContext)
                    .load(url)
                    .placeholder(R.drawable.girl)
                    .error(R.drawable.ic_error_black_24dp)
                    .into(ivMusic);
        }
    }
}
