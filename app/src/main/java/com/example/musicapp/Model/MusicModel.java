package com.example.musicapp.Model;

public class MusicModel {
    private String title;
    private int link;

    public MusicModel(String title, int link) {
        this.title = title;
        this.link = link;
    }

    public MusicModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLink() {
        return link;
    }

    public void setLink(int link) {
        this.link = link;
    }
}
