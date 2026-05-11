CREATE TABLE categories (
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE questions (
    id          BIGSERIAL PRIMARY KEY,
    category_id BIGINT NOT NULL REFERENCES categories (id) ON DELETE RESTRICT,
    text        TEXT NOT NULL,
    image_url   TEXT
);

CREATE TABLE answers (
    id          BIGSERIAL PRIMARY KEY,
    question_id BIGINT NOT NULL REFERENCES questions (id) ON DELETE CASCADE,
    text        TEXT NOT NULL,
    correct     BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE INDEX idx_questions_category ON questions (category_id);
CREATE INDEX idx_answers_question ON answers (question_id);
