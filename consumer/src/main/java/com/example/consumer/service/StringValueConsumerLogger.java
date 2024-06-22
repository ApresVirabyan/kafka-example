package com.example.consumer.service;

import com.example.consumer.model.StringValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class StringValueConsumerLogger implements StringValueConsumer{

    private static final Logger log = LoggerFactory.getLogger(StringValueConsumerLogger.class);

    @Override
    public void accept(List<StringValue> values) {
        for(var value: values){
            log.info("log: {}", value);
        }
    }
}
