package emg.demo.hex.application.services;

import java.util.List;
import java.util.Optional;

import emg.demo.hex.domain.models.AdditionalTaskInfo;
import emg.demo.hex.domain.models.Task;
import emg.demo.hex.domain.ports.in.CreateTaskUseCase;
import emg.demo.hex.domain.ports.in.DeleteTaskUseCase;
import emg.demo.hex.domain.ports.in.GetAdditionalTaskInfoUseCase;
import emg.demo.hex.domain.ports.in.RetrieveTaskUseCase;
import emg.demo.hex.domain.ports.in.UpdateTaskUseCase;

public class TaskService implements CreateTaskUseCase, DeleteTaskUseCase, GetAdditionalTaskInfoUseCase,
        RetrieveTaskUseCase, UpdateTaskUseCase {

    private final CreateTaskUseCase createTaskUseCase;
    private final DeleteTaskUseCase deleteTaskUseCase;
    private final GetAdditionalTaskInfoUseCase getAdditionalTaskInfoUseCase;
    private final RetrieveTaskUseCase retrieveTaskUseCase;
    private final UpdateTaskUseCase updateTaskUseCase;

    public TaskService(CreateTaskUseCase createTaskUseCase, DeleteTaskUseCase deleteTaskUseCase,
            GetAdditionalTaskInfoUseCase getAdditionalTaskInfoUseCase, RetrieveTaskUseCase retrieveTaskUseCase,
            UpdateTaskUseCase updateTaskUseCase) {
        this.createTaskUseCase = createTaskUseCase;
        this.deleteTaskUseCase = deleteTaskUseCase;
        this.getAdditionalTaskInfoUseCase = getAdditionalTaskInfoUseCase;
        this.retrieveTaskUseCase = retrieveTaskUseCase;
        this.updateTaskUseCase = updateTaskUseCase;
    }

    @Override
    public Optional<Task> updateTask(Long taskId, Task updatedTask) {
        return this.updateTaskUseCase.updateTask(taskId, updatedTask);
    }

    @Override
    public Optional<Task> getTask(Long id) {
        return retrieveTaskUseCase.getTask(id);
    }

    @Override
    public List<Task> getAllTasks() {
        return retrieveTaskUseCase.getAllTasks();
    }

    @Override
    public AdditionalTaskInfo getAdditionalTaskInfo(Long taskId) {
        return this.getAdditionalTaskInfoUseCase.getAdditionalTaskInfo(taskId);
    }

    @Override
    public boolean deleteTask(Long id) {
        return this.deleteTaskUseCase.deleteTask(id);
    }

    @Override
    public Task createTask(Task task) {
        return this.createTaskUseCase.createTask(task);
    }

}
