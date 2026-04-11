package com.nickmenshikov.flux.tasks.repository;

import com.nickmenshikov.flux.core.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {
    Optional<Task> findTaskByIdAndCreatorId(Long id, Long creatorId);
    void deleteTaskById(Long id);
}
