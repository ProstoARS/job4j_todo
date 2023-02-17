package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CategoryRepository {

    private static final String FIND_ALL = "FROM Category ORDER BY id";

    private static final String FIND_BY_ID = "FROM Category WHERE id = :fId";

    private static final String FIND_CATEGORIES_BY_ID = "FROM Category WHERE id IN (:fIds)";

    private final CrudRepository crudRepository;

    public List<Category> findAll() {
        return crudRepository.query(FIND_ALL, Category.class);
    }

    public Optional<Category> findById(int id) {
        return crudRepository.optional(FIND_BY_ID, Category.class, Map.of("fId", id));
    }

    public List<Category> findCategoriesById(List<Integer> ids) {
        return crudRepository.query(FIND_CATEGORIES_BY_ID, Category.class,
                Map.of("fIds", ids));
    }
}
