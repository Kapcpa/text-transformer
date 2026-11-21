package pl.put.poznan.transformer.rest.dto;

import java.util.List;

/**
 * JSON body of a transformation request.
 *
 * <pre>{"text": "Hello World", "transforms": ["upper", "inverse"]}</pre>
 *
 * @param text       text to transform
 * @param transforms names of transformations, applied in the given order
 */
public record TransformRequest(String text, List<String> transforms) {

    /**
     * Normalizes missing values: {@code null} text becomes an empty string and a
     * {@code null} list becomes an empty list.
     *
     * @param text       text to transform
     * @param transforms names of transformations
     */
    public TransformRequest {
        text = text == null ? "" : text;
        transforms = transforms == null ? List.of() : List.copyOf(transforms);
    }
}
