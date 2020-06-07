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
import com.example.btvnday12.model.Match;

import java.util.List;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.ViewHolder> {
    private Context mContext;
    private List<Match> mMatches;

    public MatchAdapter(Context mContext, List<Match> mMatches) {
        this.mContext = mContext;
        this.mMatches = mMatches;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.layout_match,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ((ViewHolder)holder).onBind(position);
    }

    @Override
    public int getItemCount() {
        return mMatches.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivTeamOne,ivTeamTwo;
        TextView tvTeamOne,tvTeamTwo,tvDay,tvHour,tvChanel;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivTeamOne=itemView.findViewById(R.id.cv_teamone);
            ivTeamTwo=itemView.findViewById(R.id.cv_teamtwo);
            tvTeamOne=itemView.findViewById(R.id.tv_teamone);
            tvTeamTwo=itemView.findViewById(R.id.tv_teamtwo);
            tvDay=itemView.findViewById(R.id.tv_day);
            tvHour=itemView.findViewById(R.id.tv_hour);
            tvChanel=itemView.findViewById(R.id.tv_chanel);
        }

        public void onBind(int position) {
            Match match = mMatches.get(position);
            Glide.with(mContext).load(match.getQuocky1()).placeholder(R.drawable.girl).error(R.drawable.ic_error_black_24dp).into(ivTeamOne);
            Glide.with(mContext).load(match.getQuocky2()).placeholder(R.drawable.girl).error(R.drawable.ic_error_black_24dp).into(ivTeamTwo);
            tvTeamOne.setText(match.getDoi1());
            tvTeamTwo.setText(match.getDoi2());
            tvDay.setText(match.getNgay());
            tvHour.setText(match.getGio());
            tvChanel.setText(match.getKenh());

        }
    }
}
