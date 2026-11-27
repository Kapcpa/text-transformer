package pl.put.poznan.transformer.logic.transforms;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import pl.put.poznan.transformer.logic.PlainText;

class UpperCaseTransformerTest {

    private final UpperCaseTransformer transformer = new UpperCaseTransformer(new PlainText());

    @Test
    void convertsLowerCaseToUpperCase() {
        assertThat(transformer.transform("upper")).isEqualTo("UPPER");
    }

    @Test
    void keepsUpperCaseAndNonLetters() {
        assertThat(transformer.transform("ABC 123 !?")).isEqualTo("ABC 123 !?");
    }

    @Test
    void supportsPolishLetters() {
        assertThat(transformer.transform("zażółć gęślą jaźń")).isEqualTo("ZAŻÓŁĆ GĘŚLĄ JAŹŃ");
    }

    @Test
    void emptyTextStaysEmpty() {
        assertThat(transformer.transform("")).isEmpty();
    }
}
