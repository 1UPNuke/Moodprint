package fi.metropolia.valemajoneesi.moodprint;

import android.content.Context;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class EmotionTracker {
    private static EmotionTracker instance = null;
    private static List<Emotion> emotions = new ArrayList<>();
    private static List<Integer> selectedEmotions = new ArrayList<>();
    private static TreeMap<LocalDateTime, List> history = new TreeMap<>();

    private static final Integer[] icons = {
            R.drawable.emotion_a0,
            R.drawable.emotion_a1,
            R.drawable.emotion_a2,
            R.drawable.emotion_a3,
            R.drawable.emotion_a4,
            R.drawable.emotion_b0,
            R.drawable.emotion_b1,
            R.drawable.emotion_b2,
            R.drawable.emotion_b3,
            R.drawable.emotion_b4,
            R.drawable.emotion_c0,
            R.drawable.emotion_c1,
            R.drawable.emotion_c2,
            R.drawable.emotion_c3,
            R.drawable.emotion_c4,
            R.drawable.emotion_d0,
            R.drawable.emotion_d1,
            R.drawable.emotion_d2,
            R.drawable.emotion_d3,
            R.drawable.emotion_d4,
            R.drawable.emotion_e0,
            R.drawable.emotion_e1,
            R.drawable.emotion_e2,
            R.drawable.emotion_e3,
            R.drawable.emotion_e4
    };

    public EmotionTracker(Context ctx) {
        int i = 0;
        for(int mood = 0; mood < 5; mood++) {
            for(int enrg = 0; enrg < 5; enrg++) {
                emotions.add(new Emotion(
                        ctx.getResources().getStringArray(R.array.emotions)[i],
                        mood-2, enrg, icons[i]
                ));
                i++;
            }
        }
        //Really hacky solution to display emotions in the correct order
        //Reverse columns
        for (i = 0; i < 3; i++ ) {
            int k = 4 - i;
            for ( int j = 0; j < 5; ++j ) {
                Emotion temp = emotions.get(i * 5 + j);
                emotions.set(i * 5 + j, emotions.get(k * 5 + j));
                emotions.set(k * 5 + j, temp);
            }
        }
    }

    public static EmotionTracker getInstance() {
        return instance;
    }

    public static void setInstance(Context ctx) {
        if(instance == null) {
            instance = new EmotionTracker(ctx);
        }
    }

    public List<Emotion> getEmotions() {
        return emotions;
    }

    public Emotion getEmotion(int index) {
        return emotions.get(index);
    }

    public void selectEmotion(int id) {
        if(!selectedEmotions.contains(id)) {
            selectedEmotions.add(id);
        }
    }

    public void unselectEmotion(int id) {
        if(selectedEmotions.contains(id)) {
            selectedEmotions.remove(selectedEmotions.indexOf(id));
        }
    }
    public void unselectAll() {
        selectedEmotions = new ArrayList<>();
    }

    public Map<LocalDateTime, List> getHistory() {
        return history;
    }
    public List<Emotion> lastHistoryEntry() { return history.lastEntry().getValue(); }
    public void storeSelectedInHistory() {
        history.put(LocalDateTime.now(), getSelected());
        unselectAll();
    }

    public List<Emotion> getSelected() {
        List<Emotion> returnList = new ArrayList<Emotion>();
        for(int id : selectedEmotions) {
            returnList.add(getEmotion(id));
        }
        return returnList;
    }

    public static double averageEnergy(List<Emotion> emotions) {
        double sum = 0;
        for(Emotion emo : emotions) {
            sum += emo.getEnergy();
        }
        return sum/emotions.size();
    }

    public static double averageMood(List<Emotion> emotions) {
        double sum = 0;
        for(Emotion emo : emotions) {
            sum += emo.getMood();
        }
        return sum/emotions.size();
    }
}
