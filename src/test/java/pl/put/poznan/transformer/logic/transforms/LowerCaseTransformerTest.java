package pl.put.poznan.transformer.logic.transforms;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import pl.put.poznan.transformer.logic.PlainText;

class LowerCaseTransformerTest {

    private final LowerCaseTransformer transformer = new LowerCaseTransformer(new PlainText());

    @Test
    void convertsUpperCaseToLowerCase() {
        assertThat(transformer.transform("Lower")).isEqualTo("lower");
    }

    @Test
    void keepsLowerCaseAndNonLetters() {
        assertThat(transformer.transform("abc 123 !?")).isEqualTo("abc 123 !?");
    }

    @Test
    void supportsPolishLetters() {
        assertThat(transformer.transform("ZAŻÓŁĆ GĘŚLĄ JAŹŃ")).isEqualTo("zażółć gęślą jaźń");
    }
}
