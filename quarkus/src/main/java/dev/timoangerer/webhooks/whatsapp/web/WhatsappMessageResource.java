package dev.timoangerer.webhooks.whatsapp.web;

import java.util.UUID;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import dev.timoangerer.channel.whatsapp.db.WhatsappChannelRepository;
import dev.timoangerer.contact.db.ContactRepository;
import dev.timoangerer.contact.model.Contact;
import dev.timoangerer.webhooks.whatsapp.model.WhatsappMessage;

@Path("/wh/whatsapp")
public class WhatsappMessageResource {

    private final WhatsappChannelRepository whatsappChannelRepository;

    private final ContactRepository contactRepository;

    public WhatsappMessageResource(WhatsappChannelRepository whatsappChannelRepository, ContactRepository contactRepository) {
      this.whatsappChannelRepository = whatsappChannelRepository;
      this.contactRepository = contactRepository;
    }

    @POST
    public void receiveMessage(WhatsappMessage whatsappMessage) {

        Contact contact = whatsappChannelRepository.findContactByWaId(whatsappMessage.getWaId());
        
        if (contact == null) {
            Contact newContact = new Contact(null, whatsappMessage.getProfileName(), null);
            contact = contactRepository.insert(newContact);

            whatsappChannelRepository.insert(contact, whatsappMessage.getWaId());
            System.out.println("Created new contact from new WA channel");
        }

        System.out.println("Contact from WA endpoint:");
        System.out.println(contact.getName());
    }
}
