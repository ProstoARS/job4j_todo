package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.CategoryService;
import ru.job4j.todo.service.PriorityService;
import ru.job4j.todo.service.TaskService;
import ru.job4j.todo.util.SessionUser;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final PriorityService priorityService;
    private final CategoryService categoryService;

    @GetMapping("/index")
    public String index(Model model, HttpSession session) {
        model.addAttribute("user", SessionUser.getSessionUser(session));
        model.addAttribute("tasks", taskService.findAll());
        return "task/index";
    }

    @GetMapping("/formAdd")
    public String addTask(Model model, HttpSession session) {
        model.addAttribute("user", SessionUser.getSessionUser(session));
        model.addAttribute("task", new Task());
        model.addAttribute("priorities", priorityService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "task/add";
    }

    @PostMapping("/create")
    public String createTask(@ModelAttribute Task task,
                             @RequestParam(name = "categoriesId", required = false) List<Integer> categoryIdList, HttpSession session) {
        task.setUser(SessionUser.getSessionUser(session));
        boolean checkAndAddCategory = taskService.addOrChangeCategory(task, categoryService.findCategoriesById(categoryIdList));
        if (!checkAndAddCategory || !taskService.addTask(task)) {
            return "redirect:/tasks/error";
        }
        return "redirect:/tasks/index";
    }

    @GetMapping("/description/{id}")
    public String descriptionTask(Model model, @PathVariable("id") int id, HttpSession session) {
        model.addAttribute("user", SessionUser.getSessionUser(session));
        Optional<Task> task = taskService.findById(id);
        if (task.isEmpty()) {
            return "redirect:/tasks/error";
        }
        model.addAttribute("task", task.get());
        return "task/description";
    }

    @GetMapping("/allNew")
    public String newTasks(Model model, HttpSession session) {
        model.addAttribute("user", SessionUser.getSessionUser(session));
        model.addAttribute("newTasks", taskService.findConditionTasks(false));
        return "task/newList";
    }

    @GetMapping("/allDone")
    public String doneTasks(Model model, HttpSession session) {
        model.addAttribute("user", SessionUser.getSessionUser(session));
        model.addAttribute("doneTasks", taskService.findConditionTasks(true));
        return "task/doneList";
    }

    @PostMapping("/execute/{id}")
    public String executeTask(@PathVariable("id") int id) {
        if (!taskService.executeTask(id)) {
            return "redirect:/tasks/error";
        }
        return "redirect:/tasks/index";
    }

    @PostMapping("/delete/{id}")
    public String deleteTask(@PathVariable("id") int id) {
        if (!taskService.deleteTask(id)) {
            return "redirect:/tasks/error";
        }
        return "redirect:/tasks/index";
    }

    @GetMapping("/edit/{id}")
    public String editTask(@PathVariable("id") int id, Model model, HttpSession session) {
        model.addAttribute("user", SessionUser.getSessionUser(session));
        model.addAttribute("task", taskService.findById(id).get());
        model.addAttribute("priorities", priorityService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "task/update";
    }

    @PostMapping("/update")
    public String updateTask(@ModelAttribute Task task,
                             @RequestParam(name = "categoriesId", required = false) List<Integer> categoryIdList,
                             HttpSession session) {
        task.setUser(SessionUser.getSessionUser(session));
        boolean checkAndAddCategory = taskService.addOrChangeCategory(task, categoryService.findCategoriesById(categoryIdList));
        if (!checkAndAddCategory || !taskService.upgradeTask(task)) {
            return "redirect:/tasks/error";
        }
        return "redirect:/tasks/index";
    }

    @GetMapping("/error")
    public String fail(Model model, HttpSession session) {
        model.addAttribute("user", SessionUser.getSessionUser(session));
        return "task/fail";
    }
}
