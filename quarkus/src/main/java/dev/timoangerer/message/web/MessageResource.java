package dev.timoangerer.message.web;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.timoangerer.contact.db.ContactRepository;
import dev.timoangerer.message.db.MessageRepository;
import dev.timoangerer.message.model.Message;
import dev.timoangerer.messaging.MessagingService;

@Path("/messages")
public class MessageResource {

    private final MessageRepository messageRepository;
    private final ContactRepository contactRepository;
    private final MessagingService messsMessagingService;

    public MessageResource(MessageRepository messageRepository, ContactRepository contactRepository, MessagingService messagingService) {
        this.messageRepository = messageRepository;
        this.contactRepository = contactRepository;
        this.messsMessagingService = messagingService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Message> all(@QueryParam("contactId") UUID contactId) {
        
        return messageRepository.findAll(contactId);    
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public void sendMessage(Message message) {
	
        message.setSentByContact(false);
        message.setSentAt(Timestamp.from(Instant.now()));

        messsMessagingService.sendMessage(message);
    }
}
