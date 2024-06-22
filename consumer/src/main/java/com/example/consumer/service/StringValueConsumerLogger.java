package com.example.consumer.service;

import com.example.consumer.entity.StringValueEntity;
import com.example.consumer.model.StringValue;
import com.example.consumer.repository.StringValueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StringValueConsumerLogger implements StringValueConsumer{

    private final StringValueRepository stringValueRepository;

    public StringValueConsumerLogger(StringValueRepository stringValueRepository) {
        this.stringValueRepository = stringValueRepository;
    }

    private static final Logger log = LoggerFactory.getLogger(StringValueConsumerLogger.class);

    @Override
    public void accept(List<StringValue> values) {
        for(var value: values){
            log.info("log: {}", value);
            StringValueEntity stringValueEntity = convert(value);
            stringValueRepository.save(stringValueEntity);
        }
    }

    private StringValueEntity convert(StringValue stringValue){
        StringValueEntity stringValueEntity = new StringValueEntity();
        stringValueEntity.setValue(stringValue.value());
        return stringValueEntity;
    }
}
