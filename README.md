# Text Transformer

[![CI](https://github.com/Kapcpa/text-transformer/actions/workflows/ci.yml/badge.svg)](https://github.com/Kapcpa/text-transformer/actions/workflows/ci.yml)

Projekt z Inżynierii Oprogramowania – REST API do transformacji tekstu
(zmiana wielkości liter, odwracanie, liczby na słowa, skróty itd.).

- Backlog i sprinty: [GitHub Project](https://github.com/users/Kapcpa/projects/10)
- Diagram klas: [docs/uml/class-diagram.md](docs/uml/class-diagram.md)
- Jak pracujemy: [CONTRIBUTING.md](CONTRIBUTING.md)

## Odpalenie

Potrzebne JDK 17+ i Maven.

```bash
mvn verify                                # build + testy + jar + javadoc
java -jar target/text-transformer-*.jar   # http://localhost:8080
```

Javadoc po buildzie: `target/site/apidocs/index.html` (albo artefakt `javadoc` w CI).

## API

```bash
curl -X POST localhost:8080/api/transform \
  -H 'Content-Type: application/json' \
  -d '{"text": "MirEk", "transforms": ["inverse", "upper"]}'
# {"text":"MirEk","transforms":["inverse","upper"],"result":"KERIM"}
```

- `POST /api/transform` – JSON jak wyżej
- `GET /api/transform?text=abc&transforms=upper,inverse` – to samo w URL
- `GET /api/transforms` – lista dostępnych transformacji

Transformacje lecą w podanej kolejności, nieznana nazwa daje `400`.

## Zespół

- Kacper Musiała ([@Kapcpa](https://github.com/Kapcpa))
- 
- 
