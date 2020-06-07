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
import com.example.btvnday12.model.Player;

import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder> {
    private Context mContext;
    private List<Player> mPlayers;

    public PlayerAdapter(Context mContext, List<Player> mPlayers) {
        this.mContext = mContext;
        this.mPlayers = mPlayers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.layout_player,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ((ViewHolder)holder).onBind(position);
    }

    @Override
    public int getItemCount() {
        return mPlayers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAvatar;
        TextView tvName,tvNational;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAvatar=itemView.findViewById(R.id.cv_avatar);
            tvName=itemView.findViewById(R.id.tv_nameplayer);
            tvNational=itemView.findViewById(R.id.tv_national);
        }

        public void onBind(int position) {
            Player player = mPlayers.get(position);
            Glide.with(mContext).load(player.getImage()).placeholder(R.drawable.girl).error(R.drawable.ic_error_black_24dp).into(ivAvatar);
            tvName.setText(player.getTen());
            tvNational.setText(player.getDoi());
        }
    }
}
