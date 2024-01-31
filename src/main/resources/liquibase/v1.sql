--liquibase formatted sql
--changeset ABolodurin:V1

CREATE TABLE business_objects
(
    id           serial    NOT NULL,
    name         varchar   NOT NULL,
    timestamp    timestamp NOT NULL,
    non_business integer   NOT NULL,

    CONSTRAINT pk_business_objects PRIMARY KEY (id),
    CONSTRAINT unique_name UNIQUE (name)
);

INSERT INTO business_objects (name, timestamp, non_business)
VALUES ('First', now(), random() * 100),
       ('Second', now(), random() * 100),
       ('Third', now(), random() * 100);

-- rollback DROP TABLE business_objects;
