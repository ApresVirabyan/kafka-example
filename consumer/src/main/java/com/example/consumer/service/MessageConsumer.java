package com.example.consumer.service;

import com.example.consumer.model.Message;

import java.util.List;

public interface MessageConsumer {

    void accept(List<Message> value);
}
