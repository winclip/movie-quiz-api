CREATE TABLE page_results (
    id            BIGSERIAL PRIMARY KEY,
    client_id     UUID NOT NULL REFERENCES clients (id) ON DELETE CASCADE,
    page          INT NOT NULL,
    stars         SMALLINT NOT NULL,
    correct_count INT NOT NULL,
    total_count   INT NOT NULL,
    completed_at  TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT uq_page_results_client_page UNIQUE (client_id, page),
    CONSTRAINT chk_page_results_stars CHECK (stars >= 0 AND stars <= 3),
    CONSTRAINT chk_page_results_correct_count CHECK (correct_count >= 0),
    CONSTRAINT chk_page_results_total_count CHECK (total_count > 0)
);

CREATE INDEX idx_page_results_client ON page_results (client_id);
