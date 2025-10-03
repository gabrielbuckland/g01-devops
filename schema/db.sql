DROP TABLE IF EXISTS form_db;

CREATE TABLE form_db (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    vorname TEXT NOT NULL,
    nachname TEXT NOT NULL,
    email TEXT NOT NULL
)