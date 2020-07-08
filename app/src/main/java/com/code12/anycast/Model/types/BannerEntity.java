package com.code12.anycast.Model.types;

public class BannerEntity {
    public BannerEntity(String link, String title, String img) {
        this.link = link;
        this.title = title;
        this.img = img;
    }

    public String title;
    public String img;
    public String link;
}
