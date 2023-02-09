package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Priority;

import java.util.List;

@Repository
@AllArgsConstructor
public class PriorityRepository {

    private static final String FIND_ALL = "FROM Priority ORDER BY id";

    private final CrudRepository crudRepository;

    public List<Priority> findAll() {
        return crudRepository.query(FIND_ALL, Priority.class);
    }
}
