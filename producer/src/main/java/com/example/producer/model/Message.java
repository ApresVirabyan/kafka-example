package com.example.producer.model;

import java.util.Date;

public record Message(
        long id,
        String text,
        Date date
) {
}
