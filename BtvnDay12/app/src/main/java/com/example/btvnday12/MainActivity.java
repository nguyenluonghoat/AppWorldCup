package com.example.btvnday12;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;

import com.example.btvnday12.adapter.MusicAdapter;
import com.example.btvnday12.listener.OnRecycClickListener;
import com.example.btvnday12.model.Music;

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

public class MainActivity extends AppCompatActivity implements OnRecycClickListener {
    private RecyclerView rvMusic;
    private List<Music> mMusic;
    private MusicAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        rvMusic = findViewById(R.id.rv_music);
        mMusic = new ArrayList<>();
        adapter = new MusicAdapter(this,this,mMusic);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvMusic.setLayoutManager(manager);
        rvMusic.setHasFixedSize(true);

        rvMusic.setAdapter(adapter);
        new DownLoadMusicFromUrl().execute("https://storage.googleapis.com/automotive-media/music.json");

    }
    class DownLoadMusicFromUrl extends AsyncTask<String,Void,List<Music>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Music> doInBackground(String... args) {
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
                List<Music> musics = parseJson(result.toString());
                return musics;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private List<Music> parseJson(String json) {
            List<Music> musics = new ArrayList<>();
            try {
                JSONObject root = new JSONObject(json);
                JSONArray vd = root.getJSONArray("music");
                for(int i=0;i<vd.length();i++){
                    JSONObject ms = vd.getJSONObject(i);
                    String title = ms.getString("title");
                    String album = ms.getString("album");
                    String artist = ms.getString("artist");
                    String genre = ms.getString("genre");
                    String source = ms.getString("source");
                    String image = ms.getString("image");
                    int trackNumber = ms.getInt("trackNumber");
                    int totalTrackCount = ms.getInt("totalTrackCount");
                    int duration = ms.getInt("duration");
                    String site = ms.getString("site");
                    Music m = new Music(title, album, artist, genre, source, image, trackNumber, totalTrackCount, duration, site);
                    musics.add(m);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return musics;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(List<Music> musics) {
            super.onPostExecute(musics);
            mMusic.clear();
            mMusic.addAll(musics);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void OnItemClickListener(int position) {

    }

    @Override
    public void OnItemLongClickListener(int position) {

    }
}
