
CREATE TABLE question_translations (
    id            BIGSERIAL PRIMARY KEY,
    question_id   BIGINT NOT NULL REFERENCES questions (id) ON DELETE CASCADE,
    locale        VARCHAR(10) NOT NULL,
    translation_text TEXT NOT NULL,
    CONSTRAINT uq_question_translations_question_locale UNIQUE (question_id, locale)
);

CREATE INDEX idx_question_translations_question ON question_translations (question_id);
CREATE INDEX idx_question_translations_locale ON question_translations (locale);

CREATE TABLE answer_translations (
    id            BIGSERIAL PRIMARY KEY,
    answer_id     BIGINT NOT NULL REFERENCES answers (id) ON DELETE CASCADE,
    locale        VARCHAR(10) NOT NULL,
    translation_text TEXT NOT NULL,
    CONSTRAINT uq_answer_translations_answer_locale UNIQUE (answer_id, locale)
);

CREATE INDEX idx_answer_translations_answer ON answer_translations (answer_id);
CREATE INDEX idx_answer_translations_locale ON answer_translations (locale);
