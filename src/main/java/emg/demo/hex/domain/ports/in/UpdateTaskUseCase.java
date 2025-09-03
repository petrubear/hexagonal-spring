package emg.demo.hex.domain.ports.in;

import java.util.Optional;

import emg.demo.hex.domain.models.Task;

public interface UpdateTaskUseCase {
    Optional<Task> updateTask(Long taskId, Task updatedTask);
}
