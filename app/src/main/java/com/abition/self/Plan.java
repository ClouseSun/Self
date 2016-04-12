package com.abition.self;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by KlousesSun on 16/4/9.
 */
public class Plan {
    private String title;
    private int themeImage;
    private int steak;

    static public Map<Integer, Integer> themeStyle;

    static {
        themeStyle = new HashMap<>();
        themeStyle.put(R.drawable.spring, 0xFF009688);
        themeStyle.put(R.drawable.autumn, 0xFFEF6C00);
        themeStyle.put(R.drawable.winter, 0xFF1E88E5);
        themeStyle.put(R.drawable.night, 0xFF455A64);
        themeStyle.put(R.drawable.love, 0xFFE91E63);
    }

    public Plan(String title, int themeImage, int steak) {
        this.title = title;
        this.themeImage = themeImage;
        this.steak = steak;
    }

    public void setThemeImage(int themeImage) {
        this.themeImage = themeImage;
    }

    public void setSteak(int steak) {
        this.steak = steak;
    }

    public int getThemeImage() {
        return themeImage;
    }

    public int getSteak() {
        return steak;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
