package dev.timoangerer.message.web;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import dev.timoangerer.contact.db.ContactRepository;
import dev.timoangerer.message.db.MessageRepository;
import dev.timoangerer.message.model.Message;

@Path("/messages")
public class MessageResource {

    private final MessageRepository messageRepository;
    private final ContactRepository contactRepository;

    public MessageResource(MessageRepository messageRepository, ContactRepository contactRepository) {
        this.messageRepository = messageRepository;
        this.contactRepository = contactRepository;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Message> all(@QueryParam("contactId") UUID contactId) {
        
        return messageRepository.findAll(contactId);    
    }
}
