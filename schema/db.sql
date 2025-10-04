DROP TABLE IF EXISTS form_db;

CREATE TABLE form_db (
    email TEXT NOT NULL PRIMARY KEY,
    vorname TEXT NOT NULL,
    nachname TEXT NOT NULL
)