package pl.put.poznan.transformer.rest;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import pl.put.poznan.transformer.logic.TransformerFactory;
import pl.put.poznan.transformer.logic.UnknownTransformationException;

@WebMvcTest
@ContextConfiguration(classes = {TextTransformerController.class, RestExceptionHandler.class})
class TextTransformerControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TransformerFactory factory;

    @Test
    void postReturnsTransformedTextAsJson() throws Exception {
        when(factory.create(List.of("upper"))).thenReturn(String::toUpperCase);

        mvc.perform(post("/api/transform")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"text\":\"abc\",\"transforms\":[\"upper\"]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("abc"))
                .andExpect(jsonPath("$.transforms[0]").value("upper"))
                .andExpect(jsonPath("$.result").value("ABC"));
    }

    @Test
    void getSplitsCommaSeparatedTransforms() throws Exception {
        when(factory.create(List.of("upper", "lower"))).thenReturn(t -> "ok");

        mvc.perform(get("/api/transform").param("text", "abc").param("transforms", "upper,lower"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("ok"));
    }

    @Test
    void unknownTransformationGives400() throws Exception {
        when(factory.create(anyList())).thenThrow(new UnknownTransformationException("foo"));

        mvc.perform(get("/api/transform").param("text", "abc").param("transforms", "foo"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Unknown transformation: 'foo'"));
    }
}
