package uk.dcgmackenzie.snookerTracker;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import uk.dcgmackenzie.snookerTracker.entities.Drill;
import uk.dcgmackenzie.snookerTracker.entities.User;
import uk.dcgmackenzie.snookerTracker.services.DrillService;


import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SanityTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean private DrillService drillService;
    private String validAccessToken;

    @BeforeEach
    public void setup() {
        validAccessToken = JWT.create()
                .withSubject("david@gmail.co.uk")
                .withExpiresAt(new Date(System.currentTimeMillis() + (1000 * 60 * 60)))
                .sign(Algorithm.HMAC256("secret".getBytes()));
    }

    @Test
    public void healthCheckTest() throws Exception {
        this.mockMvc.perform(get("/snooker/health")).andExpect(status().isOk());
    }

    @Test
    public void securityTest() throws Exception {
        mockMvc.perform(post("/snooker/drills")).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "david", password = "password123")
    public void securityTestWithPermission() throws Exception {
        Drill mockDrill = new Drill();
        mockDrill.setId(1L);
        mockDrill.setName("My Drill");
        mockDrill.setDescription("My new drill");
        mockDrill.setUser(new User());
        Mockito.when(this.drillService.saveDrill(ArgumentMatchers.any(), ArgumentMatchers.anyString()))
        .thenReturn(mockDrill);
        mockMvc.perform(post("/snooker/drills")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"name\": \"Pot Seven balls\",\n" +
                        "    \"description\": \"Pot an easy shot\",\n" +
                        "    \"maxScore\":1\n" +
                        "}")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + validAccessToken)
                .characterEncoding("utf-8"))
                .andExpect(status().isCreated());
    }
}
