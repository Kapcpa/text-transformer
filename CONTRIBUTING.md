# Jak pracujemy

## Flow

1. Bierzesz zadanie z [projektu](https://github.com/users/Kapcpa/projects/10) (Sprint 1), przypisujesz się, przesuwasz na *In Progress*.
2. Branch z `main`: `feature/<nr-issue>-<opis>`, np. `feature/13-upper`.
3. PR do `main` z `Closes #<nr>` w opisie. Merge dopiero jak CI jest zielone.

Commity najlepiej `feat: ...`, `fix: ...`, `test: ...`, `docs: ...`.

## Nowa transformacja

Klasa w `logic/transforms/`, dziedziczy po `TransformerDecorator`:

```java
/**
 * Converts all letters to upper case: {@code upper -> UPPER}.
 */
public class UpperCaseTransformer extends TransformerDecorator {

    /**
     * @param wrapped transformer executed before this one
     */
    public UpperCaseTransformer(TextTransformer wrapped) {
        super(wrapped);
    }

    /**
     * Converts the text to upper case.
     *
     * @param text input text
     * @return text in upper case
     */
    @Override
    protected String apply(String text) {
        return text.toUpperCase(Locale.ROOT);
    }
}
```

Potem:

- rejestracja w konstruktorze `TransformerFactory`: `register("upper", UpperCaseTransformer::new);`
- testy w `src/test/java/.../logic/transforms/`
- dopisać klasę na [diagramie klas](docs/uml/class-diagram.md)

**Javadoc jest punktowany** – opis klasy, wszystkich metod (poza get/set) i pól.

## Koniec sprintu

Wersja w `pom.xml` musi się zgadzać z tagiem (Sprint 1 → `1.0.0`):

```bash
git switch main && git pull
git tag -a v1.0.0 -m "Sprint 1"
git push origin v1.0.0
```
