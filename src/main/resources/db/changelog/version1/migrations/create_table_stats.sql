CREATE TABLE IF NOT EXISTS stats (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    word_id BIGINT NOT NULL,
    attempts INT NOT NULL CHECK ( attempts >= 0 AND attempts < 4),
    status VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT uk_unique_id_combination UNIQUE (user_id, word_id),
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_word_id FOREIGN KEY (word_id) REFERENCES words(id)
)