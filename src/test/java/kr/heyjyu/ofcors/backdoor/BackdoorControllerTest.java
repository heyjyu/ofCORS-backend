package kr.heyjyu.ofcors.backdoor;

import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class BackdoorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    public void cleanup() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/backdoor/truncate"));
    }

    @Test
    void setupDatabase() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/backdoor/setup-database"))
                .andExpect(status().isOk());
    }

    @Test
    void writeQuestions() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/backdoor/write-questions"))
                .andExpect(status().isOk());
    }
}
