package com.example.yourrpg.model;

public class MessageModal {
    private String message;
    private String sender;
    private int icon;

    // constructor.
    public MessageModal(String message, String sender) {
        this.message = message;
        this.sender = sender;
    }

    public MessageModal(String message, String sender, int icon) {
        this.message = message;
        this.sender = sender;
        this.icon = icon;
    }

    // getter and setter methods.
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
