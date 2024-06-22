package com.example.consumer.service;

import com.example.consumer.entity.MessageEntity;
import com.example.consumer.model.Message;
import com.example.consumer.repository.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageConsumerLogger implements MessageConsumer {

    private final MessageRepository messageRepository;

    public MessageConsumerLogger(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    private static final Logger log = LoggerFactory.getLogger(MessageConsumerLogger.class);

    @Override
    public void accept(List<Message> values) {
        for(var value: values){
            log.info("log: {}", value);
            MessageEntity messageEntity = convert(value);
            messageRepository.save(messageEntity);
        }
    }

    private MessageEntity convert(Message message){
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setText(message.text());
        messageEntity.setDate(message.date());
        return messageEntity;
    }
}
