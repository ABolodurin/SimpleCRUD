CREATE TABLE IF NOT EXISTS business_objects
(
    id           serial    NOT NULL,
    name         varchar   NOT NULL,
    timestamp    timestamp NOT NULL,
    non_business integer   NOT NULL,

    CONSTRAINT pk_business_objects PRIMARY KEY (id),
    CONSTRAINT unique_name UNIQUE (name)
);
