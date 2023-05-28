package ru.nsu.calendar.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.calendar.entities.Task;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long>, JpaSpecificationExecutor<Task> {

}
