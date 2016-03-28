package com.handy.androidclub.chat;

public class Chat {

    private String message;
    private String author;

    // Needed for Firebase to rebuild the object
    private Chat() {
    }

    Chat(String message, String author) {
        this.message = message;
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public String getAuthor() {
        return author;
    }
}
