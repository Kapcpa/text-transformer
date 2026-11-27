package pl.put.poznan.transformer.logic.transforms;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import pl.put.poznan.transformer.logic.PlainText;

class CapitalizeTransformerTest {

    private final CapitalizeTransformer transformer = new CapitalizeTransformer(new PlainText());

    @Test
    void capitalizesEveryWord() {
        assertThat(transformer.transform("capitalize text")).isEqualTo("Capitalize Text");
    }

    @Test
    void keepsRestOfWordUnchanged() {
        assertThat(transformer.transform("iPhone mAC")).isEqualTo("IPhone MAC");
    }

    @Test
    void handlesMultipleAndMixedWhitespace() {
        assertThat(transformer.transform("  a\tb\nc")).isEqualTo("  A\tB\nC");
    }

    @Test
    void supportsPolishLetters() {
        assertThat(transformer.transform("żółw ćma")).isEqualTo("Żółw Ćma");
    }

    @Test
    void doesNotChangeWordsStartingWithNonLetter() {
        assertThat(transformer.transform("123abc (test)")).isEqualTo("123abc (test)");
    }
}
