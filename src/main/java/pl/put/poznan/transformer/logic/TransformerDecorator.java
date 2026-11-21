package pl.put.poznan.transformer.logic;

import java.util.Objects;

/**
 * Base class for all transformations (abstract decorator of the Decorator pattern).
 *
 * <p>A decorator wraps another {@link TextTransformer}: it first lets the wrapped
 * transformer process the text and then applies its own change in
 * {@link #apply(String)}. Wrapping decorators one in another builds a chain that
 * executes transformations in the order they were added.</p>
 *
 * <p>To add a new transformation, extend this class, implement {@link #apply(String)}
 * and register the class in {@link TransformerFactory}.</p>
 */
public abstract class TransformerDecorator implements TextTransformer {

    /** The transformer wrapped by this decorator; it is executed before this one. */
    private final TextTransformer wrapped;

    /**
     * Creates a decorator around another transformer.
     *
     * @param wrapped transformer executed before this decorator, must not be {@code null}
     */
    protected TransformerDecorator(TextTransformer wrapped) {
        this.wrapped = Objects.requireNonNull(wrapped, "wrapped transformer must not be null");
    }

    /**
     * Runs the wrapped transformer and then this decorator's own transformation.
     *
     * @param text input text
     * @return text after the whole chain up to and including this decorator
     */
    @Override
    public final String transform(String text) {
        return apply(wrapped.transform(text));
    }

    /**
     * Applies only this decorator's transformation.
     *
     * @param text text already processed by the wrapped transformers
     * @return text after this transformation
     */
    protected abstract String apply(String text);
}
