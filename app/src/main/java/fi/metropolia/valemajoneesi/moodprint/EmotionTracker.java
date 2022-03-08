package fi.metropolia.valemajoneesi.moodprint;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class EmotionTracker {
    private static List<Emotion> emotions = new ArrayList<>();
    private static List<Integer> selectedEmotions = new ArrayList<>();
    private static TreeMap<Long, List> history = new TreeMap<>();
    private static boolean initialized = false;

    // I wish there was a better way to do this
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

    public static void initialize(Context ctx) {
        if(initialized) {
            return;
        }
        initialized = true;
        //Build emotion array with mood from -2 to 2 and energy from 0 to 5
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
        // Really hacky solution to display emotions in the correct order
        // Reverse columns
        for (i = 0; i < 3; i++ ) {
            int k = 4 - i;
            for ( int j = 0; j < 5; ++j ) {
                Emotion temp = emotions.get(i * 5 + j);
                emotions.set(i * 5 + j, emotions.get(k * 5 + j));
                emotions.set(k * 5 + j, temp);
            }
        }
    }

    public static List<Emotion> getEmotions() {
        return emotions;
    }

    public static Emotion getEmotion(int index) {
        return emotions.get(index);
    }

    public static void selectEmotion(int id) {
        if(!selectedEmotions.contains(id)) {
            selectedEmotions.add(id);
        }
    }

    public static void unselectEmotion(int id) {
        if(selectedEmotions.contains(id)) {
            selectedEmotions.remove(selectedEmotions.indexOf(id));
        }
    }
    public static void unselectAll() {
        selectedEmotions = new ArrayList<>();
    }

    public static Map<Long, List> getHistory() {
        return history;
    }
    public static void storeSelectedInHistory() {
        history.put(LocalDateTime.now().toInstant(ZoneOffset.UTC).getEpochSecond(), getSelected());
        unselectAll();
    }

    public static List<Emotion> getSelected() {
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

    public static void saveHistory(Context ctx) {
        try {
            File file = new File(ctx.getFilesDir(), "history");
            // create a new file with an ObjectOutputStream
            FileOutputStream out = new FileOutputStream(file);
            ObjectOutputStream oout = new ObjectOutputStream(out);

            // write something in the file
            oout.writeObject(getHistory());
            oout.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void loadHistory(Context ctx) {
        try {
            File file = new File(ctx.getFilesDir(), "history");
            // create an ObjectInputStream for the file we created before
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            history = (TreeMap<Long, List>) ois.readObject();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
