package fi.metropolia.valemajoneesi.moodprint;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class EmotionTracker {
    private static EmotionTracker instance = null;
    private static List<Emotion> emotions = new ArrayList<Emotion>();
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
        List<String> charArray = new ArrayList<String>();
        char[] chars = "abcde".toCharArray();
        int i = 0;
        for(int mood = 0; mood < 5; mood++) {
            char ch = chars[mood];
            for(int enrg = 0; enrg < 5; enrg++) {
                emotions.add(new Emotion(
                        ctx.getResources().getStringArray(R.array.emotions)[i],
                        mood-2, enrg, icons[i]
                ));
                i++;
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
}
