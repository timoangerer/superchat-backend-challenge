package dev.timoangerer.webhooks.whatsapp.web;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import dev.timoangerer.channel.whatsapp.db.WhatsappChannelRepository;
import dev.timoangerer.contact.db.ContactRepository;
import dev.timoangerer.contact.model.Contact;
import dev.timoangerer.message.db.MessageRepository;
import dev.timoangerer.message.model.Message;
import dev.timoangerer.webhooks.whatsapp.model.WhatsappMessage;

@Path("/wh/whatsapp")
public class WhatsappMessageResource {

    private final WhatsappChannelRepository whatsappChannelRepository;

    private final ContactRepository contactRepository;

    private final MessageRepository messageRepository;

    public WhatsappMessageResource(WhatsappChannelRepository whatsappChannelRepository, ContactRepository contactRepository, MessageRepository messageRepository) {
      this.whatsappChannelRepository = whatsappChannelRepository;
      this.contactRepository = contactRepository;
      this.messageRepository = messageRepository;
    }

    @POST
    public void receiveMessage(WhatsappMessage whatsappMessage) {

        Contact contact = whatsappChannelRepository.findContactByWaId(whatsappMessage.getWaId());
        UUID channelId = null;

        if (contact == null) {
            Contact newContact = new Contact(null, whatsappMessage.getProfileName(), null);
            contact = contactRepository.insert(newContact);

            channelId = whatsappChannelRepository.insert(contact, whatsappMessage.getWaId());
            System.out.println("Created new contact from new WA channel");
        } else {
            channelId = whatsappChannelRepository.findChannelIdByWaId(whatsappMessage.getWaId());
        }

        System.out.println("Contact from WA endpoint:");
        System.out.println(contact.getName());

        // System.out.println("Message content:");
        // System.out.println(contact.getId());
        // System.out.println(channelId);
        // System.out.println(whatsappMessage.getTimestamp());

        Message message = new Message(contact.getId(), channelId, true, Timestamp.from(Instant.ofEpochSecond(Integer.parseInt(whatsappMessage.getTimestamp()))), whatsappMessage.getText());
        System.out.println("Message content:" + message.toString());


        Message savedMessage = messageRepository.insert(message);
        System.out.println("Id of created message: " + savedMessage.getId());

    }
}
