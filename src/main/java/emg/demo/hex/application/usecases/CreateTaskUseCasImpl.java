package emg.demo.hex.application.usecases;

import emg.demo.hex.domain.models.Task;
import emg.demo.hex.domain.ports.in.CreateTaskUseCase;
import emg.demo.hex.domain.ports.out.TaskRepositoryPort;

public class CreateTaskUseCasImpl implements CreateTaskUseCase {
    private final TaskRepositoryPort taskRepositoryPort;

    public CreateTaskUseCasImpl(TaskRepositoryPort taskRepositoryPort) {
        this.taskRepositoryPort = taskRepositoryPort;
    }

    @Override
    public Task createTask(Task task) {
        return this.taskRepositoryPort.save(task);
    }
}
