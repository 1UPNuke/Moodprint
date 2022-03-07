package fi.metropolia.valemajoneesi.moodprint;

import java.io.Serializable;

public class Emotion implements Serializable {
    private String name = "neutral";
    private int mood = 0;
    private int energy = 0;
    private int imageId = 0;

    public Emotion(String name, int mood, int energy, int imageId) {
        this.name = name;
        this.mood = mood;
        this.energy = energy;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public int getMood() {
        return mood;
    }

    public int getEnergy() {
        return energy;
    }

    public int getImageId() {
        return imageId;
    }

    @Override
    public String toString() {
        return name;
    }
}
