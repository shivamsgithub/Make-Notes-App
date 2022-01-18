package com.application.makenotes.activity;

public class Notes {
    private int id;
    private String title;
    private String description;
    private byte[] images;
    private int imagesInt;
    private String timeStamp;

    public Notes() {
    }

    public Notes(int id, String title, byte[] images, int imagesInt , String timeStamp) {
        this.id = id;
        this.title = title;
        this.images = images;
        this.imagesInt = imagesInt;
        this.timeStamp = timeStamp;
    }

    public Notes(String title, String timeStamp) {
        this.title = title;
        this.timeStamp = timeStamp;
    }

    public Notes(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public byte[] getImages() {
        return images;
    }

    public void setImages(byte[] images) {
        this.images = images;
    }

    public int getImagesInt() {

        Byte b = new Byte(String.valueOf(getImages()));
        int imagesInt = b.intValue();

        return imagesInt;
    }

    public void setImagesInt(int imagesInt) {
        this.imagesInt = imagesInt;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}

