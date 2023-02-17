package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class TaskService {


    private final TaskRepository taskRepository;
    private final PriorityService priorityService;
    private final CategoryService categoryService;

    public boolean addTask(Task task) {
        return checkFromDb(task) && taskRepository.addTask(task).isPresent();
    }

    public boolean upgradeTask(Task task) {
        return checkFromDb(task) && taskRepository.upgradeTask(task);
    }

    public Boolean deleteTask(int id) {
        return taskRepository.deleteTask(id);
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public List<Task> findConditionTasks(Boolean check) {
        return taskRepository.findConditionTasks(check);
    }

    public Optional<Task> findById(int id) {
        return taskRepository.findById(id);
    }

    public boolean executeTask(int id) {
        return taskRepository.executeTask(id);
    }

    public boolean checkFromDb(Task task) {
        Optional<Priority> priority = priorityService.findById(task.getPriority().getId());
        List<Category> categoryList = task.getCategoryList();
        for (Category category : categoryList) {
            Optional<Category> categoryDb = categoryService.findById(category.getId());
            if (categoryDb.isEmpty() || priority.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public void addOrChangeCategory(Task task, List<Category> categories) {
        task.getCategoryList().addAll(categories);
        categories.forEach(category -> category.getTasks().add(task));
    }

}
