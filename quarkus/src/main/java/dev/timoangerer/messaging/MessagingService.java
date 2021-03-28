package dev.timoangerer.messaging;

import javax.enterprise.context.ApplicationScoped;

import dev.timoangerer.channel.ChannelRepository;
import dev.timoangerer.channel.ChannelType;
import dev.timoangerer.message.db.MessageRepository;
import dev.timoangerer.message.model.Message;

@ApplicationScoped
public class MessagingService {

    private final ChannelRepository channelRepository;

    private final MessageRepository messageRepository;

    public MessagingService(ChannelRepository channelRepository, MessageRepository messageRepository) {
        this.channelRepository = channelRepository;
        this.messageRepository = messageRepository;
    }

    public void sendMessage(Message message) {
        ChannelType channelType = channelRepository.findChannelTypeByChannelId(message.getChannelId());

        // Render template string if template is used.

        // Pass message to channel specific handler
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
