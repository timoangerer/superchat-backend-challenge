package dev.timoangerer.message.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = { "id" }, allowGetters = true)
public class Message {
    private UUID id;
    private UUID contactId;
    private UUID channelId;
    private Boolean sentByContact;
    private String sentAt;

    public Message(UUID contactId, UUID channelId, Boolean sentByContact, String sentAt) {
        this.contactId = contactId;
        this.channelId = channelId;
        this.sentByContact = sentByContact;
        this.sentAt = sentAt;
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

    public String getSentAt() {
        return sentAt;
    }

    public void setSentAt(String sentAt) {
        this.sentAt = sentAt;
    }
}
