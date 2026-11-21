package pl.put.poznan.transformer.rest;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pl.put.poznan.transformer.logic.TextTransformer;
import pl.put.poznan.transformer.logic.TransformerFactory;
import pl.put.poznan.transformer.rest.dto.TransformRequest;
import pl.put.poznan.transformer.rest.dto.TransformResponse;

/**
 * REST API of the application.
 *
 * <p>The controller only translates HTTP requests to calls of the logic layer; the
 * transformations themselves are built by {@link TransformerFactory}.</p>
 *
 * <ul>
 *   <li>{@code POST /api/transform} - JSON body {@link TransformRequest}</li>
 *   <li>{@code GET  /api/transform?text=...&transforms=upper,inverse}</li>
 *   <li>{@code GET  /api/transforms} - list of supported transformation names</li>
 * </ul>
 */
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class TextTransformerController {

    private static final Logger logger = LoggerFactory.getLogger(TextTransformerController.class);

    /** Factory building transformation chains from transformation names. */
    private final TransformerFactory transformerFactory;

    /**
     * Creates the controller.
     *
     * @param transformerFactory factory used to build transformation chains
     */
    public TextTransformerController(TransformerFactory transformerFactory) {
        this.transformerFactory = transformerFactory;
    }

    /**
     * Transforms text sent as JSON.
     *
     * @param request text and transformation names
     * @return original text, applied transformations and the result
     */
    @PostMapping(value = "/transform", consumes = MediaType.APPLICATION_JSON_VALUE)
    public TransformResponse transform(@RequestBody TransformRequest request) {
        logger.info("POST /api/transform transforms={}", request.transforms());
        logger.debug("Input text: '{}'", request.text());
        return execute(request);
    }

    /**
     * Transforms text passed as query parameters (handy for testing in a browser).
     *
     * @param text       text to transform
     * @param transforms comma separated transformation names
     * @return original text, applied transformations and the result
     */
    @GetMapping("/transform")
    public TransformResponse transform(@RequestParam String text,
                                       @RequestParam(defaultValue = "") List<String> transforms) {
        logger.info("GET /api/transform transforms={}", transforms);
        logger.debug("Input text: '{}'", text);
        return execute(new TransformRequest(text, transforms));
    }

    /**
     * Lists supported transformations.
     *
     * @return names that can be used in the {@code transforms} field
     */
    @GetMapping("/transforms")
    public Set<String> availableTransformations() {
        return transformerFactory.availableTransformations();
    }

    /**
     * Builds the transformation chain, runs it and wraps the result.
     *
     * @param request normalized request
     * @return response with the transformed text
     */
    private TransformResponse execute(TransformRequest request) {
        TextTransformer transformer = transformerFactory.create(request.transforms());
        String result = transformer.transform(request.text());
        logger.debug("Result: '{}'", result);
        return new TransformResponse(request.text(), request.transforms(), result);
    }
}
