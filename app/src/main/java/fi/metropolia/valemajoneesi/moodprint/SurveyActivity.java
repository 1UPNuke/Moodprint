package fi.metropolia.valemajoneesi.moodprint;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class SurveyActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_survey);

        EmotionTracker.setInstance(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        RecyclerView rv = findViewById(R.id.RecyclerView);
        // Attach the adapter to the recyclerview to populate items
        rv.setAdapter(new EmotionAdapter());
        // Set layout manager to position the items
        rv.setLayoutManager(new GridLayoutManager(this, 5));
    }

    public void okOnClick(View view) {
        EmotionTracker.getInstance().storeSelectedInHistory();
        EmotionTracker.getInstance().saveHistory(this);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}