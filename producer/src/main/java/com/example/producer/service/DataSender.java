package com.example.producer.service;

import com.example.producer.model.Message;

public interface DataSender {
    void send(Message value);
}
