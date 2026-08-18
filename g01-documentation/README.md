# Modul DEVOPS - g01

Dokumentation der Gruppe g01 im Modul DEVOPS HS25.

## Links

* [Maven Site Dokumentation](https://hslu.pages.switch.ch/edu/bachelor-computer-science/devops/25hs01/g01/g01-documentation)
* [AsciiDoc Dokumentation](https://hslu.pages.switch.ch/edu/bachelor-computer-science/devops/25hs01/g01/g01-documentation/asciidoc/index.html) [(PDF)](https://hslu.pages.switch.ch/edu/bachelor-computer-science/devops/25hs01/g01/g01-documentation/asciidoc/index.pdf)

## Vorschlag Projekt

### Simples UI

- Formular
- Speicherung
- Anzeigen der Einträge
- Technologie: Svelte
- build: npm & node 22.22.0

### Backend

- einträge speichern via REST
- einträge anzeigen via REST
- Persistenz mit Postgres
- Technologie: Micronaut Java 21
- build: maven

### CI

- Gitlab

### Artifact storage

- Gitlab

### Security scans

- Gitlab

### Environments

- Test
- Prod

## VMs

| VM owner | hostname                     | username | usage    |
|----------|------------------------------|----------|----------|
| gabriel  | srv-003.devops.ls.eee.intern | labadmin |          |
| bleron   | srv-012.devops.ls.eee.intern | labadmin |          |
| nevi     | srv-020.devops.ls.eee.intern | labadmin | TEST env |
| lukas    | srv-021.devops.ls.eee.intern | labadmin | PROD env |

