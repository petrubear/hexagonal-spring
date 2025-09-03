package emg.demo.hex.application.usecases;

import emg.demo.hex.domain.ports.in.DeleteTaskUseCase;
import emg.demo.hex.domain.ports.out.TaskRepositoryPort;

public class DeleteTaskUseCaseImpl implements DeleteTaskUseCase {
    private final TaskRepositoryPort taskRepositoryPort;

    public DeleteTaskUseCaseImpl(TaskRepositoryPort taskRepositoryPort) {
        this.taskRepositoryPort = taskRepositoryPort;
    }

    @Override
    public boolean deleteTask(Long id) {
        return this.taskRepositoryPort.deleteById(id);
    }
}
