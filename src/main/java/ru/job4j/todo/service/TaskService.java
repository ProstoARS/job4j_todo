package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class TaskService {


    private final TaskRepository taskRepository;
    private final PriorityService priorityService;

    public boolean addTask(Task task) {
        return checkPriorityFromDb(task) && taskRepository.addTask(task).isPresent();
    }

    public boolean upgradeTask(Task task) {
        return checkPriorityFromDb(task) && taskRepository.upgradeTask(task).isPresent();
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

    public boolean checkPriorityFromDb(Task task) {
        return priorityService.findById(task.getPriority().getId()).isPresent();
    }

    public boolean addOrChangeCategory(Task task, List<Category> categories) {
        for (Category rsl : categories) {
            if (rsl == null) {
                return false;
            }
        }
        task.getCategoryList().addAll(categories);
        categories.forEach(category -> category.getTasks().add(task));
        return true;
    }

}
