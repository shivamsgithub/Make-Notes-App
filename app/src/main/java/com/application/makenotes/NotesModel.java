package com.application.makenotes;

public class NotesModel {
    private String name;
    private String url;
    private String topic;

    public NotesModel(String name, String url, String topic) {
        this.name = name;
        this.url = url;
        this.topic = topic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
