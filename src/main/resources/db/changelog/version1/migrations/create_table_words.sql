CREATE TABLE IF NOT EXISTS words (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    word VARCHAR(255) NOT NULL,
    transcription VARCHAR(255) NOT NULL,
    translation VARCHAR(255) NOT NULL,
    partOfSpeech VARCHAR(100) NOT NULL,
    CONSTRAINT uk_unique_word_combination UNIQUE (word, transcription, translation, partOfSpeech)
);