package dev.timoangerer.webhooks.whatsapp.web;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import dev.timoangerer.channel.whatsapp.db.WhatsappChannelRepository;
import dev.timoangerer.contact.model.Contact;
import dev.timoangerer.webhooks.whatsapp.model.WhatsappMessage;

@Path("/wh/whatsapp")
public class WhatsappMessageResource {

    private final WhatsappChannelRepository whatsappChannelRepository;

    public WhatsappMessageResource(WhatsappChannelRepository whatsappChannelRepository) {
      this.whatsappChannelRepository = whatsappChannelRepository;
    }

    @POST
    public void receiveMessage(WhatsappMessage whatsappMessage) {
        System.out.println("Received wa message:");
        System.out.println(whatsappMessage.getText());

        // Find or create customer based on recieved message
        Contact contact = whatsappChannelRepository.findContactByWaId(whatsappMessage.getWaId());
        if (contact == null) {
            System.out.println("no contact associated with this wa_id");
            
        } else {
            System.out.println("Found a contact!");
            System.out.println(contact.getName());
        }

        // Query the webhook specifc table (WA in this case) for the identifier (waId)
        // If there is a waId, fetch the customer associated with it.
        // Else, create a new customer with the infos of the channel.
    }
}
