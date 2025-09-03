package emg.demo.hex.infraestructure.adapters;

import emg.demo.hex.domain.models.AdditionalTaskInfo;
import emg.demo.hex.domain.ports.out.ExternalServicePort;
import org.springframework.web.client.RestTemplate;

public class ExternalServiceAdapter implements ExternalServicePort {
    private final RestTemplate restTemplate;

    public ExternalServiceAdapter() {
        restTemplate = new RestTemplate();
    }

    @Override
    public AdditionalTaskInfo getAdditionalTaskInfo(Long taskId) {
        String todoApi = "https://jsonplaceholder.typicode.com/todos/";
        var apiUrl = todoApi + taskId;
        var response = restTemplate.getForEntity(apiUrl, JsonPlaceHolderTodo.class);
        var todo = response.getBody();
        if (todo == null) {
            return null;
        }
        String userApi = "https://jsonplaceholder.typicode.com/users/";
        var userUrl = userApi + todo.getUserId();
        var responseUser = restTemplate.getForEntity(userUrl, JsonPlaceHolderUser.class);
        var user = responseUser.getBody();

        if (user == null) {
            return null;
        }
        return AdditionalTaskInfo.of(
            user.getId(),
            user.getName(),
            user.getEmail()
        );
    }

    private static class JsonPlaceHolderTodo {
        private Long id;
        private String userId;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }

    private static class JsonPlaceHolderUser {
        private Long id;
        private String name;
        private String email;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
