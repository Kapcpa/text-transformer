package pl.put.poznan.transformer.logic.transforms;

import java.util.Locale;

import pl.put.poznan.transformer.logic.TextTransformer;
import pl.put.poznan.transformer.logic.TransformerDecorator;

/**
 * Converts all letters to upper case: {@code upper -> UPPER}.
 *
 * <p>Registered as {@code upper}. Works for all Unicode letters, including
 * Polish diacritics ({@code żółw -> ŻÓŁW}).</p>
 */
public class UpperCaseTransformer extends TransformerDecorator {

    /**
     * Creates the transformation on top of another transformer.
     *
     * @param wrapped transformer executed before this one
     */
    public UpperCaseTransformer(TextTransformer wrapped) {
        super(wrapped);
    }

    /**
     * Converts the text to upper case using locale-independent rules.
     *
     * @param text input text
     * @return text with all letters in upper case
     */
    @Override
    protected String apply(String text) {
        return text.toUpperCase(Locale.ROOT);
    }
}
