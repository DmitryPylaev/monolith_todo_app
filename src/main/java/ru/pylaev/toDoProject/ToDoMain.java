package ru.pylaev.toDoProject;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import ru.pylaev.toDoProject.presentLayer.runnableUI.console.ConsoleUI;
import ru.pylaev.toDoProject.presentLayer.runnableUI.window.WindowUi;
import ru.pylaev.util.CustomProperties;

import java.util.concurrent.Executors;

@SpringBootApplication
public class ToDoMain {
    public static final CustomProperties PROPERTIES = new CustomProperties("customConfig");

    public static void main (String[] args){
        ApplicationContext context = new SpringApplicationBuilder(ToDoMain.class).headless(false).run(args);

        var executorService = Executors.newCachedThreadPool();

        executorService.execute(context.getBean(ConsoleUI.class));
        executorService.execute(context.getBean(WindowUi.class));
//        executorService.execute(context.getBean(TelegramUi.class));

    }
}

