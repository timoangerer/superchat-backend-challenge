package dev.timoangerer.message.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = { "id" }, allowGetters = true)
public class Message {
    private UUID id;
    private UUID contactId;
    private Boolean sentByContact;
    private UUID channelId;
    private String sentAt;

    public Message(UUID id, UUID contactId, Boolean sentByContact, UUID channelId, String timestamp) {
        this.id = id;
        this.contactId = contactId;
        this.sentByContact = sentByContact;
        this.channelId = channelId;
        this.sentAt = timestamp;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getContactId() {
        return contactId;
    }

    public void setContactId(UUID contactId) {
        this.contactId = contactId;
    }

    public Boolean getSentByContact() {
        return sentByContact;
    }

    public void setSentByContact(Boolean sentByContact) {
        this.sentByContact = sentByContact;
    }

    public UUID getChannelId() {
        return channelId;
    }

    public void setChannelId(UUID channelId) {
        this.channelId = channelId;
    }

    public String getTimestamp() {
        return sentAt;
    }

    public void setTimestamp(String timestamp) {
        this.sentAt = timestamp;
    }

}
