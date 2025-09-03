package emg.demo.hex.infraestructure.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import emg.demo.hex.infraestructure.entities.TaskEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaTaskRepository extends JpaRepository<TaskEntity, Long> {
}
