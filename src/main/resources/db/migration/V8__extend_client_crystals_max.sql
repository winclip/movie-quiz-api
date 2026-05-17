ALTER TABLE clients
    DROP CONSTRAINT IF EXISTS chk_clients_crystals;

ALTER TABLE clients
    ADD CONSTRAINT chk_clients_crystals CHECK (crystals >= 0 AND crystals <= 10);
