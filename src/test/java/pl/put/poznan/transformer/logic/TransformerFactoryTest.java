package pl.put.poznan.transformer.logic;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransformerFactoryTest {

    /** Test-only decorator: appends a marker so the order of the chain is visible. */
    private static final class Suffix extends TransformerDecorator {
        private final String suffix;

        Suffix(TextTransformer wrapped, String suffix) {
            super(wrapped);
            this.suffix = suffix;
        }

        @Override
        protected String apply(String text) {
            return text + suffix;
        }
    }

    private TransformerFactory factory;

    @BeforeEach
    void setUp() {
        factory = new TransformerFactory();
        factory.register("a", t -> new Suffix(t, "A"));
        factory.register("b", t -> new Suffix(t, "B"));
    }

    @Test
    void emptyChainReturnsTextUnchanged() {
        assertThat(factory.create(List.of()).transform("text")).isEqualTo("text");
    }

    @Test
    void appliesTransformationsInRequestedOrder() {
        assertThat(factory.create(List.of("b", "a")).transform("x")).isEqualTo("xBA");
    }

    @Test
    void namesAreCaseInsensitive() {
        assertThat(factory.create(List.of(" A ")).transform("x")).isEqualTo("xA");
    }

    @Test
    void unknownNameThrows() {
        assertThatThrownBy(() -> factory.create(List.of("nope")))
                .isInstanceOf(UnknownTransformationException.class)
                .hasMessageContaining("nope");
    }

    @Test
    void duplicateRegistrationThrows() {
        assertThatThrownBy(() -> factory.register("a", t -> t))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
