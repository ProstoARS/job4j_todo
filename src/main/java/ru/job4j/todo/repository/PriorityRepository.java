package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Priority;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class PriorityRepository {

    private static final String FIND_ALL = "FROM Priority ORDER BY id";

    private static final String FIND_BY_ID = "FROM Priority WHERE id = :fId";

    private final CrudRepository crudRepository;

    public List<Priority> findAll() {
        return crudRepository.query(FIND_ALL, Priority.class);
    }

    public Optional<Priority> findById(int id) {
        return crudRepository.optional(FIND_BY_ID, Priority.class, Map.of("fId", id));
    }
}
