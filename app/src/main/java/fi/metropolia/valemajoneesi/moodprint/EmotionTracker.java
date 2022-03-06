package fi.metropolia.valemajoneesi.moodprint;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class EmotionTracker {
    private static EmotionTracker instance = null;
    private static List<Emotion> emotions = new ArrayList<Emotion>();
    private static List<Emotion> displayEmotions = new ArrayList<Emotion>();
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
        for(int mood = 0; mood < 5; mood++) {
            char ch = chars[mood];
            for(int enrg = 0; enrg < 5; enrg++) {
                int i = 5*enrg + mood;
                emotions.add(new Emotion(
                        ctx.getResources().getStringArray(R.array.emotions)[i],
                        mood-2, enrg, icons[i]
                ));
            }
        }
        constructDisplayList();
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

    private void constructDisplayList() {
        //Hacky solution to reorder emotions for display
        displayEmotions = emotions;
        for ( int i = 0; i < 3; i++ ) {
            int k = 4 - i;
            for ( int j = 0; j < 5; ++j ) {
                Emotion temp = displayEmotions.get(i * 5 + j);
                displayEmotions.set(i * 5 + j, displayEmotions.get(k * 5 + j));
                displayEmotions.set(k * 5 + j, temp);
            }
        }
    }

    public Emotion getDisplayEmotion(int index) {
        return displayEmotions.get(index);
    }
}
