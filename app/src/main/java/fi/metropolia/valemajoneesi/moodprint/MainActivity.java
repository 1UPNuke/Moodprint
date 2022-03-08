package fi.metropolia.valemajoneesi.moodprint;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Make settings icon
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle back button
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

        EmotionTracker.initialize(this);

        EmotionTracker.loadHistory(this);

        GraphView enrgGraph = (GraphView) findViewById(R.id.energyGraph);
        GraphView moodGraph = (GraphView) findViewById(R.id.moodGraph);

        // Set GraphView styling
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

        // If there are emotions in history then draw graphs
        if(!(EmotionTracker.getEmotions().size() < 1)) {
            // Get history
            Set<Map.Entry<Long, List>> hist;
            hist = EmotionTracker.getHistory().entrySet();

            // Define series and their styles
            LineGraphSeries<DataPoint> enrgSeries = new LineGraphSeries<DataPoint>();
            enrgSeries.setColor(getColor(R.color.energy_yellow));
            enrgSeries.setDrawDataPoints(true);
            enrgSeries.setDataPointsRadius(10);

            LineGraphSeries<DataPoint> moodSeries = new LineGraphSeries<DataPoint>();
            moodSeries.setDrawDataPoints(true);
            moodSeries.setDataPointsRadius(10);

            // Add data points to series
            double i = 0;
            for(Map.Entry<Long, List> entry : hist) {
                double energy = EmotionTracker.averageEnergy(entry.getValue());
                enrgSeries.appendData(new DataPoint(i, energy), true, 50);

                double mood = EmotionTracker.averageMood(entry.getValue());
                moodSeries.appendData(new DataPoint(i, mood), true, 50);

                i++;
            }

            //Display series and scroll graphs to the end
            enrgGraph.addSeries(enrgSeries);
            moodGraph.addSeries(moodSeries);

            enrgGraph.getViewport().scrollToEnd();
            moodGraph.getViewport().scrollToEnd();
        }
    }

    public void historyOnClick(View view) {
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }

    public void surveyOnClick(View view) {
        Intent intent = new Intent(this, SurveyActivity.class);
        startActivity(intent);
    }
}