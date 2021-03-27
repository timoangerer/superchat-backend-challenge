package dev.timoangerer.webhooks.whatsapp.web;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import dev.timoangerer.webhooks.whatsapp.model.WhatsappMessage;

@Path("/wh/whatsapp")
public class WhatsappMessageResource {

    @POST
    public void receiveMessage(WhatsappMessage whatsappMessage) {
        System.out.println("Received wa message:");
        System.out.println(whatsappMessage.getText());

    }
}
