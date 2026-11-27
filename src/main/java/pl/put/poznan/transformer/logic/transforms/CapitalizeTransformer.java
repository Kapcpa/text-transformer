package pl.put.poznan.transformer.logic.transforms;

import pl.put.poznan.transformer.logic.TextTransformer;
import pl.put.poznan.transformer.logic.TransformerDecorator;

/**
 * Capitalizes the first letter of every word: {@code capitalize text -> Capitalize Text}.
 *
 * <p>Registered as {@code capitalize}. A word is a sequence of characters separated by
 * whitespace. Only the first character of each word is changed, the rest is kept as is
 * ({@code iPhone -> IPhone}). Works on Unicode code points, so letters outside the
 * Basic Multilingual Plane are handled too.</p>
 */
public class CapitalizeTransformer extends TransformerDecorator {

    /**
     * Creates the transformation on top of another transformer.
     *
     * @param wrapped transformer executed before this one
     */
    public CapitalizeTransformer(TextTransformer wrapped) {
        super(wrapped);
    }

    /**
     * Converts the first character of every word to title case.
     *
     * @param text input text
     * @return text with every word starting with a capital letter
     */
    @Override
    protected String apply(String text) {
        StringBuilder result = new StringBuilder(text.length());
        boolean wordStart = true;
        for (int i = 0; i < text.length(); ) {
            int codePoint = text.codePointAt(i);
            result.appendCodePoint(wordStart ? Character.toTitleCase(codePoint) : codePoint);
            wordStart = Character.isWhitespace(codePoint);
            i += Character.charCount(codePoint);
        }
        return result.toString();
    }
}
