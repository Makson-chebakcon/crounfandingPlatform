CREATE TABLE person
(
    id                            UUID PRIMARY KEY,
    role                          VARCHAR(255) NOT NULL,
    password                      VARCHAR(512) NOT NULL,
    name                          VARCHAR(512) NOT NULL,
    surname                       VARCHAR(512) NOT NULL,
    patronymic                    VARCHAR(512) NOT NULL,
    bio                           VARCHAR(32000),
    email                         VARCHAR(512) NOT NULL UNIQUE,
    email_is_confirm              BOOLEAN      NOT NULL,
    email_confirm_code            UUID,
    email_confirm_code_expires_at TIMESTAMP,
    money                         NUMERIC      NOT NULL
);

CREATE TABLE refresh_token
(
    id         UUID PRIMARY KEY,
    value      VARCHAR(1024) NOT NULL,
    created_at TIMESTAMP     NOT NULL,
    expired_at TIMESTAMP     NOT NULL,
    owner_id   UUID REFERENCES person (id) ON DELETE CASCADE
);

CREATE TABLE project
(
    id               UUID PRIMARY KEY,
    title            VARCHAR(255)   NOT NULL,
    summary          VARCHAR(255)   NOT NULL,
    description      VARCHAR(32768) NOT NULL,
    target_amount    NUMERIC        NOT NULL,
    collected_amount NUMERIC        NOT NULL,
    avatar_id        UUID           NOT NULL,
    creation_date    TIMESTAMP      NOT NULL,
    finish_date      TIMESTAMP      NOT NULL,
    is_approved      BOOLEAN        NOT NULL,
    category         VARCHAR(255)   NOT NULL,
    status           VARCHAR(128)   NOT NULL,
    author_id        UUID REFERENCES person (id) ON DELETE CASCADE
);

CREATE TABLE project_attachment_ids
(
    attachment_ids UUID,
    project_id     UUID references project (id)
);

CREATE TABLE file_meta_information
(
    id   UUID PRIMARY KEY,
    name VARCHAR(512)
);