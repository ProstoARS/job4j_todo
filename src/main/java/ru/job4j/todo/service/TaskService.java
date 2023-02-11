package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public Optional<Task> addTask(Task task) {
        return taskRepository.addTask(task);
    }

    public boolean upgradeTask(Task task) {
        Optional<Priority> priority = Optional.ofNullable(task.getPriority());
        return priority.isPresent() && taskRepository.upgradeTask(task);
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
}
