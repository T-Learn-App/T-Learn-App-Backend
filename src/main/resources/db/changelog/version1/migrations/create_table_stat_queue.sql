CREATE TABLE IF NOT EXISTS stat_queue (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    word_id BIGINT NOT NULL,
    status VARCHAR(255) NOT NULL,
    error VARCHAR(255),
    CONSTRAINT uk_unique_queue_id_combination UNIQUE (user_id, word_id),
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_word_id FOREIGN KEY (word_id) REFERENCES words(id)
)