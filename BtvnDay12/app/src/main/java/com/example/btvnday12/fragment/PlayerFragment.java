package com.example.btvnday12.fragment;


import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.btvnday12.R;
import com.example.btvnday12.adapter.PlayerAdapter;
import com.example.btvnday12.model.Player;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlayerFragment extends Fragment {
    private static PlayerFragment mInstance;
    private List<Player> mPlayers;
    private PlayerAdapter adapter;
    private RecyclerView rvPlayer;

    private PlayerFragment() {
        // Required empty public constructor
    }
    public static PlayerFragment getInstance(){
        if(mInstance==null){
            mInstance= new PlayerFragment();
        }
        return mInstance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_player, container, false);
        initViews(v);
        return v;
    }

    private void initViews(View v) {
        rvPlayer = v.findViewById(R.id.rv_player);
        mPlayers = new ArrayList<>();
        adapter = new PlayerAdapter(getContext(),mPlayers);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvPlayer.setHasFixedSize(true);
        rvPlayer.setLayoutManager(manager);
        rvPlayer.setAdapter(adapter);
        new DownLoadPlayerUrl().execute("https://obscure-gorge-93598.herokuapp.com/cauthu/all");
    }

    class DownLoadPlayerUrl extends AsyncTask<String,Void,List<Player>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Player> doInBackground(String... args) {
            String link=args[0];
            try {
                URL url = new URL(link);
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder result = new StringBuilder();
                String line = reader.readLine();
                while (line!=null){
                    result.append(line);
                    line=reader.readLine();
                }
                List<Player> players = parseJson("{\"player\" :"+result.toString()+"}");
                return players;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        private List<Player> parseJson(String json) {
            List<Player> players = new ArrayList<>();
            try {
                JSONObject root = new JSONObject(json);
                JSONArray pl = root.getJSONArray("player");
                for(int i=0;i<pl.length();i++){
                    JSONObject play = pl.getJSONObject(i);
                    String ten = play.getString("ten");
                    String doi = play.getString("doi");
                    String image = play.getString("image");
                    Player player = new Player(ten,doi,image);
                    players.add(player);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return players;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(List<Player> players) {
            super.onPostExecute(players);
            mPlayers.clear();
            mPlayers.addAll(players);
            adapter.notifyDataSetChanged();
        }
    }
}
