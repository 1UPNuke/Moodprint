package fi.metropolia.valemajoneesi.moodprint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.Collection;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                EmotionTracker.getInstance().unselectAll();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        EmotionTracker.setInstance(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Collection<List> emotionListCollection = EmotionTracker.getInstance().getHistory().values();

        ListView lv = (ListView) findViewById(R.id.listView);

        for(List<Emotion> surveyAnswer : emotionListCollection) {
            //A list of emotions chosen in a survey
            for(Emotion emotion : surveyAnswer) {
                //Individual emotion
            }
        }
    }
}