package ru.pylaev.toDoProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.pylaev.toDoProject.ToDoMain;
import ru.pylaev.toDoProject.models.Task;
import ru.pylaev.toDoProject.repo.TaskRepository;

import java.time.temporal.ValueRange;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class MainController {

    public static String message = ToDoMain.properties.getPropertyContent("askOwner");
    public static int taskIndex;
    public static String owner = "";
    public static String[] arrTasks;

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/")
    public String show (Model model) {
        model.addAttribute("arrTasks", arrTasks);
        model.addAttribute("message", message);
        model.addAttribute("input", "");
        return "home";
    }

    @PostMapping()
    public String processUserInput (@RequestParam("userInput") String userInput) {
        process(userInput);
        return "redirect:/";
    }

    public void process (String userInput) {
        String[] invalidNameSymbols = new String[]{" ", "\\", "|", "/", ":", "?", "\"", "<", ">"};
        String[] commands = new String[] {"ARCH", "DONE", "WAIT", "BACK", "EXIT"};
        String[] tasksStates = new String[] {"ARCH", "DONE", "WAIT"};

        if (owner.equals("")) {
            if (inputCheck(invalidNameSymbols, userInput) < 0) {
                owner = userInput;
            }
            else return;
        }

        String finalOwner = owner;
        List<Task> list = ((List<Task>) taskRepository.findAll()).stream().filter(task -> !task.getStatus().equals("ARCH")).filter(task -> task.getOwner().equals(finalOwner)).collect(Collectors.toList());

        if ((list.size()==0 && message.equals(ToDoMain.properties.getPropertyContent("askOwner")))
            || (userInput.equals("NEW") && message.equals(ToDoMain.properties.getPropertyContent("askNumber")))) {
            if (!userInput.equals("BACK")) {
                message = ToDoMain.properties.getPropertyContent("askNew");
            }
            return;
        }

        if (message.equals(ToDoMain.properties.getPropertyContent("askNew"))) {
            if (!userInput.equals("BACK")) {
                taskRepository.save(new Task(owner, userInput, new Date(), "WAIT"));
            }
            message = ToDoMain.properties.getPropertyContent("askNumber");
        }

        else if (message.equals(ToDoMain.properties.getPropertyContent("askOwner"))) {
            message = ToDoMain.properties.getPropertyContent("askNumber");
        }

        else if (message.equals(ToDoMain.properties.getPropertyContent("askNumber"))) {
            if (!userInput.equals("BACK")) {
                taskIndex = getIndex(userInput, list);
                if ((taskIndex != -1)&&(inputCheck(commands, userInput)<=0)) {
                    message = ToDoMain.properties.getPropertyContent("askStatus");
                }
            }
        }

        else if (message.equals(ToDoMain.properties.getPropertyContent("askStatus"))) {
            if (!userInput.equals("BACK")) {
                if (inputCheck(tasksStates, userInput)>0) {
                    Optional<Task> optionalTask = taskRepository.findById(list.get(taskIndex-1).getId());
                    if (optionalTask.isPresent()) {
                        Task task = optionalTask.get();
                        task.setStatus(userInput);
                        taskRepository.save(task);
                        message = ToDoMain.properties.getPropertyContent("askNumber");
                    }
                }
            }
            else {
                message = ToDoMain.properties.getPropertyContent("askNumber");
            }
        }

        List<Task> finalList = ((List<Task>) taskRepository.findAll()).stream().filter(task -> !task.getStatus().equals("ARCH")).filter(task -> task.getOwner().equals(finalOwner)).collect(Collectors.toList());
        arrTasks = new String[finalList.size()];
        IntStream.range(0, finalList.size()).forEach(i -> arrTasks[i] = i + 1 + " " + finalList.get(i));
    }

    private int getIndex (String userInput, List <Task> list) {
        try {
            int taskIndex = Integer.parseInt(userInput);
            return  (ValueRange.of(1, list.size()).isValidIntValue(taskIndex))?taskIndex:-1;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private int inputCheck (String[] arrString, String userInput) {
        boolean anyMatch = Arrays.stream(arrString).anyMatch(userInput::contains);
        if (userInput.length() > 0) {
            return (anyMatch)?1:-1;
        }
        else return 0;
    }

}
