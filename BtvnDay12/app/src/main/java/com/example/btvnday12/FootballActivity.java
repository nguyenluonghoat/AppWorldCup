package com.example.btvnday12;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.btvnday12.adapter.FootBallPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class FootballActivity extends AppCompatActivity {
    private TabLayout tabFootBall;
    private ViewPager vpFootBall;
    private FootBallPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_football);
        initViewsFootball();
    }

    private void initViewsFootball() {
        tabFootBall = findViewById(R.id.tab_football);
        vpFootBall = findViewById(R.id.vp_football);
        adapter = new FootBallPagerAdapter(getSupportFragmentManager());
        vpFootBall.setAdapter(adapter);
        tabFootBall.setupWithViewPager(vpFootBall);
    }
}
