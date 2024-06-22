package com.example.producer.config;

import com.example.producer.model.Message;
import com.example.producer.service.DataSender;
import com.example.producer.service.DataSenderKafka;
import com.example.producer.service.MessageServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.JacksonUtils;
import org.springframework.kafka.support.serializer.JsonSerializer;


@Configuration
public class ApplicationConfig {

    private static final Logger log = LoggerFactory.getLogger(ApplicationConfig.class);
    private final String topicName;

    public ApplicationConfig(@Value("${application.kafka.topic}") String topicName) {
        this.topicName = topicName;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return JacksonUtils.enhancedObjectMapper();
    }

    @Bean
    public ProducerFactory<String, Message> producerFactory(
            KafkaProperties kafkaProperties,
            ObjectMapper objectMapper
    ) {
        var props = kafkaProperties.buildProducerProperties();
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        var kafkaProducerFactory = new DefaultKafkaProducerFactory<String, Message>(props);
        kafkaProducerFactory.setValueSerializer(new JsonSerializer<>(objectMapper));

        return kafkaProducerFactory;
    }

    @Bean
    public KafkaTemplate<String, Message> kafkaTemplate(
            ProducerFactory<String, Message> producerFactory
    ){
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public NewTopic topik(){
         return TopicBuilder.name(topicName).partitions(1).replicas(1).build();
    }

    @Bean
    public DataSender dataSender(
            NewTopic topic,
            KafkaTemplate<String, Message> kafkaTemplate
    ){
        return new DataSenderKafka(
                topic.name(),
                kafkaTemplate,
                message -> log.info("asked, text:{} ", message));
    }

    @Bean
    public MessageServiceImpl messageServiceImpl(DataSender dataSender){
        return new MessageServiceImpl(dataSender);
    }

}
