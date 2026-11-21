package pl.put.poznan.transformer.logic;

/**
 * Thrown when a request contains a transformation name that is not registered in
 * {@link TransformerFactory}. The REST layer maps it to HTTP 400 Bad Request.
 */
public class UnknownTransformationException extends RuntimeException {

    /** Name of the transformation that could not be found. */
    private final String transformationName;

    /**
     * Creates the exception for the given transformation name.
     *
     * @param transformationName name that was requested but is not supported
     */
    public UnknownTransformationException(String transformationName) {
        super("Unknown transformation: '" + transformationName + "'");
        this.transformationName = transformationName;
    }

    /**
     * Returns the name of the unsupported transformation.
     *
     * @return requested transformation name
     */
    public String getTransformationName() {
        return transformationName;
    }
}
