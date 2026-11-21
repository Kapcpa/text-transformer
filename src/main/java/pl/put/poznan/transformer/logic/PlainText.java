package pl.put.poznan.transformer.logic;

/**
 * Concrete component of the Decorator pattern: the innermost element of every
 * transformation chain.
 *
 * <p>It returns the text unchanged, so a chain without any decorators is an
 * identity transformation.</p>
 */
public class PlainText implements TextTransformer {

    /**
     * Returns the input text unchanged.
     *
     * @param text input text
     * @return the same text
     */
    @Override
    public String transform(String text) {
        return text;
    }
}
