--liquibase formatted sql

--changeset creat-table:1 dbms:postgresql context:main
CREATE TABLE IF NOT EXISTS categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);
--rollback drop table users

--changeset create-table:2 dbms:postgresql context:main
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);
--rollback drop table words

--changeset create-table:3 dbms:postgresql context:main
CREATE TABLE IF NOT EXISTS words (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    word VARCHAR(255) NOT NULL,
    transcription VARCHAR(255) NOT NULL,
    translation VARCHAR(255) NOT NULL,
    part_of_speech VARCHAR(100) NOT NULL,
    category_id BIGINT NOT NULL,
    CONSTRAINT fk_word_category FOREIGN KEY (category_id) REFERENCES categories(id),
    CONSTRAINT uk_unique_word_combination UNIQUE (word, transcription, translation, part_of_speech, category_id)
);
--rollback drop table categories

--changeset create-table:4 dbms:postgresql context:main
CREATE TABLE IF NOT EXISTS stat_queue (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    word_id BIGINT NOT NULL,
    status VARCHAR(255) NOT NULL,
    error VARCHAR(255),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT uk_unique_queue_id_combination UNIQUE (user_id, word_id),
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_word_id FOREIGN KEY (word_id) REFERENCES words(id)
);
--rollback drop table stat_queue

--changeset create-table:5 dbms:postgresql context:main
CREATE TABLE IF NOT EXISTS stats (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    word_id BIGINT NOT NULL,
    attempts INT NOT NULL CHECK ( attempts >= 0 AND attempts < 4),
    status VARCHAR(255) NOT NULL,
    category_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT uk_unique_id_combination UNIQUE (user_id, word_id),
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_word_id FOREIGN KEY (word_id) REFERENCES words(id),
    CONSTRAINT fk_category_id FOREIGN KEY (category_id) REFERENCES categories(id)
);
--rollback drop table stats

