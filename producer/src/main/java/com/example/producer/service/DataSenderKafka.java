package com.example.producer.service;

import java.util.function.Consumer;

import com.example.producer.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;


public class DataSenderKafka implements DataSender {

    private static final Logger log = LoggerFactory.getLogger(DataSenderKafka.class);

    private final KafkaTemplate<String, Message> template;

    private final Consumer<Message> sendAsk;

    private final String topic;

    public DataSenderKafka(String topic,
                           KafkaTemplate<String, Message> template,
                           Consumer<Message> sendAsk
    ) {
        this.template = template;
        this.sendAsk = sendAsk;
        this.topic = topic;
    }

    @Override
    public void send(Message message) {
        try {
            log.info("text: {}", message);
            template.send(topic, message)
                    .whenComplete(
                            (result, ex) -> {
                                if (ex == null) {
                                    log.info("message id: {} was sent, offset: {}",
                                            message.id(),
                                            result.getRecordMetadata().offset());
                                    sendAsk.accept(message);
                                } else {
                                    log.error("message id:{} was not sent", message.id(), ex);
                                }
                            }
                    );
        } catch (Exception ex) {
            log.error("send error, text:{}", message, ex);
        }
    }
}