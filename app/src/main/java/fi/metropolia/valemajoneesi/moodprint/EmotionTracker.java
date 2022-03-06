package fi.metropolia.valemajoneesi.moodprint;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;

public class EmotionTracker {
    private static EmotionTracker instance = null;
    private static List<Emotion> emotions = new ArrayList<Emotion>();
    private static List<Integer> selectedEmotions = new ArrayList<Integer>();
    //private static Dictionary<Date, List> emotionHistory = new Dictionary<Date, List>();

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
        char[] chars = "abcde".toCharArray();
        //Really hacky solution to display emotions in the correct order
        for(int mood = 4; mood >= 0; mood--) {
            char ch = chars[mood];
            for(int enrg = 4; enrg >= 0; enrg--) {
                int i = 5*enrg + mood;
                emotions.add(new Emotion(
                        ctx.getResources().getStringArray(R.array.emotions)[i],
                        mood-2, enrg, icons[i]
                ));
            }
        }
        for ( int i = 0; i < 3; i++ ) {
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
        selectedEmotions = new ArrayList<Integer>();
    }

    public List<Emotion> getSelected() {
        List<Emotion> returnList = new ArrayList<Emotion>();
        for(int id : selectedEmotions) {
            returnList.add(getEmotion(id));
        }
        return returnList;
    }
}
