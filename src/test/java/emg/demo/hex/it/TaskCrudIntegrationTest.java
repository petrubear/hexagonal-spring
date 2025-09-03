package emg.demo.hex.it;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
class TaskCrudIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void fullCrudFlow_withH2_shouldSucceed() throws Exception {
        // Create
        var creationPayload = objectMapper.createObjectNode();
        creationPayload.putNull("id");
        creationPayload.put("title", "Write docs");
        creationPayload.put("description", "Add README and examples");
        creationPayload.put("creationDate", LocalDateTime.now().withNano(0).toString());
        creationPayload.put("completed", false);

        MvcResult createResult = mockMvc.perform(
                post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(creationPayload)))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode created = objectMapper.readTree(createResult.getResponse().getContentAsString());
        Long id = created.get("id").asLong();
        assertThat(id).isNotNull();

        // Read by id
        mockMvc.perform(get("/api/tasks/" + id))
                .andExpect(status().isOk());

        // List all
        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk());

        // Update
        var updatePayload = objectMapper.createObjectNode();
        updatePayload.put("id", id);
        updatePayload.put("title", "Write better docs");
        updatePayload.put("description", "Tighten examples");
        updatePayload.put("creationDate", created.get("creationDate").asText());
        updatePayload.put("completed", true);

        mockMvc.perform(
                        put("/api/tasks/" + id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updatePayload)))
                .andExpect(status().isOk());

        // Delete
        mockMvc.perform(delete("/api/tasks/" + id))
                .andExpect(status().isOk());

        // Verify deleted
        mockMvc.perform(get("/api/tasks/" + id))
                .andExpect(status().isNotFound());
    }
}

