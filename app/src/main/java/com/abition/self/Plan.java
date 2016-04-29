package com.abition.self;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by KlousesSun on 16/4/9.
 */
public class Plan implements Comparable <Plan> {
    private _Plan plan;
    private String title;
    private int themeImage;
    private int steak;
    private int target;
    private Status status;

    @Override
    public int compareTo(Plan another) {
        return this.status.ordinal() - another.status.ordinal();
    }

    public enum Status {
        PROCESSING, FINISHED, FAILED
    }

    static public Map<Integer, Integer> themeStyle;

    static {
        themeStyle = new HashMap<>();
        themeStyle.put(R.drawable.spring, 0xFF009688);
        themeStyle.put(R.drawable.autumn, 0xFFEF6C00);
        themeStyle.put(R.drawable.winter, 0xFF1E88E5);
        themeStyle.put(R.drawable.night, 0xFF455A64);
        themeStyle.put(R.drawable.love, 0xFFE91E63);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public Plan(String title, int themeImage, int steak, int target, Status status) {
        this.title = title;
        this.themeImage = themeImage;
        this.steak = steak;
        this.status = status;
        this.target = target;
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
