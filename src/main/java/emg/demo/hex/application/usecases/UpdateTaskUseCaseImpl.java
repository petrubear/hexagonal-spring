package emg.demo.hex.application.usecases;

import java.util.Objects;
import java.util.Optional;

import emg.demo.hex.domain.models.Task;
import emg.demo.hex.domain.ports.in.UpdateTaskUseCase;
import emg.demo.hex.domain.ports.out.TaskRepositoryPort;

public class UpdateTaskUseCaseImpl implements UpdateTaskUseCase {
    private final TaskRepositoryPort taskRepositoryPort;

    public UpdateTaskUseCaseImpl(TaskRepositoryPort taskRepositoryPort) {
        this.taskRepositoryPort = taskRepositoryPort;
    }

    @Override
    public Optional<Task> updateTask(Long taskId, Task updatedTask) {
        if (Objects.equals(updatedTask.id(), taskId)) {
            return this.taskRepositoryPort.update(updatedTask);
        }
        return Optional.empty();
    }
}
