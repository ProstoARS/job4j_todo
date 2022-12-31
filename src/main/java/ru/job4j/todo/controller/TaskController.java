package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.TaskService;

@AllArgsConstructor
@Controller
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "index";
    }

    @GetMapping("/formAddTask")
    public String addTask(Model model) {
        model.addAttribute("task", new Task());
        return "addTask";
    }

    @PostMapping("/createTask")
    public String createTask(@ModelAttribute Task task) {
        taskService.addTask(task);
        return "redirect:/index";
    }

    @GetMapping("/taskDescription/{taskId}")
    public String taskDescription(Model model, @PathVariable("taskId") int id) {
        model.addAttribute("task", taskService.findById(id));
        return "descriptionTask";
    }

    @GetMapping("/allNewTask")
    public String newTasks(Model model) {
        model.addAttribute("newTasks", taskService.findConditionTasks(false));
        return "newTaskList";
    }

    @GetMapping("/allDoneTask")
    public String doneTasks(Model model) {
        model.addAttribute("doneTasks", taskService.findConditionTasks(true));
        return "doneTaskList";
    }
}
