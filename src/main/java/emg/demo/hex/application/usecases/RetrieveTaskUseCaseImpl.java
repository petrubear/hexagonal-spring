package emg.demo.hex.application.usecases;

import java.util.List;
import java.util.Optional;

import emg.demo.hex.domain.models.Task;
import emg.demo.hex.domain.ports.in.RetrieveTaskUseCase;
import emg.demo.hex.domain.ports.out.TaskRepositoryPort;

public class RetrieveTaskUseCaseImpl implements RetrieveTaskUseCase {
    private final TaskRepositoryPort taskRepositoryPort;

    public RetrieveTaskUseCaseImpl(TaskRepositoryPort taskRepositoryPort) {
        this.taskRepositoryPort = taskRepositoryPort;
    }

    @Override
    public Optional<Task> getTask(Long id) {
        return this.taskRepositoryPort.findById(id);
    }

    @Override
    public List<Task> getAllTasks() {
        return this.taskRepositoryPort.findAll();
    }
}
