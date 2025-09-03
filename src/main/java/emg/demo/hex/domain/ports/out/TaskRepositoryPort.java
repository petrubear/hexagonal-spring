package emg.demo.hex.domain.ports.out;

import java.util.List;
import java.util.Optional;

import emg.demo.hex.domain.models.Task;

public interface TaskRepositoryPort {

    Task save(Task task);

    Optional<Task> findById(Long id);

    List<Task> findAll();

    Optional<Task> update(Task task);

    boolean deleteById(Long id);
}
