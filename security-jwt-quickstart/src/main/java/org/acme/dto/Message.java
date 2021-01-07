package org.acme.dto;

public class Message {
    
    public String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Message(){}

    public Message(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Message [content=" + content + "]";
    }

    
}
