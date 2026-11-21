package pl.put.poznan.transformer.rest.dto;

import java.util.List;

/**
 * JSON body of a transformation response.
 *
 * <pre>{"text": "Hello World", "transforms": ["upper"], "result": "HELLO WORLD"}</pre>
 *
 * @param text       original text
 * @param transforms transformations that were applied, in order
 * @param result     transformed text
 */
public record TransformResponse(String text, List<String> transforms, String result) {
}
