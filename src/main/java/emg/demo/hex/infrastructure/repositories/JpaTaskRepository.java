package emg.demo.hex.infrastructure.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import emg.demo.hex.infrastructure.entities.TaskEntity;

@Repository
public interface JpaTaskRepository extends JpaRepository<TaskEntity, Long> {
}
