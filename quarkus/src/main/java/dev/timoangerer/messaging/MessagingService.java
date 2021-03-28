package dev.timoangerer.messaging;

import javax.enterprise.context.ApplicationScoped;

import dev.timoangerer.channel.ChannelRepository;
import dev.timoangerer.channel.ChannelType;
import dev.timoangerer.contact.db.ContactRepository;
import dev.timoangerer.contact.model.Contact;
import dev.timoangerer.message.db.MessageRepository;
import dev.timoangerer.message.model.Message;
import dev.timoangerer.messaging.templating.GeneralTemplateVariables;
import dev.timoangerer.messaging.templating.ModelTemplateVariables;
import dev.timoangerer.messaging.templating.TemplatingEngine;

@ApplicationScoped
public class MessagingService {

    private final ChannelRepository channelRepository;

    private final MessageRepository messageRepository;

    private final ContactRepository contactRepository;

    public MessagingService(ChannelRepository channelRepository, MessageRepository messageRepository, ContactRepository contactRepository) {
        this.channelRepository = channelRepository;
        this.messageRepository = messageRepository;
        this.contactRepository = contactRepository;
    }

    public void sendMessage(Message message) {
        ChannelType channelType = channelRepository.findChannelTypeByChannelId(message.getChannelId());

        TemplatingEngine templatingEngine = new TemplatingEngine();
        templatingEngine.addVariables(GeneralTemplateVariables.getAllGeneralVariables());
        Contact contact = contactRepository.findById(message.getContactId());
        templatingEngine.addVariables(ModelTemplateVariables.contactToMap(contact));
        String renderedTemplate = templatingEngine.renderTemplate(message.getText());
        message.setText(renderedTemplate);

        // Pass the message to channel specific handler
        switch (channelType) {
            case WHATSAPP:
                sendWhatsappMessage(message);
                break;

            // ....
        }
    }

    // This would be a seperate service/class to handle sending WA messages
    private void sendWhatsappMessage(Message message) {
        System.out.println("Sending whatsapp message...");
        System.out.println(message.toString());
        messageRepository.insert(message);
    }
}
