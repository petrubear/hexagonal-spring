package emg.demo.hex.domain.models;

import java.time.LocalDateTime;

public record Task (
     Long id,
     String title,
     String description,
     LocalDateTime creationDate,
     boolean completed
) {
    public static Task of(Long id, String title, String description, LocalDateTime creationDate, boolean completed) {
        return new Task(id, title, description, creationDate, completed);
    }
}
