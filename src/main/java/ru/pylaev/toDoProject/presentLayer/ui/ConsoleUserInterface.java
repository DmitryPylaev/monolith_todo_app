package ru.pylaev.toDoProject.presentLayer.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.pylaev.toDoProject.ToDoMain;
import ru.pylaev.toDoProject.businessLogicLayer.UserInputService;
import ru.pylaev.toDoProject.presentLayer.ViewHandler;
import ru.pylaev.toDoProject.presentLayer.view.View;

import java.util.Arrays;
import java.util.Scanner;

@Component
public class ConsoleUserInterface implements Runnable {
    private final View view;
    private final UserInputService userInputService;

    private final Scanner _scanner;
    private volatile boolean running = true;

    @Autowired
    public ConsoleUserInterface (View view, UserInputService userInputService) {
        this.view = view;
        this.userInputService = userInputService;
        _scanner = new Scanner((System.in)).useDelimiter("\n");
        System.out.println(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askOwner"));
    }

    @Override
    public void run () {
        while (running) {
            var userInput = _scanner.next();
            if (userInput.equals("EXIT")) {
                System.out.println(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("exitMessage"));
                running = false;
                break;
            }
            ViewHandler.processUserInput(userInput, view, userInputService);
            Arrays.stream(view.getArrTasks()).forEach(System.out::println);
            System.out.println(view.getMessage());
        }
    }
}
