package pl.sgorski.Tagger.controller;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.sgorski.Tagger.service.auth.JwtService;
import pl.sgorski.Tagger.service.auth.UserService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(Router.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class RouterTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserService userService;

    @Nested
    class DocsRedirects {
        @Test
        void shouldRedirectRootToDocs() throws Exception {
            mockMvc.perform(get("/"))
                    .andExpect(status().isFound())
                    .andExpect(redirectedUrl("/swagger-ui/index.html"));
        }

        @Test
        void shouldRedirectHomeToDocs() throws Exception {
            mockMvc.perform(get("/home"))
                    .andExpect(status().isFound())
                    .andExpect(redirectedUrl("/swagger-ui/index.html"));
        }

        @Test
        void shouldRedirectDocsToDocs() throws Exception {
            mockMvc.perform(get("/docs"))
                    .andExpect(status().isFound())
                    .andExpect(redirectedUrl("/swagger-ui/index.html"));
        }
    }

    @Test
    void shouldRedirectLoginToGoogleOAuth2() throws Exception {
        mockMvc.perform(get("/auth/login"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/oauth2/authorization/google"));
    }
}
