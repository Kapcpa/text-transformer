package pl.put.poznan.transformer.logic;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.UnaryOperator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Builds transformation chains from transformation names (the dynamic part of the
 * Decorator pattern).
 *
 * <p>Each supported transformation is registered under a lowercase name together
 * with a function that wraps an existing {@link TextTransformer} in the matching
 * decorator. {@link #create(List)} starts from {@link PlainText} and wraps it once
 * per requested name, so the resulting object applies the transformations in the
 * requested order.</p>
 */
@Component
public class TransformerFactory {

    private static final Logger logger = LoggerFactory.getLogger(TransformerFactory.class);

    /** Registered transformations: name -> function wrapping a transformer in a decorator. */
    private final Map<String, UnaryOperator<TextTransformer>> registry = new LinkedHashMap<>();

    /**
     * Creates the factory with all transformations supported by the application.
     */
    public TransformerFactory() {
        // Kazdy odkomentowuje tylko swoja linie. Pelne nazwy klas zamiast importow i puste linie
        // miedzy wpisami = brak konfliktow przy merge.

        // #7 zmiana wielkosci liter
        // register("upper", pl.put.poznan.transformer.logic.transforms.UpperCaseTransformer::new);

        // register("lower", pl.put.poznan.transformer.logic.transforms.LowerCaseTransformer::new);

        // register("capitalize", pl.put.poznan.transformer.logic.transforms.CapitalizeTransformer::new);

        // #12 inverse
        // register("inverse", pl.put.poznan.transformer.logic.transforms.InverseTransformer::new);

        // #15 liczby na tekst
        // register("numbers", pl.put.poznan.transformer.logic.transforms.NumberToWordsTransformer::new);

        // #20 skroty
        // register("abbreviate", pl.put.poznan.transformer.logic.transforms.AbbreviateTransformer::new);

        // #23 rozwijanie skrotow
        // register("expand", pl.put.poznan.transformer.logic.transforms.ExpandAbbreviationsTransformer::new);
    }

    /**
     * Registers a transformation under the given name.
     *
     * @param name    transformation name used in REST requests (case-insensitive)
     * @param wrapper function that wraps a transformer in the decorator
     * @throws IllegalArgumentException if the name is already registered
     */
    public final void register(String name, UnaryOperator<TextTransformer> wrapper) {
        String key = normalize(name);
        if (registry.putIfAbsent(key, wrapper) != null) {
            throw new IllegalArgumentException("Transformation already registered: " + key);
        }
        logger.debug("Registered transformation '{}'", key);
    }

    /**
     * Builds a transformer applying the given transformations in order.
     *
     * @param names transformation names; an empty list gives an identity transformer
     * @return transformer executing the whole chain
     * @throws UnknownTransformationException if any name is not registered
     */
    public TextTransformer create(List<String> names) {
        TextTransformer transformer = new PlainText();
        for (String name : names) {
            UnaryOperator<TextTransformer> wrapper = registry.get(normalize(name));
            if (wrapper == null) {
                logger.info("Rejected unknown transformation '{}'", name);
                throw new UnknownTransformationException(name);
            }
            transformer = wrapper.apply(transformer);
        }
        logger.debug("Built transformation chain {}", names);
        return transformer;
    }

    /**
     * Returns names of all registered transformations in registration order.
     *
     * @return unmodifiable set of transformation names
     */
    public Set<String> availableTransformations() {
        return Collections.unmodifiableSet(registry.keySet());
    }

    /**
     * Normalizes a transformation name (trims it and converts it to lowercase).
     *
     * @param name raw name
     * @return normalized name
     */
    private static String normalize(String name) {
        return name == null ? "" : name.trim().toLowerCase(Locale.ROOT);
    }
}
