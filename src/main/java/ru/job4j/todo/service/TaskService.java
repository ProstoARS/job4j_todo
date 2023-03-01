package ru.job4j.todo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.TaskRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@PropertySource("classpath:application.properties")
public class TaskService {

    @Value("${defaultTimeZone}")
    private String defaultTimeZone;
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

    public List<Task> findAll(ZoneId zoneId) {
        return taskRepository.findAll()
                .stream()
                .peek(task -> changeTaskTimeZone(task, zoneId))
                .collect(Collectors.toList());
    }

    public List<Task> findConditionTasks(Boolean check, ZoneId zoneId) {
        return taskRepository.findConditionTasks(check)
                .stream()
                .peek(task -> changeTaskTimeZone(task, zoneId))
                .collect(Collectors.toList());
    }

    public Optional<Task> findById(int id, ZoneId zoneId) {
        Optional<Task> byId = taskRepository.findById(id);
        return byId.map(task -> changeTaskTimeZone(task, zoneId));
    }

    public boolean executeTask(int id) {
        return taskRepository.executeTask(id);
    }

    public boolean checkPriorityFromDb(Task task) {
        return priorityService.findById(task.getPriority().getId()).isPresent();
    }

    public boolean addOrChangeCategory(Task task, List<Category> categories) {
        task.getCategoryList().addAll(categories);
        categories.forEach(category -> category.getTasks().add(task));
        return true;
    }

    public Task changeTaskTimeZone(Task task, ZoneId zoneId) {
        LocalDateTime createdWithTimeZone = task.getCreated().atZone(ZoneId.of(defaultTimeZone))
                .withZoneSameInstant(zoneId).toLocalDateTime();
        task.setCreated(createdWithTimeZone);
        return task;
    }

}
