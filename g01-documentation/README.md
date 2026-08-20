# Modul DEVOPS - g01

Der Modulbericht der Gruppe g01, DEVOPS HS25. AsciiDoc unter
`src/docs/asciidoc`, gebaut mit Maven und Asciidoctor. Braucht ein JDK 21 und
Maven, sonst nichts:

```bash
mvn clean site
```

Asciidoctor hängt an der `site`-Phase, `mvn package` erzeugt also nichts.
Danach liegen die Kapitel als HTML und PDF in `target/generated-docs`, die
Maven-Site in `target/site`. Die veröffentlichte Fassung lag auf den GitLab
Pages der Hochschule und ist von aussen nicht mehr erreichbar, deshalb sind
hier keine Links darauf.

## Kapitel

| Datei                              | Inhalt                                                    |
| ---------------------------------- | --------------------------------------------------------- |
| `01_uebersicht.adoc`               | Projektstruktur, Branching-Strategie, Technologiewahl     |
| `02_1_deployment_setup.adoc`       | Jib, Docker in der Pipeline, Deployment auf die VMs       |
| `02_2_vm_setup.adoc`               | Aufsetzen der Lab-VMs                                     |
| `03_1_basisszenarien.adoc`         | Change-Request von der Umsetzung bis zur Produktion       |
| `03_2_erweiterte_szenarien.adoc`   | Hotfix-Vorgehen und kontrolliertes Schnell-Deployment     |
| `03_3_individuelle_szenarien.adoc` | Flyway-Migration, 12-Factor-Betrachtung, Lessons Learned  |

## Umgebung

Die VMs im HSLU-Labornetz, je eine pro Gruppenmitglied verwaltet:

| Host    | Rolle                |
| ------- | -------------------- |
| srv-020 | Test-Umgebung        |
| srv-021 | Produktions-Umgebung |
| srv-003 | Monitoring           |

Die Maschinen sind nur aus dem Hochschulnetz erreichbar und uns nicht mehr
zugänglich. `devops-stack` im Wurzelverzeichnis bildet dieselbe Topologie
lokal auf einem Rechner ab.
