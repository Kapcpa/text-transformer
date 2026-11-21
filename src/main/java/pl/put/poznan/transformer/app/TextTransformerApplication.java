package pl.put.poznan.transformer.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point of the Text Transformer application.
 *
 * <p>Starts the embedded web server and registers all Spring components found
 * in the {@code pl.put.poznan.transformer} package and its sub-packages
 * (REST controllers and the transformation logic).</p>
 */
@SpringBootApplication(scanBasePackages = {"pl.put.poznan.transformer"})
public class TextTransformerApplication {

    /**
     * Starts the Spring Boot application.
     *
     * @param args command line arguments passed to Spring
     *             (e.g. {@code --server.port=9090})
     */
    public static void main(String[] args) {
        SpringApplication.run(TextTransformerApplication.class, args);
    }
}
