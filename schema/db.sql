DROP TABLE IF EXISTS form_db;

CREATE TABLE form_db (
    id UUID PRIMARY KEY,
    vorname TEXT NOT NULL,
    nachname TEXT NOT NULL,
    email TEXT NOT NULL
)