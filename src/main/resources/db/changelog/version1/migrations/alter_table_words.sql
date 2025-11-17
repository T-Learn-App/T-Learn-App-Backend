ALTER TABLE IF EXISTS words ADD COLUMN category_id BIGINT;
ALTER TABLE IF EXISTS words ADD CONSTRAINT fk_word_category FOREIGN KEY (category_id) REFERENCES categories(id)