package dev.timoangerer.message.web;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dev.timoangerer.message.db.MessageRepository;
import dev.timoangerer.message.model.Message;
import dev.timoangerer.messaging.MessagingService;
import dev.timoangerer.messaging.templating.MissingTemplateVariableException;

@Path("/messages")
public class MessageResource {

    private final MessageRepository messageRepository;
    private final MessagingService messsMessagingService;

    public MessageResource(MessageRepository messageRepository, MessagingService messagingService) {
        this.messageRepository = messageRepository;
        this.messsMessagingService = messagingService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Message> all(@QueryParam("contactId") UUID contactId) {
        
        return messageRepository.findAll(contactId);    
    }

    @POST
    public Response sendMessage(Message message) {
	
        message.setSentByContact(false);
        message.setSentAt(Timestamp.from(Instant.now()));

        try {
            messsMessagingService.sendMessage(message);
            return Response.ok("Message sent").build();
        } catch (MissingTemplateVariableException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Err: " + e.getMessage()).build();
        }
    }
}
