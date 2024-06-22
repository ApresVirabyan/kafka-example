package com.example.consumer.model;

import java.util.Date;

public record Message(
        long id,
        String text,
        Date date
) {
}
