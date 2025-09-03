package emg.demo.hex.domain.ports.in;

import java.util.List;
import java.util.Optional;

import emg.demo.hex.domain.models.Task;

public interface RetrieveTaskUseCase {
    Optional<Task> getTask(Long id);

    List<Task> getAllTasks();
}
