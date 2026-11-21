package pl.put.poznan.transformer.logic;

/**
 * Component interface of the Decorator pattern.
 *
 * <p>Every text transformation (upper, inverse, ...) implements this interface,
 * either directly ({@link PlainText}) or by extending {@link TransformerDecorator}.
 * Because decorators wrap other {@code TextTransformer} objects, any sequence of
 * transformations can be assembled at runtime and still be used through this
 * single method.</p>
 */
public interface TextTransformer {

    /**
     * Transforms the given text.
     *
     * @param text input text, never {@code null}
     * @return transformed text, never {@code null}
     */
    String transform(String text);
}
