package ru.pylaev.toDoProject.businessLogicLayer;

import ru.pylaev.toDoProject.dataAccessLayer.Task;
import ru.pylaev.toDoProject.presentLayer.Observer;

import java.util.List;

public interface Observable {
    void addObserver (Observer observer);
    void notifyObservers (List<Task> tasks);
}
