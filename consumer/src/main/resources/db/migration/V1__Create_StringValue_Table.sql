CREATE DATABASE postgres;

CREATE TABLE string_value_entity (
    id BIGSERIAL PRIMARY KEY,
    value VARCHAR(255) NOT NULL
);