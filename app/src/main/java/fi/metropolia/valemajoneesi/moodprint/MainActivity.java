package fi.metropolia.valemajoneesi.moodprint;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settingsButton:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EmotionTracker.setInstance(this);

        GraphView enrgGraph = (GraphView) findViewById(R.id.energyGraph);
        GraphView moodGraph = (GraphView) findViewById(R.id.moodGraph);

        enrgGraph.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        moodGraph.getGridLabelRenderer().setHorizontalLabelsVisible(false);

        enrgGraph.getViewport().setYAxisBoundsManual(true);
        enrgGraph.getViewport().setMinY(0);
        enrgGraph.getViewport().setMaxY(4);
        enrgGraph.getViewport().setScrollable(true);

        moodGraph.getViewport().setYAxisBoundsManual(true);
        moodGraph.getViewport().setMinY(-2);
        moodGraph.getViewport().setMaxY(2);
        moodGraph.getViewport().setScrollable(true);

        double i = 0;
        if(!(EmotionTracker.getInstance().getEmotions().size() < 1)) {
            Set<Map.Entry<LocalDateTime, List>> hist;
            hist = EmotionTracker.getInstance().getHistory().entrySet();

            LineGraphSeries<DataPoint> enrgSeries = new LineGraphSeries<DataPoint>();
            enrgSeries.setColor(Color.YELLOW);
            enrgSeries.setDrawDataPoints(true);
            enrgSeries.setDataPointsRadius(10);

            LineGraphSeries<DataPoint> moodSeries = new LineGraphSeries<DataPoint>();
            moodSeries.setDrawDataPoints(true);
            moodSeries.setDataPointsRadius(10);

            for(Map.Entry<LocalDateTime, List> entry : hist) {
                double energy = EmotionTracker.averageEnergy(entry.getValue());
                enrgSeries.appendData(new DataPoint(i, energy), true, 50);

                double mood = EmotionTracker.averageMood(entry.getValue());
                moodSeries.appendData(new DataPoint(i, mood), true, 50);

                Log.d("TAG", "Energy: "+energy+" Mood: "+mood+" i: "+i);

                i++;
            }

            enrgGraph.addSeries(enrgSeries);
            moodGraph.addSeries(moodSeries);

            enrgGraph.getViewport().scrollToEnd();
            moodGraph.getViewport().scrollToEnd();
        }
    }

    public void mapOnClick(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public void surveyOnClick(View view) {
        Intent intent = new Intent(this, SurveyActivity.class);
        startActivity(intent);
    }
}