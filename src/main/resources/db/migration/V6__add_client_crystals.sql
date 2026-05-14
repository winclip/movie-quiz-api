ALTER TABLE clients
    ADD COLUMN IF NOT EXISTS crystals SMALLINT NOT NULL DEFAULT 3;

ALTER TABLE clients
    ADD COLUMN IF NOT EXISTS next_crystal_at TIMESTAMPTZ;

ALTER TABLE clients
    DROP CONSTRAINT IF EXISTS chk_clients_crystals;

ALTER TABLE clients
    ADD CONSTRAINT chk_clients_crystals CHECK (crystals >= 0 AND crystals <= 3);
