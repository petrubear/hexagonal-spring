package emg.demo.hex.infrastructure.repositories;

import emg.demo.hex.domain.models.Task;
import emg.demo.hex.domain.ports.out.TaskRepositoryPort;
import emg.demo.hex.infrastructure.entities.TaskEntity;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JpaTaskRepositoryAdapter implements TaskRepositoryPort {

    private final JpaTaskRepository jpaTaskRepository;

    public JpaTaskRepositoryAdapter(JpaTaskRepository jpaTaskRepository) {
        this.jpaTaskRepository = jpaTaskRepository;
    }

    @Override
    public Task save(Task task) {
        var taskEntity = TaskEntity.fromDomain(task);
        var savedEntity = jpaTaskRepository.save(taskEntity);
        return TaskEntity.toDomain(savedEntity);
    }

    @Override
    public Optional<Task> findById(Long id) {
        return jpaTaskRepository.findById(id).map(TaskEntity::toDomain);
    }

    @Override
    public List<Task> findAll() {
        return jpaTaskRepository.findAll().stream().map(TaskEntity::toDomain).toList();
    }

    @Override
    public Optional<Task> update(Task task) {
        if (jpaTaskRepository.existsById(task.id())) {
            var taskEntity = TaskEntity.fromDomain(task);
            var updatedEntity = jpaTaskRepository.save(taskEntity);
            return Optional.of(TaskEntity.toDomain(updatedEntity));
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteById(Long id) {
        if (jpaTaskRepository.existsById(id)) {
            jpaTaskRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
