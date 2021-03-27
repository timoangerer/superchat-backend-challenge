package dev.timoangerer.webhooks.whatsapp.model;

public class WhatsappMessage {
    private String msgId;
    private String waId;
    private String profileName;
    private String text;
    private String timestamp;
    
    public WhatsappMessage(String msgId, String waId, String profileName, String text, String timestamp) {
        this.msgId = msgId;
        this.waId = waId;
        this.profileName = profileName;
        this.text = text;
        this.timestamp = timestamp;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getWaId() {
        return waId;
    }

    public void setWaId(String waId) {
        this.waId = waId;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    
}
