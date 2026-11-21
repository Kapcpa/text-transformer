package pl.put.poznan.transformer.rest.dto;

/**
 * JSON body returned when a request cannot be processed.
 *
 * <pre>{"error": "Unknown transformation: 'foo'"}</pre>
 *
 * @param error human readable description of the problem
 */
public record ErrorResponse(String error) {
}
