# Café Les Quatres Fées

La meilleure API de gestion de café au Québec!

## Notes

- Choix conscient d'afficher la string de l'enum de status d'un siège dans le domaine.
- Convention de code en anglais et commentaires en français

## Requis

- Java 18 (OpenJDK)
- Maven

## Intégration Docker

```bash
docker build -t application-glo4002 .
docker run -p 8181:8181 application-glo4002
```

## Setup

### Compilation

```bash
mvn clean install
```

### Exécution

```bash
mvn exec:java -pl cafe-api
```
