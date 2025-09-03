package emg.demo.hex.domain.ports.in;

import emg.demo.hex.domain.models.Task;

public interface CreateTaskUseCase {
    Task createTask(Task task);
}
