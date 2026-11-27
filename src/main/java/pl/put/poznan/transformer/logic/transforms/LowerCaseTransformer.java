package pl.put.poznan.transformer.logic.transforms;

import java.util.Locale;

import pl.put.poznan.transformer.logic.TextTransformer;
import pl.put.poznan.transformer.logic.TransformerDecorator;

/**
 * Converts all letters to lower case: {@code Lower -> lower}.
 *
 * <p>Registered as {@code lower}. Works for all Unicode letters, including
 * Polish diacritics ({@code ŻÓŁW -> żółw}).</p>
 */
public class LowerCaseTransformer extends TransformerDecorator {

    /**
     * Creates the transformation on top of another transformer.
     *
     * @param wrapped transformer executed before this one
     */
    public LowerCaseTransformer(TextTransformer wrapped) {
        super(wrapped);
    }

    /**
     * Converts the text to lower case using locale-independent rules.
     *
     * @param text input text
     * @return text with all letters in lower case
     */
    @Override
    protected String apply(String text) {
        return text.toLowerCase(Locale.ROOT);
    }
}
