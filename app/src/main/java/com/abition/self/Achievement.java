package com.abition.self;

/**
 * Created by KlousesSun on 16/5/1.
 */
public class Achievement {
    private int image;
    private String title;
    private String description;
    private boolean accomplished;

    public boolean isAccomplished() {
        return accomplished;
    }

    public void setAccomplished(boolean accomplished) {
        this.accomplished = accomplished;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Achievement(int image, String title, String description, boolean accomplished) {
        this.image = image;
        this.title = title;
        this.description = description;
        this.accomplished = accomplished;
    }
}
