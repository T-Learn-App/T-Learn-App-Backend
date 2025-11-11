ALTER TABLE words ADD COLUMN category_id BIGINT;
ALTER TABLE words ADD CONSTRAINT fk_word_category FOREIGN KEY (category_id) REFERENCES categories(id)