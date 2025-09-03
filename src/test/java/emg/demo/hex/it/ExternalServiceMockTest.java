package emg.demo.hex.it;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import emg.demo.hex.domain.models.AdditionalTaskInfo;
import emg.demo.hex.domain.ports.out.ExternalServicePort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class ExternalServiceMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExternalServicePort externalServicePort;

    @Test
    void additionalInfoEndpoint_returnsMockedData() throws Exception {
        Long taskId = 1L;
        var mocked = AdditionalTaskInfo.of(99L, "Jane Doe", "jane.doe@example.com");
        when(externalServicePort.getAdditionalTaskInfo(taskId)).thenReturn(mocked);

        mockMvc.perform(get("/api/tasks/" + taskId + "/additionalInfo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", is(99)))
                .andExpect(jsonPath("$.userName", is("Jane Doe")))
                .andExpect(jsonPath("$.userEmail", is("jane.doe@example.com")));
    }
}

