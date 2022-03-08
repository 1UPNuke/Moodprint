package fi.metropolia.valemajoneesi.moodprint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class HistoryActivity extends AppCompatActivity {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle back button
        switch (item.getItemId()) {
            case android.R.id.home:
                EmotionTracker.unselectAll();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        EmotionTracker.initialize(this);

        // Set back button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        List<String> emoStringList = new ArrayList<>();
        for(Map.Entry entr : EmotionTracker.getHistory().entrySet()) {
            String str = LocalDateTime.ofInstant(
                    Instant.ofEpochSecond((long)entr.getKey()),
                    TimeZone.getDefault().toZoneId()
            ).toString()+": ";

            for (Emotion emo : (List<Emotion>) entr.getValue()) {
                str += emo.getName() + " ";
            }
            emoStringList.add(str);
        }

        Collections.reverse(emoStringList);

        ListView lv = (ListView) findViewById(R.id.listView);

        lv.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, emoStringList));
    }
}