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
import com.example.btvnday12.adapter.MatchAdapter;
import com.example.btvnday12.model.Match;

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
public class MatchFragment extends Fragment {
    private static MatchFragment mInstance;
    private RecyclerView rvMatch;
    private MatchAdapter adapter;
    private List<Match> mMatch;

    private MatchFragment() {
        // Required empty public constructor
    }
    public static MatchFragment getInstance(){
        if (mInstance==null){
            mInstance = new MatchFragment();
        }
        return mInstance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_match, container, false);
        initViews(v);
        return v;
    }

    private void initViews(View v) {
        rvMatch=v.findViewById(R.id.rv_match);
        mMatch = new ArrayList<>();
        adapter = new MatchAdapter(getContext(),mMatch);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvMatch.setHasFixedSize(true);
        rvMatch.setLayoutManager(manager);
        rvMatch.setAdapter(adapter);
        new DownLoadMatchUrl().execute("https://obscure-gorge-93598.herokuapp.com/lichthidau/all");


    }
    class DownLoadMatchUrl extends AsyncTask<String,Void,List<Match>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Match> doInBackground(String... args) {
            String link = args[0];
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
                List<Match> matches = parseJson("{\"match\" :"+result.toString()+"}");
                return matches;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private List<Match> parseJson(String json) {
            List<Match> matches = new ArrayList<>();
            try {
                JSONObject root = new JSONObject(json);
                JSONArray m = root.getJSONArray("match");
                for(int i=0;i<m.length();i++){
                    JSONObject mc = m.getJSONObject(i);
                    String doi1 = mc.getString("doi1");
                    String quocky1 = mc.getString("quocky1");
                    String doi2 = mc.getString("doi2");
                    String quocky2 = mc.getString("quocky2");
                    String ngay = mc.getString("ngay");
                    String gio = mc.getString("gio");
                    String kenh = mc.getString("kenh");
                    Match matc  = new Match(doi1,quocky1,doi2,quocky2,ngay,gio,kenh);
                    matches.add(matc);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return matches;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(List<Match> matches) {
            super.onPostExecute(matches);
            mMatch.clear();
            mMatch.addAll(matches);
            adapter.notifyDataSetChanged();
        }
    }
}
